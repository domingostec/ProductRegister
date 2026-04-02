package org.example.productregister.service;

import org.example.productregister.dtos.request.UserRequest;
import org.example.productregister.dtos.response.UserResponse;
import org.example.productregister.exceptions.NotFoundException;
import org.example.productregister.model.User;
import org.example.productregister.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private  final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserResponse toResponse(User user){
        return new UserResponse(
                user.getEmail(),
                user.getName(),
                user.getNumberPhone()
        );
    }

    public UserResponse createUser(UserRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email already in use");
                });
        User user = new User();
        user.setName(request.getName().toLowerCase());
        user.setEmail(request.getEmail().toLowerCase());
        user.setNumberPhone(request.getNumberPhone());
        userRepository.save(user);

        return new UserResponse(user.getEmail(), user.getName(), user.getNumberPhone());
    }

    public UserResponse infoUser(UserRequest request){
        User user = userRepository.findByNameAndEmailAndNumberPhone(request.getName(), request.getEmail(), request.getNumberPhone())
                .orElseThrow(() -> new NotFoundException("User not found"));
        return toResponse(user);
    }


    public UserResponse updateUserEmail(String currentEmail, String newEmail){
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));
        if(userRepository.existsByEmail(newEmail)){
            throw new RuntimeException("Email already in use");
        }
        user.setEmail(newEmail);
        return toResponse(userRepository.save(user));
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
    }

}
