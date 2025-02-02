package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateQuestionRequest;
import ASKy.Backend.dto.response.QuestionResponse;
import ASKy.Backend.model.Question;
import ASKy.Backend.model.Topic;
import ASKy.Backend.model.User;
import ASKy.Backend.repository.QuestionRepository;
import ASKy.Backend.repository.TopicRepository;
import ASKy.Backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;

    public QuestionService(QuestionRepository questionRepository,
                           UserRepository userRepository,
                           TopicRepository topicRepository,
                           ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
    }

    // Create a new question
    public QuestionResponse createQuestion(CreateQuestionRequest request, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new EntityNotFoundException("Tema no encontrado"));

        Question question = modelMapper.map(request, Question.class);
        question.setUser(user);
        question.setTopic(topic);
        question.setCreatedAt(java.time.LocalDateTime.now());

        Question savedQuestion = questionRepository.save(question);
        return modelMapper.map(savedQuestion, QuestionResponse.class);
    }

    // Retrieve a question by ID
    public QuestionResponse getQuestionById(Integer questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada"));
        return modelMapper.map(question, QuestionResponse.class);
    }

    public List<QuestionResponse> searchQuestions(String keyword) {
        List<Question> questions = questionRepository.findByTitleContainingOrBodyContaining(keyword, keyword);
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionResponse.class))
                .toList();
    }

    public List<QuestionResponse> getQuestionsByUser(Integer userId) {
        List<Question> questions = questionRepository.findByUserUserId(userId);
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionResponse.class))
                .toList();
    }

    // Retrieve all questions
    public List<QuestionResponse> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionResponse.class))
                .toList();
    }

    // Assign a question to an expert
    public QuestionResponse assignQuestionToExpert(Integer questionId, Integer expertId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada"));

        User expert = userRepository.findById(expertId)
                .filter(User::getIsConsultant)
                .orElseThrow(() -> new EntityNotFoundException("Experto no encontrado o no válido"));

        question.setUser(expert);
        Question updatedQuestion = questionRepository.save(question);
        return modelMapper.map(updatedQuestion, QuestionResponse.class);
    }

    // Delete a question
    public void deleteQuestion(Integer questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new EntityNotFoundException("Pregunta no encontrada");
        }
        questionRepository.deleteById(questionId);
    }
}
