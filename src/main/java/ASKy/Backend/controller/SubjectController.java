package ASKy.Backend.controller;

import ASKy.Backend.dto.request.CreateSubjectRequest;
import ASKy.Backend.dto.response.ActionResponse;
import ASKy.Backend.dto.response.SubjectResponse;
import ASKy.Backend.enums.ResponseMessage;
import ASKy.Backend.service.SubjectService;
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
@RequestMapping("/subjects")
@Tag(name = "Subjects", description = "Controlador para la gestión de subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Operation(summary = "Crear Subject",
            description = "Crea un nuevo subject.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Subject creado con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping
    public ResponseEntity<ActionResponse<SubjectResponse>> createSubject(
            @PathVariable Integer topicId,
            @Valid @RequestBody CreateSubjectRequest request) {
        SubjectResponse subjectResponse = subjectService.createSubject(topicId, request);
        ActionResponse<SubjectResponse> response = new ActionResponse<SubjectResponse>(
                true, subjectResponse, ResponseMessage.SUBJECT_CREATED_SUCCESS.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener Todos los Subjects",
            description = "Recupera todos los subjects registrados en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Subjects encontrados con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectResponse.class)))
            })
    @GetMapping
    public ResponseEntity<ActionResponse<List<SubjectResponse>>> getAllSubjects() {
        List<SubjectResponse> responses = subjectService.getAllSubjects();
        ActionResponse<List<SubjectResponse>> response = new ActionResponse<>(
                true, responses, ResponseMessage.SUBJECTS_RETRIEVED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar Subject",
            description = "Elimina un subject específico identificado por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Subject eliminado con éxito."),
                    @ApiResponse(responseCode = "404", description = "Subject no encontrado.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<ActionResponse<Void>> deleteSubject(@PathVariable Integer id) {
        subjectService.deleteSubject(id);
        ActionResponse<Void> response = new ActionResponse<>(
                true, null, ResponseMessage.SUBJECT_DELETED_SUCCESS.getMessage());
        return ResponseEntity.noContent().build();
    }
}
