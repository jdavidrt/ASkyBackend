package ASKy.Backend.controller;
import ASKy.Backend.dto.request.RateAnswerRequest;
import ASKy.Backend.dto.response.RatingResponse;
import ASKy.Backend.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public ResponseEntity<RatingResponse> rateAnswer( @RequestBody RateAnswerRequest request) {
        RatingResponse response = ratingService.rateAnswer(request);
        return ResponseEntity.ok(response);
    }
}