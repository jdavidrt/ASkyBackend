package ASKy.Backend.controller;

import ASKy.Backend.dto.request.CreateTopicRequest;
import ASKy.Backend.dto.response.ActionResponse;
import ASKy.Backend.dto.response.TopicResponse;
import ASKy.Backend.enums.ResponseMessage;
import ASKy.Backend.service.TopicService;
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
@RequestMapping("/topics")
@Tag(name = "Topics", description = "Controlador para la gestión de temas")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @Operation(summary = "Crear Tema",
            description = "Crea un nuevo tema asociado a un subject específico.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Tema creado con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TopicResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping
    public ResponseEntity<ActionResponse<TopicResponse>> createTopic(
            @Valid @RequestBody CreateTopicRequest request) {
        TopicResponse topicResponse = topicService.createTopic(request);
        ActionResponse<TopicResponse> response = new ActionResponse<>(
                true, topicResponse, ResponseMessage.TOPIC_CREATED_SUCCESS.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener Todos los Temas",
            description = "Recupera todos los temas disponibles en la plataforma.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Temas encontrados con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TopicResponse.class)))
            })
    @GetMapping
    public ResponseEntity<ActionResponse<List<TopicResponse>>> getAllTopics() {
        List<TopicResponse> responses = topicService.getAllTopics();
        ActionResponse<List<TopicResponse>> response = new ActionResponse<>(
                true, responses, ResponseMessage.TOPICS_RETRIEVED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar Tema",
            description = "Elimina un tema específico identificado por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Tema eliminado con éxito."),
                    @ApiResponse(responseCode = "404", description = "Tema no encontrado.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<ActionResponse<Void>> deleteTopic(@PathVariable Integer id) {
        topicService.deleteTopic(id);
        ActionResponse<Void> response = new ActionResponse<>(
                true, null, ResponseMessage.TOPIC_DELETED_SUCCESS.getMessage());
        return ResponseEntity.noContent().build();
    }
}

