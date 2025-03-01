package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateQuestionRequest;
import ASKy.Backend.dto.request.RejectQuestionRequest;
import ASKy.Backend.dto.response.QuestionResponse;
import ASKy.Backend.model.*;
import ASKy.Backend.repository.*;
import ASKy.Backend.specification.QuestionSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final IQuestionRepository IQuestionRepository;
    private final IUserRepository IUserRepository;
    private final IExpertRepository IExpertRepository;
    private final FileUploadService fileUploadService;
    private final ITopicRepository ITopicRepository;
    private final ModelMapper modelMapper;
    private final INotificationRepository INotificationRepository;

    public QuestionService(IQuestionRepository IQuestionRepository,
                           IUserRepository IUserRepository,
                           IExpertRepository IExpertRepository,
                           FileUploadService fileUploadService,
                           ITopicRepository ITopicRepository,
                           ModelMapper modelMapper,
                           INotificationRepository INotificationRepository) {
        this.IQuestionRepository = IQuestionRepository;
        this.IUserRepository = IUserRepository;
        this.IExpertRepository = IExpertRepository;
        this.fileUploadService = fileUploadService;
        this.ITopicRepository = ITopicRepository;
        this.modelMapper = modelMapper;
        this.INotificationRepository = INotificationRepository;
    }

    // Create a new question
    public QuestionResponse createQuestion(CreateQuestionRequest request) {

        User user = IUserRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Expert expert = IExpertRepository.findById(request.getExpertId())
                .orElseThrow(() -> new EntityNotFoundException("Experto no encontrado"));

        Topic topic = ITopicRepository.findById(request.getTopicId())
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

        if (user.getAmountAskoins()<request.getPrice()){
            throw new IllegalArgumentException("No tienes suficientes askoins para realizar esta pregunta");
        }



        Question question = new Question();
        question.setTitle(request.getTitle());
        question.setBody(request.getBody()); // 🔹 Ensure this is not null
        question.setPrice(request.getPrice());
        question.setTopic(topic);
        question.setUser(user);
        question.setExpert(expert);
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

        Question savedQuestion = IQuestionRepository.save(question);
        return modelMapper.map(savedQuestion, QuestionResponse.class);
    }

    // Retrieve a question by ID
    public QuestionResponse getQuestionById(Integer questionId) {
        Question question = IQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada"));
        return modelMapper.map(question, QuestionResponse.class);
    }

    public List<QuestionResponse> searchQuestions(String keyword) {
        List<Question> questions = IQuestionRepository.findByTitleContainingOrBodyContaining(keyword, keyword);
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionResponse.class))
                .toList();
    }

    public List<QuestionResponse> getQuestionsByUser(Integer userId) {
        List<Question> questions = IQuestionRepository.findAllByUserUserId(userId);

        return questions.stream()
                .map(this::mapToQuestionResponse) // 🔹 Use the custom mapping method
                .collect(Collectors.toList());
    }

    // Retrieve all questions
    public List<QuestionResponse> getAllQuestions() {
        List<Question> questions = IQuestionRepository.findAll();

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
        return active != null && active == 1 ? "abierto" : "cerrado";
    }

    // 🔹 Convert `status` (Byte) to a String
    private String mapStatus(Byte status) {
        return switch (status) {
            case 0 -> "pendiente";
            case 1 -> "aceptado";
            case 2 -> "rechazado";
            case 3 -> "cancelado";
            default -> "desconocido";
        };
    }

    // Assign a question to an expert
    public QuestionResponse assignQuestionToExpert(Integer questionId, Integer expertId) {
        Question question = IQuestionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada"));

        User expert = IUserRepository.findById(expertId)
                .filter(User::getIsConsultant)
                .orElseThrow(() -> new EntityNotFoundException("Experto no encontrado o no válido"));

        question.setUser(expert);
        Question updatedQuestion = IQuestionRepository.save(question);
        return modelMapper.map(updatedQuestion, QuestionResponse.class);
    }

    // Delete a question
    public void deleteQuestion(Integer questionId) {
        if (!IQuestionRepository.existsById(questionId)) {
            throw new EntityNotFoundException("Pregunta no encontrada");
        }
        IQuestionRepository.deleteById(questionId);
    }

    public void rejectQuestion(RejectQuestionRequest request) {
        Question question = IQuestionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("Pregunta no encontrada"));

        question.setStatus((byte) 2); // 🔹 2 = Rejected
        IQuestionRepository.save(question);

        // 🔹 Notify the user
        Notification notification = new Notification();
        notification.setUser(question.getUser());
        notification.setMessage("Tu pregunta ha sido rechazada: " + request.getRejectionReason());
        notification.setType("QUESTION_REJECTED");
        notification.setReadStatus((byte) 0);
        notification.setCreatedAt(LocalDateTime.now());

        INotificationRepository.save(notification);
    }

    public List<QuestionResponse> filterQuestions(String title, String body, Integer topicId, Integer userId, Integer expertId, Byte status) {
        Specification<Question> spec = QuestionSpecification.byFilters(title, body, topicId, userId, expertId, status, "createdAt"); // 🔹 Added default orderBy
        List<Question> questions = IQuestionRepository.findAll(spec);

        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionResponse.class))
                .collect(Collectors.toList());
    }

}
