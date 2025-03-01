package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateAnswerRequest;
import ASKy.Backend.dto.response.AnswerDetailResponse;
import ASKy.Backend.dto.response.AnswerResponse;
import ASKy.Backend.model.*;
import ASKy.Backend.repository.IAnswerDetailRepository;
import ASKy.Backend.repository.IAnswerRepository;
import ASKy.Backend.repository.IQuestionRepository;
import ASKy.Backend.repository.IUserRepository;
import ASKy.Backend.specification.AnswerDetailSpecification;
import ASKy.Backend.specification.AnswerSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public AnswerResponse createAnswer(CreateAnswerRequest request) {
        Question question = IQuestionRepository.findByQuestionId(request.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada o no asignada a este experto"));

        // Check if the question already has an answer
        if (question.getAnswer() != null) {
            throw new IllegalArgumentException("Esta pregunta ya ha sido respondida o rechazada.");
        }

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setBody(request.getBody());
        answer.setType(request.getType());
        answer.setCreatedAt(LocalDateTime.now());

        // Update question status based on the answer type
        if (request.getType() == 1) {
            question.setStatus((byte) 1); // Answered
        } else {
            question.setStatus((byte) 2); // Rejected
        }

        Answer savedAnswer = IAnswerRepository.save(answer);
        IQuestionRepository.save(question);

        return modelMapper.map(savedAnswer, AnswerResponse.class);
    }

    public List<AnswerResponse> getAnswersByQuestion(Integer questionId) {
       Optional<Answer> answers = IAnswerRepository.findById(questionId);
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
                .map(this::mapToAnswerResponse) // ✅ Usamos el nuevo método de mapeo
                .toList();
    }


    private AnswerResponse mapToAnswerResponse(Answer answer) {
        AnswerResponse response = new AnswerResponse();
        response.setId(answer.getAnswerId());
        response.setType(mapAnswerType(answer.getType()));
        response.setBody(answer.getBody());
        response.setQuestionId(answer.getQuestion().getQuestionId());
        response.setCreatedAt(answer.getCreatedAt());
        return response;
    }

    private String mapAnswerType(Byte type) {
        return type != null && type == 1 ? "accepted" : "rejected";
    }

    public List<AnswerDetailResponse> getAnswerDetailsByQuestion(Integer questionId) {
        List<AnswerDetail> details = IAnswerDetailRepository.findByQuestionQuestionId(questionId);
        return details.stream()
                .map(detail -> modelMapper.map(detail, AnswerDetailResponse.class))
                .toList();
    }

    /**
     * ✅ Get all answer details for a specific expert
     */
    public List<AnswerDetailResponse> getAnswerDetailsByExpert(Integer expertId) {
        List<AnswerDetail> details = IAnswerDetailRepository.findByExpertUserId(expertId);
        return details.stream()
                .map(detail -> modelMapper.map(detail, AnswerDetailResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * ✅ Get all answer details for a specific user
     */
    public List<AnswerDetailResponse> getAnswerDetailsByUser(Integer userId) {
        List<AnswerDetail> details = IAnswerDetailRepository.findByUserUserId(userId);
        return details.stream()
                .map(detail -> modelMapper.map(detail, AnswerDetailResponse.class))
                .collect(Collectors.toList());
    }

    public List<AnswerResponse> searchAnswerDetails(String expertName, String userName, Boolean isRight, Integer minRating) {
        Specification<AnswerDetail> spec = AnswerDetailSpecification.byFilters(expertName, userName, isRight, minRating);
        List<AnswerDetail> answerDetails = IAnswerDetailRepository.findAll(spec);

        return answerDetails.stream()
                .map(answerDetail -> {
                    AnswerResponse response = modelMapper.map(answerDetail.getAnswer(), AnswerResponse.class);
                    response.setIsRight(answerDetail.getIsRight());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<AnswerResponse> filterAnswers(String expertName, String userName, Boolean isRight, Integer minRating) {
        Specification<Answer> spec = AnswerSpecification.byFilters(expertName, userName, isRight, minRating);
        List<Answer> answers = IAnswerRepository.findAll(spec);

        return answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerResponse.class))
                .collect(Collectors.toList());
    }

}

