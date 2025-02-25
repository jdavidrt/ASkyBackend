package ASKy.Backend.controller;

import ASKy.Backend.dto.request.CreateAnswerRequest;
import ASKy.Backend.dto.request.RateAnswerRequest;
import ASKy.Backend.dto.response.ActionResponse;
import ASKy.Backend.dto.response.AnswerDetailResponse;
import ASKy.Backend.dto.response.AnswerResponse;
import ASKy.Backend.enums.ResponseMessage;
import ASKy.Backend.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
@Tag(name = "Answers", description = "Controlador para la gestión de respuestas")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Operation(summary = "Crear Respuesta",
            description = "Crea una nueva respuesta y la guarda en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Respuesta creada con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping
    public ResponseEntity<ActionResponse<AnswerResponse>> createAnswer(@Valid @RequestBody CreateAnswerRequest request, @RequestParam Integer userId) {
        AnswerResponse answerResponse = answerService.createAnswer(request, userId);
        ActionResponse<AnswerResponse> response = new ActionResponse<>(true, answerResponse, ResponseMessage.ANSWER_CREATED_SUCCESS.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener Respuestas por Pregunta",
            description = "Recupera una lista de respuestas asociadas a una pregunta específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuestas encontradas con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponse.class)))
            })
    @GetMapping("/question/{questionId}")
    public ResponseEntity<ActionResponse<List<AnswerResponse>>> getAnswersByQuestion(@PathVariable Integer questionId) {
        List<AnswerResponse> answers = answerService.getAnswersByQuestion(questionId);
        ActionResponse<List<AnswerResponse>> response = new ActionResponse<>(true, answers, ResponseMessage.ANSWERS_RETRIEVED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar Respuestas",
            description = "Busca respuestas que contengan una palabra clave específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuestas encontradas con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponse.class)))
            })
    @GetMapping("/search")
    public ResponseEntity<ActionResponse<List<AnswerResponse>>> searchAnswers(@RequestParam String keyword) {
        List<AnswerResponse> answers = answerService.searchAnswers(keyword);
        ActionResponse<List<AnswerResponse>> response = new ActionResponse<>(true, answers, ResponseMessage.ANSWERS_RETRIEVED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar Respuesta",
            description = "Elimina una respuesta específica identificada por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Respuesta eliminada con éxito."),
                    @ApiResponse(responseCode = "404", description = "Respuesta no encontrada.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @DeleteMapping("/{answerId}")
    public ResponseEntity<ActionResponse<Void>> deleteAnswer(@PathVariable Integer answerId) {
        answerService.deleteAnswer(answerId);
        ActionResponse<Void> response = new ActionResponse<>(true, null, ResponseMessage.ANSWER_DELETED_SUCCESS.getMessage());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener Todas las Respuestas",
            description = "Recupera una lista de todas las respuestas en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuestas encontradas con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponse.class)))
            })
    @GetMapping
    public ResponseEntity<ActionResponse<List<AnswerResponse>>> getAllAnswers() {
        List<AnswerResponse> answers = answerService.getAllAnswers();
        ActionResponse<List<AnswerResponse>> response = new ActionResponse<>(true, answers, ResponseMessage.ANSWERS_RETRIEVED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener Detalles de Respuestas por Pregunta",
            description = "Recupera detalles de respuestas asociadas a una pregunta específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalles encontrados con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerDetailResponse.class)))
            })
    @GetMapping("/details/question/{questionId}")
    public ResponseEntity<ActionResponse<List<AnswerDetailResponse>>> getAnswerDetailsByQuestion(@PathVariable Integer questionId) {
        List<AnswerDetailResponse> details = answerService.getAnswerDetailsByQuestion(questionId);
        ActionResponse<List<AnswerDetailResponse>> response = new ActionResponse<>(true, details, "Detalles de respuestas obtenidos con éxito.");
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Obtener Detalles de Respuestas por Experto",
            description = "Recupera detalles de respuestas asociadas a un experto específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalles encontrados con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerDetailResponse.class)))
            })
    @GetMapping("/expert/{expertId}")
    public ResponseEntity<List<AnswerDetailResponse>> getAnswerDetailsByExpert(@PathVariable Integer expertId) {
        List<AnswerDetailResponse> responses = answerService.getAnswerDetailsByExpert(expertId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Obtener Detalles de Respuestas por Usuario",
            description = "Recupera detalles de respuestas asociadas a un usuario específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalles encontrados con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerDetailResponse.class)))
            })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AnswerDetailResponse>> getAnswerDetailsByUser(@PathVariable Integer userId) {
        List<AnswerDetailResponse> responses = answerService.getAnswerDetailsByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Buscar Respuestas con Filtros",
            description = "Permite buscar respuestas con filtros opcionales: nombre del experto, nombre del usuario, si es correcta y calificación mínima.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuestas encontradas con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponse.class)))
            })
    @GetMapping("/search-detail")
    public ResponseEntity<List<AnswerResponse>> searchAnswerDetails(
            @RequestParam(required = false) String expertName,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) Boolean isRight,
            @RequestParam(required = false) Integer minRating
    ) {
        List<AnswerResponse> responses = answerService.searchAnswerDetails(expertName, userName, isRight, minRating);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Search Answers",
            description = "Filters answers based on expert name, user name, correctness, and rating.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Answers retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerResponse.class)))
            })
    @GetMapping("/search")
    public ResponseEntity<List<AnswerResponse>> filterAnswers(
            @RequestParam(required = false) String expertName,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) Boolean isRight,
            @RequestParam(required = false) Integer minRating) {
        List<AnswerResponse> responses = answerService.filterAnswers(expertName, userName, isRight, minRating);
        return ResponseEntity.ok(responses);
    }

}