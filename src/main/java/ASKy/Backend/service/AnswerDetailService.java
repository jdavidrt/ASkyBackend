package ASKy.Backend.service;

import ASKy.Backend.model.*;
import ASKy.Backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerDetailService {

    private final AnswerDetailRepository answerDetailRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final ExpertRepository expertRepository;

    public AnswerDetailService(AnswerDetailRepository answerDetailRepository, AnswerRepository answerRepository,
                               QuestionRepository questionRepository, UserRepository userRepository, ExpertRepository expertRepository) {
        this.answerDetailRepository = answerDetailRepository;
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.expertRepository = expertRepository;
    }

    // 🔹 Save an AnswerDetail Record (Track Answer-Question History)
    public AnswerDetail saveAnswerDetail(Integer answerId, Integer questionId, Integer expertId, Integer userId, Boolean isRight) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada"));

        Expert expert = expertRepository.findById(expertId)
                .orElseThrow(() -> new EntityNotFoundException("Experto no encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        AnswerDetail answerDetail = new AnswerDetail();
        answerDetail.setAnswer(answer);
        answerDetail.setQuestion(question);
        answerDetail.setExpert(expert);
        answerDetail.setUser(user);
        answerDetail.setIsRight(isRight);

        return answerDetailRepository.save(answerDetail);
    }

    // 🔹 Get History of Answers for a Question
    public List<AnswerDetail> getAnswerHistoryByQuestion(Integer questionId) {
        return answerDetailRepository.findByQuestionQuestionId(questionId);
    }

    // 🔹 Get Answer History for an Expert
    public List<AnswerDetail> getAnswerHistoryByExpert(Integer expertId) {
        return answerDetailRepository.findByExpertUserId(expertId);
    }

    // 🔹 Get Answer History for a User
    public List<AnswerDetail> getAnswerHistoryByUser(Integer userId) {
        return answerDetailRepository.findByUserUserId(userId);
    }

    // 🔹 Delete Answer Detail (If needed)
    public void deleteAnswerDetail(Integer answerDetailId) {
        if (!answerDetailRepository.existsById(answerDetailId)) {
            throw new EntityNotFoundException("Registro de respuesta no encontrado");
        }
        answerDetailRepository.deleteById(answerDetailId);
    }
}