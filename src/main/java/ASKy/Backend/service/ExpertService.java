package ASKy.Backend.service;

import ASKy.Backend.dto.request.ExpertFilterRequest;
import ASKy.Backend.dto.response.ExpertResponse;
import ASKy.Backend.model.Expert;
import ASKy.Backend.model.Rating;
import ASKy.Backend.repository.ExpertRepository;
import ASKy.Backend.repository.RatingRepository;
import ASKy.Backend.repository.UserRepository;
import ASKy.Backend.specification.ExpertSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpertService {

    private final ExpertRepository expertRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;


    public ExpertService(ExpertRepository expertRepository, ModelMapper modelMapper, UserRepository userRepository, RatingRepository ratingRepository) {
        this.expertRepository = expertRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<ExpertResponse> searchExperts(ExpertFilterRequest filters) {
        Specification<Expert> specification = ExpertSpecification.byFilters(filters);
        List<Expert> experts = expertRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "averageRating"));

        return experts.stream()
                .map(expert -> modelMapper.map(expert, ExpertResponse.class))
                .collect(Collectors.toList());
    }

    // 🔹 Apply sanctions if the expert's average rating is too low

    public void applyExpertSanctions(Integer expertId) {
        Expert expert = (Expert) userRepository.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("Experto no encontrado"));

        long lowRatingsCount = ratingRepository.findByExpert(expert).stream()
                .filter(rating -> rating.getRating() < 2).count();

        // 🔹 Expert gets sanctioned if they have received 2 or more low ratings
        boolean shouldSanction = lowRatingsCount >= 2;

        if (expert.getSanctioned() != shouldSanction) {
            expert.setSanctioned(shouldSanction);
            expertRepository.save(expert);
        }
    }

    // 🔹 Update expert's average rating when a new rating is added

    public void updateExpertRating(Integer expertId) {
        Expert expert = expertRepository.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("Experto no encontrado"));

        List<Rating> ratings = ratingRepository.findByExpert(expert);
        int totalRatings = ratings.size();

        if (totalRatings == 0) {
            expert.setAverageRating(0.0f);
            expert.setTotalResponses(0);
        } else {
            double weightedSum = 0;
            double totalWeight = 0;
            double decayFactor = 0.9;

            for (int i = 0; i < totalRatings; i++) {
                double weight = Math.pow(decayFactor, totalRatings - i - 1);
                totalWeight += weight;
                weightedSum += ratings.get(i).getRating() * weight;
            }

            expert.setAverageRating((float) (weightedSum / totalWeight));
            expert.setTotalResponses(totalRatings);
        }

        expertRepository.save(expert);
    }
}
