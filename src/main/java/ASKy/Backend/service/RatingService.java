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

    private final IRatingRepository IRatingRepository;
    private final IAnswerRepository IAnswerRepository;
    private final IUserRepository IUserRepository;
    private final IExpertRepository IExpertRepository;
    private final ModelMapper modelMapper;

    public RatingService(IRatingRepository IRatingRepository, IAnswerRepository IAnswerRepository,
                         IUserRepository IUserRepository, IExpertRepository IExpertRepository, ModelMapper modelMapper) {
        this.IRatingRepository = IRatingRepository;
        this.IAnswerRepository = IAnswerRepository;
        this.IUserRepository = IUserRepository;
        this.IExpertRepository = IExpertRepository;
        this.modelMapper = modelMapper;
    }

    public RatingResponse rateAnswer(RateAnswerRequest request) {
        Answer answer = IAnswerRepository.findById(request.getAnswerId())
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));

        Expert expert = (Expert) IUserRepository.findById(answer.getQuestion().getExpert().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Experto no encontrado"));

        User user = IUserRepository.findById(answer.getQuestion().getUser().getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Rating rating = new Rating();
        rating.setUser(user);
        rating.setExpert(expert);
        rating.setAnswer(answer);
        rating.setRating(request.getRating());
        rating.setComment(request.getComment());
        rating.setCreatedAt(LocalDateTime.now());

        IRatingRepository.save(rating);

        // Transferir askoins al experto y descontar del usuario
        transferAskoins(answer, expert, user, request.getRating());
        // 🔹 Update expert rating
        updateExpertRating(expert);

        return modelMapper.map(rating, RatingResponse.class);
    }

    private void transferAskoins(Answer answer, Expert expert, User user, int rating) {
        Question question = answer.getQuestion();
        float askoinAmount = question.getPrice();

        // Calcular el pago basado en el rating (1 a 5 estrellas, cada estrella equivale al 20% del pago)
        float paymentProportion = rating * 0.2f;
        float paymentAmount = askoinAmount * paymentProportion;

        // Transferir askoins al experto
        expert.setAmountAskoins(expert.getAmountAskoins() + paymentAmount);

        // Descontar askoins del usuario
        user.setAmountAskoins(user.getAmountAskoins() - paymentAmount);

        // Guardar cambios en los repositorios
        IExpertRepository.save(expert);
        IUserRepository.save(user);
    }

    private void updateExpertRating(Expert expert) {
        List<Rating> ratings = IRatingRepository.findByExpert(expert);

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

        IExpertRepository.save(expert);
    }
}

