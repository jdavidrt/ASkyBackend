package ASKy.Backend.controller;

import ASKy.Backend.dto.request.CreateUserRequest;
import ASKy.Backend.dto.request.UpdateUserRequest;
import ASKy.Backend.dto.response.ActionResponse;
import ASKy.Backend.dto.response.UserResponse;
import ASKy.Backend.enums.ResponseMessage;
import ASKy.Backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Controlador para la gestión de usuarios")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Crear Usuario",
            description = "Crea un nuevo usuario y lo guarda en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario creado con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping
    public ResponseEntity<ActionResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse userResponse = userService.createUser(request);
        ActionResponse<UserResponse> response = new ActionResponse<>(true, userResponse, ResponseMessage.USER_CREATED_SUCCESS.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener Usuario por ID",
            description = "Recupera los detalles de un usuario específico por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario encontrado con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @GetMapping("/{id}")
    public ResponseEntity<ActionResponse<UserResponse>> getUserById(@PathVariable Integer id) {
        UserResponse userResponse = userService.getUserById(id);
        ActionResponse<UserResponse> response = new ActionResponse<>(true, userResponse, ResponseMessage.USER_NOT_FOUND.getMessage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener Lista de Usuarios",
            description = "Recupera una lista de usuarios filtrados opcionalmente por si son consultores o por email.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuarios encontrados con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
            })
    @GetMapping
    public ResponseEntity<ActionResponse<List<UserResponse>>> getUsers(
            @RequestParam(required = false) Boolean isConsultant,
            @RequestParam(required = false) String email) {
        List<UserResponse> responses = userService.getUsers(isConsultant, email);
        ActionResponse<List<UserResponse>> response = new ActionResponse<>(true, responses, ResponseMessage.USERS_RETRIEVED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar Usuario",
            description = "Actualiza los datos de un usuario específico identificado por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario actualizado con éxito.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PutMapping("/{id}")
    public ResponseEntity<ActionResponse<UserResponse>> updateUser(@PathVariable Integer id,
                                                                   @Valid @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.updateUser(id, request);
        ActionResponse<UserResponse> response = new ActionResponse<>(true, userResponse, ResponseMessage.USER_UPDATED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar Usuario",
            description = "Elimina un usuario específico identificado por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuario eliminado con éxito."),
                    @ApiResponse(responseCode = "404", description = "Usuario no encontrado.",
                            content = @Content(schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<ActionResponse<Void>> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        ActionResponse<Void> response = new ActionResponse<>(true, null, ResponseMessage.USER_DELETED_SUCCESS.getMessage());
        return ResponseEntity.noContent().build();
    }
}
