package com.authentication.microservice.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.microservice.dto.UserResponseDTO;
import com.authentication.microservice.entity.UserEntity;
import com.authentication.microservice.service.UserService;

@RestController
@RequestMapping("api/v1/users/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;

    }

    // list user
    @GetMapping("list-users")
    public ResponseEntity<List<UserResponseDTO>> listUsers(){
        return ResponseEntity.
        ok(userService.listUsers());
    }

    // deactivate user
    @PostMapping("deactivate-user/{id}")
    public ResponseEntity<Map<String, String>> deactivateUser(@PathVariable UUID id){
        userService.deactivateUser(id);
        return ResponseEntity.ok(Map.of("message", "User deactivated successfully"));
    }

    // reactivate user
    @PostMapping("activate-user/{id}")
    public ResponseEntity<Map<String, String>> activateUser(@PathVariable UUID id){
        userService.activateUser(id);
        return ResponseEntity.ok(Map.of("message", "User activated successfully"));
    }

    // delete user
    @PostMapping("delete-user/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
    
}
