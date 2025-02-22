package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateQuestionRequest;
import ASKy.Backend.dto.request.RejectQuestionRequest;
import ASKy.Backend.dto.response.QuestionResponse;
import ASKy.Backend.model.Notification;
import ASKy.Backend.model.Question;
import ASKy.Backend.model.Topic;
import ASKy.Backend.model.User;
import ASKy.Backend.repository.NotificationRepository;
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
    private final NotificationRepository notificationRepository;

    public QuestionService(QuestionRepository questionRepository,
                           UserRepository userRepository,
                            FileUploadService fileUploadService,
                           TopicRepository topicRepository,
                           ModelMapper modelMapper,
                           NotificationRepository notificationRepository) {
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.fileUploadService = fileUploadService;
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
        this.notificationRepository = notificationRepository;
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
                .map(this::mapToQuestionResponse) // 🔹 Use the custom mapping method
                .collect(Collectors.toList());
    }

    // Retrieve all questions
    public List<QuestionResponse> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream()
                .map(this::mapToQuestionResponse)
                .toList();
    }

    // 🔹 Method to map Question -> QuestionResponse
    private QuestionResponse mapToQuestionResponse(Question question) {
        QuestionResponse response = modelMapper.map(question, QuestionResponse.class);
        response.setActive(mapActiveStatus(question.getActive()));
        response.setStatus(mapStatus(question.getStatus()));
        return response;
    }

    // 🔹 Convert `active` (Byte) to a String ("open" or "closed")
    private String mapActiveStatus(Byte active) {
        return active != null && active == 1 ? "open" : "closed";
    }

    // 🔹 Convert `status` (Byte) to a String
    private String mapStatus(Byte status) {
        return switch (status) {
            case 0 -> "pending";
            case 1 -> "accepted";
            case 2 -> "rejected";
            case 3 -> "canceled";
            default -> "unknown";
        };
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

    public void rejectQuestion(RejectQuestionRequest request) {
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada"));

        question.setStatus((byte) 2); // 🔹 2 = Rejected
        questionRepository.save(question);

        // 🔹 Notify the user
        Notification notification = new Notification();
        notification.setUser(question.getUser());
        notification.setMessage("Tu pregunta ha sido rechazada: " + request.getRejectionReason());
        notification.setType("QUESTION_REJECTED");
        notification.setReadStatus((byte) 0);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }
}
