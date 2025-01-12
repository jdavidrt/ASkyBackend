package ASKy.Backend.controller;

import ASKy.Backend.dto.request.ExpertFilterRequest;
import ASKy.Backend.dto.response.ActionResponse;
import ASKy.Backend.dto.response.ExpertResponse;
import ASKy.Backend.enums.ResponseMessage;
import ASKy.Backend.service.ExpertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experts")
@Tag(name = "Experts", description = "Controlador para la gestión de expertos")
public class ExpertController {

    private final ExpertService expertService;

    public ExpertController(ExpertService expertService) {
        this.expertService = expertService;
    }

    @Operation(summary = "Obtener Lista de Expertos",
            description = "Recupera una lista de expertos basándose en filtros opcionales como especialidad, nombre, o criterio de ordenamiento.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de expertos obtenida con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExpertResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetros de búsqueda inválidos.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @GetMapping
    public ResponseEntity<ActionResponse<List<ExpertResponse>>> getExperts(
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "rating") String orderBy) {

        ExpertFilterRequest filter = new ExpertFilterRequest();
        filter.setSpecialty(specialty);
        filter.setName(name);
        filter.setOrderBy(orderBy);

        List<ExpertResponse> experts = expertService.getExperts(filter);

        ActionResponse<List<ExpertResponse>> response = new ActionResponse<>(
                true, experts, ResponseMessage.EXPERTS_RETRIEVED_SUCCESS.getMessage()
        );

        return ResponseEntity.ok(response);
    }
}

