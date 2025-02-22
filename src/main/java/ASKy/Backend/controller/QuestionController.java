package ASKy.Backend.controller;

import ASKy.Backend.dto.request.CreateQuestionRequest;
import ASKy.Backend.dto.response.ActionResponse;
import ASKy.Backend.dto.response.QuestionResponse;
import ASKy.Backend.enums.ResponseMessage;
import ASKy.Backend.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/questions")
@Tag(name = "Questions", description = "Controlador para la gestión de preguntas")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Operation(
            summary = "Crear Pregunta",
            description = "Crea una nueva pregunta asociada a un usuario específico.",
            requestBody = @RequestBody(
                    description = "Datos de la pregunta, incluyendo imagen opcional",
                    required = true,
                    content = @Content(mediaType = "multipart/form-data",
                            schema = @Schema(implementation = CreateQuestionRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pregunta creada con éxito",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = QuestionResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                            content = @Content(mediaType = "application/json"))
            }
    )
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ActionResponse<QuestionResponse>> createQuestion(
            @Valid @RequestBody CreateQuestionRequest request,
            @RequestParam Integer userId) {
        QuestionResponse questionResponse = questionService.createQuestion(request, userId);
        ActionResponse<QuestionResponse> response = new ActionResponse<>(
                true, questionResponse, ResponseMessage.QUESTION_CREATED_SUCCESS.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener Pregunta por ID",
            description = "Recupera los detalles de una pregunta específica por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pregunta encontrada con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Pregunta no encontrada.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @GetMapping("/{id}")
    public ResponseEntity<ActionResponse<QuestionResponse>> getQuestionById(@PathVariable Integer id) {
        QuestionResponse questionResponse = questionService.getQuestionById(id);
        ActionResponse<QuestionResponse> response = new ActionResponse<>(
                true, questionResponse, ResponseMessage.QUESTION_RETRIEVED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener Todas las Preguntas",
            description = "Recupera una lista de todas las preguntas registradas en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Preguntas encontradas con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse.class)))
            })
    @GetMapping
    public ResponseEntity<ActionResponse<List<QuestionResponse>>> getAllQuestions() {
        List<QuestionResponse> responses = questionService.getAllQuestions();
        ActionResponse<List<QuestionResponse>> response = new ActionResponse<>(
                true, responses, ResponseMessage.QUESTIONS_RETRIEVED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    // Función para buscar preguntas por palabra clave
    @Operation(summary = "Buscar Preguntas",
            description = "Busca preguntas utilizando una palabra clave en el título o descripción.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Preguntas encontradas con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "No se encontraron preguntas.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @GetMapping("/search")
    public ResponseEntity<ActionResponse<List<QuestionResponse>>> searchQuestions(@RequestParam String keyword) {
        List<QuestionResponse> responses = questionService.searchQuestions(keyword);
        ActionResponse<List<QuestionResponse>> response = new ActionResponse<>(
                true, responses, ResponseMessage.QUESTIONS_RETRIEVED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    // Función para obtener preguntas por usuario
    @Operation(summary = "Obtener Preguntas por Usuario",
            description = "Recupera todas las preguntas creadas por un usuario específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Preguntas obtenidas con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Usuario o preguntas no encontradas.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @GetMapping("/user/{userId}")
    public ResponseEntity<ActionResponse<List<QuestionResponse>>> getQuestionsByUser(@PathVariable Integer userId) {
        List<QuestionResponse> responses = questionService.getQuestionsByUser(userId);
        ActionResponse<List<QuestionResponse>> response = new ActionResponse<>(
                true, responses, ResponseMessage.QUESTIONS_RETRIEVED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Asignar Pregunta a un Experto",
            description = "Asigna una pregunta específica a un experto identificado por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pregunta asignada con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Pregunta o experto no encontrado.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PutMapping("/{id}/assign-expert/{expertId}")
    public ResponseEntity<ActionResponse<QuestionResponse>> assignQuestionToExpert(
            @PathVariable Integer id, @PathVariable Integer expertId) {
        QuestionResponse questionResponse = questionService.assignQuestionToExpert(id, expertId);
        ActionResponse<QuestionResponse> response = new ActionResponse<>(
                true, questionResponse, ResponseMessage.QUESTION_ASSIGNED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar Pregunta",
            description = "Elimina una pregunta específica identificada por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pregunta eliminada con éxito."),
                    @ApiResponse(responseCode = "404", description = "Pregunta no encontrada.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<ActionResponse<Void>> deleteQuestion(@PathVariable Integer id) {
        questionService.deleteQuestion(id);
        ActionResponse<Void> response = new ActionResponse<>(
                true, null, ResponseMessage.QUESTION_DELETED_SUCCESS.getMessage());
        return ResponseEntity.noContent().build();
    }
}

