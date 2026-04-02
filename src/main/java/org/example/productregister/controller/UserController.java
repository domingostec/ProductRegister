package org.example.productregister.controller;


import jakarta.validation.Valid;
import org.example.productregister.dtos.request.UserRequest;
import org.example.productregister.dtos.response.UserResponse;
import org.example.productregister.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest request){
        UserResponse response = userService.createUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam (required = false) String numberPhone

    ) {
        UserRequest request = new UserRequest();
        request.setEmail(email);
        request.setName(name);
        request.setNumberPhone(numberPhone);

        UserResponse response = userService.infoUser(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUserProfile(
            @RequestParam String currentEmail,
            @RequestParam String newEmail){
        UserResponse response = userService.updateUserEmail(currentEmail, newEmail);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserAccount(@RequestParam Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User with email " + id + " deleted successfully.");
    }
}


