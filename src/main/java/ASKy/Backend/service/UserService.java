package ASKy.Backend.service;

import ASKy.Backend.dto.request.CreateUserRequest;
import ASKy.Backend.dto.request.UpdateUserRequest;
import ASKy.Backend.dto.response.UserResponse;
import ASKy.Backend.model.Expert;
import ASKy.Backend.model.User;
import ASKy.Backend.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {


    private final IUserRepository IUserRepository;
    private final FileUploadService fileUploadService;
    private final ModelMapper modelMapper;

    public UserService(IUserRepository IUserRepository, FileUploadService fileUploadService, ModelMapper modelMapper) {
        this.IUserRepository = IUserRepository;
        this.fileUploadService = fileUploadService;
        this.modelMapper = modelMapper;
    }

    public UserResponse createUser(CreateUserRequest request) {
        if (IUserRepository.existsByAuth0Id(request.getAuth0Id())) {
            throw new IllegalArgumentException("El usuario ya está registrado");
        }

        User user;
        if (Boolean.TRUE.equals(request.getIsConsultant())) {
            Expert expert = new Expert();
            expert.setBiography(request.getBiography());
            expert.setBasePrice(request.getBasePrice());
            expert.setAverageRating(0.0f);
            expert.setAvailability(true);
            expert.setResponseRate(0.0f);
            expert.setTotalResponses(0);
            user = expert;
        } else {
            user = new User();
        }

        user.setAuth0Id(request.getAuth0Id());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setIsConsultant(request.getIsConsultant());
        user.setPassword(request.getPassword());
        user.setStatus(true);

        // Subir imagen si está presente
        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            try {
                String imageUrl = fileUploadService.uploadFile(request.getProfileImage());
                user.setProfileImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la imagen de perfil", e);
            }
        }

        User savedUser = IUserRepository.save(user);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    public UserResponse getUserById(Integer id) {
        User user = IUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return modelMapper.map(user, UserResponse.class);
    }

    public UserResponse getUserByAuth0Id(String auth0Id) {
        User user = IUserRepository.findByAuth0Id(auth0Id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return modelMapper.map(user, UserResponse.class);
    }

    public List<UserResponse> getUsers(Boolean isConsultant, String email) {
        List<User> users;
        if (isConsultant != null) {
            users = IUserRepository.findByIsConsultant(isConsultant);
        } else if (email != null) {
            users = IUserRepository.findByEmail(email).stream().toList();
        } else {
            users = IUserRepository.findAll();
        }
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
    }

    public UserResponse updateUser(Integer id, UpdateUserRequest request) {
        User user = IUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        modelMapper.map(request, user);
        User updatedUser = IUserRepository.save(user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }


    public UserResponse updateUserById(Integer Id, UpdateUserRequest request) {
        User user = IUserRepository.findById(Id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Map only the fields that are present in the request
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getDescription() != null) user.setProfileImageUrl(request.getDescription());
        if (request.getIsConsultant() != null) user.setIsConsultant(request.getIsConsultant());
        if (request.getStatus() != null) user.setStatus(request.getStatus());
//        if (request.getBiography() != null) user.setBiography(request.getBiography());
//        if (request.getBasePrice() != null) user.setBasePrice(request.getBasePrice());
//        if (request.getAvailability() != null) user.setAvailability(request.getAvailability());

        // 🔹 Handle profile image update
        if (request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            try {
                String imageUrl = fileUploadService.uploadFile(request.getProfileImage());
                user.setProfileImageUrl(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la imagen de perfil", e);
            }
        }

        User updatedUser = IUserRepository.save(user);
        return modelMapper.map(updatedUser, UserResponse.class);
    }


    public void deleteUser(Integer id) {
        if (!IUserRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario no encontrado");
        }
        IUserRepository.deleteById(id);
    }
}
