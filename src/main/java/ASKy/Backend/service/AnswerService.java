package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateAnswerRequest;
import ASKy.Backend.dto.response.AnswerDetailResponse;
import ASKy.Backend.dto.response.AnswerResponse;
import ASKy.Backend.model.*;
import ASKy.Backend.repository.IAnswerDetailRepository;
import ASKy.Backend.repository.IAnswerRepository;
import ASKy.Backend.repository.IQuestionRepository;
import ASKy.Backend.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ASKy.Backend.dto.request.RateAnswerRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerService {

    private final IAnswerRepository IAnswerRepository;
    private final IQuestionRepository IQuestionRepository;
    private final IUserRepository IUserRepository;
    private final IAnswerDetailRepository IAnswerDetailRepository;
    private final ModelMapper modelMapper;

    public AnswerService(IAnswerRepository IAnswerRepository, IQuestionRepository IQuestionRepository,
                         IUserRepository IUserRepository, IAnswerDetailRepository IAnswerDetailRepository, ModelMapper modelMapper) {
        this.IAnswerRepository = IAnswerRepository;
        this.IQuestionRepository = IQuestionRepository;
        this.IUserRepository = IUserRepository;
        this.IAnswerDetailRepository = IAnswerDetailRepository;
        this.modelMapper = modelMapper;
    }

    public AnswerResponse createAnswer(CreateAnswerRequest request, Integer userId) {
        Question question = IQuestionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada"));

        Expert expert = (Expert) IUserRepository.findById(request.getExpertId())
                .filter(User::getIsConsultant)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no es un experto"));

        User user = IUserRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setUser(expert);
        answer.setBody(request.getBody());
        answer.setCreatedAt(LocalDateTime.now());

        Answer savedAnswer = IAnswerRepository.save(answer);

        // 🔹 Create AnswerDetail entry
        AnswerDetail answerDetail = new AnswerDetail();
        answerDetail.setAnswer(savedAnswer);
        answerDetail.setQuestion(question);
        answerDetail.setExpert(expert);
        answerDetail.setUser(question.getUser());
        answerDetail.setIsRight(null); // Not evaluated yet

        IAnswerDetailRepository.save(answerDetail);

        return modelMapper.map(savedAnswer, AnswerResponse.class);
    }

    public List<AnswerResponse> getAnswersByQuestion(Integer questionId) {
        List<Answer> answers = IAnswerRepository.findByQuestionQuestionId(questionId);
        return answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerResponse
                        .class))
                .toList();
    }

    public List<AnswerResponse> searchAnswers(String keyword) {
        List<Answer> answers = IAnswerRepository.findByBodyContaining(keyword);
        return answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerResponse.class))
                .toList();
    }

    public void deleteAnswer(Integer answerId) {
        if (!IAnswerRepository.existsById(answerId)) {
            throw new EntityNotFoundException("Respuesta no encontrada");
        }
        IAnswerRepository.deleteById(answerId);
    }

    public List<AnswerResponse> getAllAnswers() {
        return IAnswerRepository.findAll().stream()
                .map(answer -> modelMapper.map(answer, AnswerResponse.class))
                .toList();
    }
    
    public AnswerResponse rateAnswer(Integer answerId, RateAnswerRequest request) {
    Answer answer = IAnswerRepository.findById(answerId)
            .orElseThrow(() -> new EntityNotFoundException("Respuesta no encontrada"));

    answer.setRating(request.getRating());
    answer.setComment(request.getComment());
    answer.setRatedAt(LocalDateTime.now());

    Answer updatedAnswer = IAnswerRepository.save(answer);

        // 🔹 Update AnswerDetail with correctness (threshold: 3 stars or higher)
        AnswerDetail answerDetail = IAnswerDetailRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Registro de respuesta no encontrado"));

        answerDetail.setIsRight(request.getRating() >= 3);
        IAnswerDetailRepository.save(answerDetail);

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

    public List<AnswerDetailResponse> getAnswerDetailsByQuestion(Integer questionId) {
        List<AnswerDetail> details = IAnswerDetailRepository.findByQuestionQuestionId(questionId);
        return details.stream()
                .map(detail -> modelMapper.map(detail, AnswerDetailResponse.class))
                .toList();
    }
}

