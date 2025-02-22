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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final FileUploadService fileUploadService;
    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;

    public QuestionService(QuestionRepository questionRepository,
                           UserRepository userRepository,
                            FileUploadService fileUploadService,
                           TopicRepository topicRepository,
                           ModelMapper modelMapper) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.fileUploadService = fileUploadService;
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
    }

    // Create a new question
    public QuestionResponse createQuestion(CreateQuestionRequest request, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new EntityNotFoundException("Tema no encontrado"));

        // **🔹 Convertir deadline de UTC a Zona Horaria de Colombia**
        ZoneId colombiaZone = ZoneId.of("America/Bogota");
        LocalDateTime deadlineUtc = request.getDeadline(); // La fecha llega en UTC desde el cliente
        LocalDateTime deadlineColombia = deadlineUtc.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(colombiaZone)
                .toLocalDateTime();

        // **🔹 Validar que la fecha sea futura en Colombia**
        if (deadlineColombia.isBefore(LocalDateTime.now(colombiaZone))) {
            throw new IllegalArgumentException("La fecha límite debe estar en el futuro en la zona horaria de Colombia.");
        }



        Question question = new Question();
        question.setTitle(request.getTitle());
        question.setBody(request.getBody()); // 🔹 Ensure this is not null
        question.setPrice(request.getPrice());
        question.setTopic(topic);
        question.setUser(user);
        question.setDeadline(deadlineColombia);
        question.setCreatedAt(LocalDateTime.now(colombiaZone));

        // Upload Image to Cloudinary and store URL
        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
            try {
                String imageUrl = fileUploadService.uploadFile(request.getImageUrl());
                question.setImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la imagen de pregunta", e);
            }
        }

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
        List<Question> questions = questionRepository.findAllByUserUserId(userId);
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionResponse.class))
                .collect(Collectors.toList());
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
