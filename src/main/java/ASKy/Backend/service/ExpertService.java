package ASKy.Backend.service;

import ASKy.Backend.dto.request.ExpertFilterRequest;
import ASKy.Backend.dto.response.ExpertResponse;
import ASKy.Backend.model.Expert;
import ASKy.Backend.model.Rating;
import ASKy.Backend.repository.IExpertRepository;
import ASKy.Backend.repository.IRatingRepository;
import ASKy.Backend.repository.IUserRepository;
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

    private final IExpertRepository IExpertRepository;
    private final ModelMapper modelMapper;
    private final IUserRepository IUserRepository;
    private final IRatingRepository IRatingRepository;


    public ExpertService(IExpertRepository IExpertRepository, ModelMapper modelMapper, IUserRepository IUserRepository, IRatingRepository IRatingRepository) {
        this.IExpertRepository = IExpertRepository;
        this.modelMapper = modelMapper;
        this.IUserRepository = IUserRepository;
        this.IRatingRepository = IRatingRepository;
    }

    public List<ExpertResponse> searchExperts(ExpertFilterRequest filters) {
        Specification<Expert> specification = ExpertSpecification.byFilters(filters);
        List<Expert> experts = IExpertRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "averageRating"));

        return experts.stream()
                .map(expert -> modelMapper.map(expert, ExpertResponse.class))
                .collect(Collectors.toList());
    }

    // 🔹 Apply sanctions if the expert's average rating is too low

    public void applyExpertSanctions(Integer expertId) {
        Expert expert = (Expert) IUserRepository.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("Experto no encontrado"));

        long lowRatingsCount = IRatingRepository.findByExpert(expert).stream()
                .filter(rating -> rating.getRating() < 2).count();

        // 🔹 Expert gets sanctioned if they have received 2 or more low ratings
        boolean shouldSanction = lowRatingsCount >= 2;

        if (expert.getSanctioned() != shouldSanction) {
            expert.setSanctioned(shouldSanction);
            IExpertRepository.save(expert);
        }
    }

    // 🔹 Update expert's average rating when a new rating is added

    public void updateExpertRating(Integer expertId) {
        Expert expert = IExpertRepository.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("Experto no encontrado"));

        List<Rating> ratings = IRatingRepository.findByExpert(expert);
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

        IExpertRepository.save(expert);
    }
}
