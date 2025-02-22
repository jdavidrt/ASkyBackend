package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateAnswerRequest;
import ASKy.Backend.dto.response.AnswerResponse;
import ASKy.Backend.model.*;
import ASKy.Backend.repository.AnswerRepository;
import ASKy.Backend.repository.QuestionRepository;
import ASKy.Backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ASKy.Backend.dto.request.RateAnswerRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository,
                         UserRepository userRepository, ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public AnswerResponse createAnswer(CreateAnswerRequest request, Integer userId) {
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada"));

        Expert expert = (Expert) userRepository.findById(request.getExpertId())
                .filter(User::getIsConsultant)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no es un experto"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setUser(expert);
        answer.setBody(request.getBody());
        answer.setCreatedAt(LocalDateTime.now());

        Answer savedAnswer = answerRepository.save(answer);

        // 🔹 Create AnswerDetail entry
        AnswerDetail answerDetail = new AnswerDetail();
        answerDetail.setAnswer(savedAnswer);
        answerDetail.setQuestion(question);
        answerDetail.setExpert(expert);
        answerDetail.setUser(user);
        answerDetail.setIsRight(null); // Not evaluated yet

        return modelMapper.map(savedAnswer, AnswerResponse.class);
    }

    public List<AnswerResponse> getAnswersByQuestion(Integer questionId) {
        List<Answer> answers = answerRepository.findByQuestionQuestionId(questionId);
        return answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerResponse
                        .class))
                .toList();
    }

    public List<AnswerResponse> searchAnswers(String keyword) {
        List<Answer> answers = answerRepository.findByBodyContaining(keyword);
        return answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerResponse.class))
                .toList();
    }

    public void deleteAnswer(Integer answerId) {
        if (!answerRepository.existsById(answerId)) {
            throw new EntityNotFoundException("Respuesta no encontrada");
        }
        answerRepository.deleteById(answerId);
    }

    public List<AnswerResponse> getAllAnswers() {
        return answerRepository.findAll().stream()
                .map(answer -> modelMapper.map(answer, AnswerResponse.class))
                .toList();
    }
    
    public AnswerResponse rateAnswer(Integer answerId, RateAnswerRequest request) {
    Answer answer = answerRepository.findById(answerId)
            .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));

    answer.setRating(request.getRating());
    answer.setComment(request.getComment());
    answer.setRatedAt(LocalDateTime.now());

    Answer updatedAnswer = answerRepository.save(answer);
    return modelMapper.map(updatedAnswer, AnswerResponse.class);
}

    private AnswerResponse mapToAnswerResponse(Answer answer) {
        AnswerResponse response = new AnswerResponse();
        response.setId(answer.getAnswerId());
        response.setBody(answer.getBody());
        response.setQuestionId(answer.getQuestion().getQuestionId());
        response.setUserId(answer.getUser().getUserId());
        response.setCreatedAt(answer.getCreatedAt());
        response.setRating(answer.getRating());
        response.setComment(answer.getComment());
        response.setRatedAt(answer.getRatedAt());
        return response;
    }
}

