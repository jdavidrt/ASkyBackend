package ASKy.Backend.controller;
import ASKy.Backend.dto.request.RateAnswerRequest;
import ASKy.Backend.dto.response.RatingResponse;
import ASKy.Backend.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/ratings")
@Tag(name = "Ratings", description = "Controlador para la gestión de calificaciones")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Operation(
            summary = "Calificar una Respuesta",
            description = "Permite a un usuario calificar la respuesta de un experto.",
            requestBody = @RequestBody(
                    description = "Datos de la calificación.",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RateAnswerRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta calificada con éxito.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RatingResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Respuesta no encontrada.")
            }
    )
    @PostMapping
    public ResponseEntity<RatingResponse> rateAnswer(@Valid @RequestBody RateAnswerRequest request) {
        RatingResponse response = ratingService.rateAnswer(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener Calificación por Respuesta",
            description = "Recupera la calificación asociada a una respuesta específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Calificación encontrada con éxito.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RatingResponse.class))),
                    @ApiResponse(responseCode = "404", description = "No se encontró calificación para la respuesta.")
            }
    )
    @GetMapping("/answer/{answerId}")
    public ResponseEntity<RatingResponse> getRatingByAnswerId(@PathVariable Integer answerId) {
        RatingResponse response = ratingService.getRatingByAnswerId(answerId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener Todas las Calificaciones",
            description = "Recupera todas las calificaciones registradas en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de calificaciones obtenida con éxito.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RatingResponse.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<RatingResponse>> getAllRatings() {
        List<RatingResponse> response = ratingService.getAllRatings();
        return ResponseEntity.ok(response);
    }
}