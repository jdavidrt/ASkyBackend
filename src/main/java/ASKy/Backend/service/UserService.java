package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateUserRequest;
import ASKy.Backend.dto.request.UpdateUserRequest;
import ASKy.Backend.dto.response.UserResponse;
import ASKy.Backend.model.User;
import ASKy.Backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByAuth0Id(request.getAuth0Id())) {
            throw new IllegalArgumentException("El usuario ya está registrado");
        }
        User user = modelMapper.map(request, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponse getUserByAuth0Id(String auth0Id) {
        User user = userRepository.findByAuth0Id(auth0Id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return modelMapper.map(user, UserResponse.class);
    }

    public List<UserResponse> getUsers(Boolean isConsultant, String email) {
        List<User> users;
        if (isConsultant != null) {
            users = userRepository.findByIsConsultant(isConsultant);
        } else if (email != null) {
            users = userRepository.findByEmail(email).stream().toList();
        } else {
            users = userRepository.findAll();
        }
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
    }

    public UserResponse updateUser(Integer id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        modelMapper.map(request, user);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}
