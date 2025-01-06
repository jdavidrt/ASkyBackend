package ASKy.Backend.controller;

import ASKy.Backend.dto.request.CreateUserRequest;
import ASKy.Backend.dto.request.UpdateUserRequest;
import ASKy.Backend.dto.response.ApiResponse;
import ASKy.Backend.dto.response.UserResponse;
import ASKy.Backend.enums.ResponseMessage;
import ASKy.Backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse userResponse = userService.createUser(request);
        ApiResponse<UserResponse> response = new ApiResponse<>(true, userResponse, ResponseMessage.USER_CREATED_SUCCESS.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Integer id) {
        UserResponse userResponse = userService.getUserById(id);
        ApiResponse<UserResponse> response = new ApiResponse<>(true, userResponse, ResponseMessage.USER_NOT_FOUND.getMessage());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers(
            @RequestParam(required = false) Boolean isConsultant,
            @RequestParam(required = false) String email) {
        List<UserResponse> responses = userService.getUsers(isConsultant, email);
        ApiResponse<List<UserResponse>> response = new ApiResponse<>(true, responses, ResponseMessage.USERS_RETRIEVED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Integer id,
                                                                @Valid @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.updateUser(id, request);
        ApiResponse<UserResponse> response = new ApiResponse<>(true, userResponse, ResponseMessage.USER_UPDATED_SUCCESS.getMessage());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        ApiResponse<Void> response = new ApiResponse<>(true, null, ResponseMessage.USER_DELETED_SUCCESS.getMessage());
        return ResponseEntity.noContent().build();
    }
}
