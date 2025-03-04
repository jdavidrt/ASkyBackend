package ASKy.Backend.controller;
import ASKy.Backend.dto.request.RateAnswerRequest;
import ASKy.Backend.dto.response.ActionResponse;
import ASKy.Backend.dto.response.RatingResponse;
import ASKy.Backend.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Calificar Respuesta", description = "Permite a un usuario calificar una respuesta y su experto.")
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


    @Operation(summary = "Eliminar Calificación",
            description = "Elimina una calificación específica identificada por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Calificación eliminada con éxito."),
                    @ApiResponse(responseCode = "404", description = "Calificación no encontrada.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<ActionResponse<Void>> deleteRating(@PathVariable Integer ratingId) {
        ratingService.deleteRating(ratingId);
        ActionResponse<Void> response = new ActionResponse<>(true, null, "Calificación eliminada con éxito.");
        return ResponseEntity.noContent().build();
    }
}