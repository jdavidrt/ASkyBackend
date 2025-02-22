package ASKy.Backend.service;

import ASKy.Backend.dto.request.RateAnswerRequest;
import ASKy.Backend.dto.response.RatingResponse;
import ASKy.Backend.model.*;
import ASKy.Backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final ExpertRepository expertRepository;
    private final ModelMapper modelMapper;

    public RatingService(RatingRepository ratingRepository, AnswerRepository answerRepository,
                         UserRepository userRepository, ExpertRepository expertRepository, ModelMapper modelMapper) {
        this.ratingRepository = ratingRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.expertRepository = expertRepository;
        this.modelMapper = modelMapper;
    }

    public RatingResponse rateAnswer(Integer userId, RateAnswerRequest request) {
        Answer answer = answerRepository.findById(request.getAnswerId())
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));

        Expert expert = (Expert) userRepository.findById(answer.getUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Experto no encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Rating rating = new Rating();
        rating.setUser(user);
        rating.setExpert(expert);
        rating.setAnswer(answer);
        rating.setRating(request.getRating());
        rating.setComment(request.getComment());
        rating.setCreatedAt(LocalDateTime.now());

        ratingRepository.save(rating);

        // 🔹 Update expert rating
        updateExpertRating(expert);

        return modelMapper.map(rating, RatingResponse.class);
    }

    private void updateExpertRating(Expert expert) {
        List<Rating> ratings = ratingRepository.findByExpert(expert);

        // 🔹 Weighted Rating Calculation (Newer ratings weigh more)
        double totalWeight = 0;
        double weightedSum = 0;
        int totalRatings = ratings.size();
        double decayFactor = 0.9; // 🔹 Newer ratings have 90% impact of the previous

        for (int i = 0; i < totalRatings; i++) {
            double weight = Math.pow(decayFactor, totalRatings - i - 1);
            totalWeight += weight;
            weightedSum += ratings.get(i).getRating() * weight;
        }

        float newAverageRating = totalRatings > 0 ? (float) (weightedSum / totalWeight) : 0;
        expert.setAverageRating(newAverageRating);

        // 🔹 Check if expert should be sanctioned
        long lowRatingsCount = ratings.stream().filter(r -> r.getRating() < 2).count();
        if (lowRatingsCount >= 2) {
            expert.setSanctioned(true);
        }

        expertRepository.save(expert);
    }
}

