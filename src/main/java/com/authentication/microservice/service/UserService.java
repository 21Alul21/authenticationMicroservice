package com.authentication.microservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.authentication.microservice.dto.UserResponseDTO;
import com.authentication.microservice.dto.UserUpdateDTO;
import com.authentication.microservice.entity.UserEntity;
import com.authentication.microservice.repository.UserRepository;


import jakarta.transaction.Transactional;

@Service
public class UserService{
    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService){
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void deleteUser(UUID id) {
        try{
            userRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("error occured wile deleting user; " + e.getMessage());
        }
    }


    public void deactivateUser(UUID id) {
      UserEntity user = userRepository.findById(id)
                                       .orElseThrow(() -> {throw new RuntimeException("Unable to load user");});
        user.setActive(false);
        userRepository.save(user);
    }


    public void activateUser(UUID id) {
          UserEntity user = userRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Unable to load user"));
            user.setActive(true);
            userRepository.save(user);
        
    }

    @Transactional
    public Map<String, String> updateUser(UUID id, UserUpdateDTO userUpdateDTO){
        UserEntity user = userRepository.findById(id)
                                         .orElseThrow(() -> new RuntimeException("Unable to load user"));
       
        // update user info 
        if (userUpdateDTO.email() != null){ 
            user.setEmail(userUpdateDTO.email());
        }
        userRepository.save(user);   
        return Map.of("message", "user record updated");

    }


    public List<UserResponseDTO> listUsers() {
        List<UserEntity> users = userRepository.findAll();

        if (users == null){
           return new ArrayList<>();
        }
        
        return users.stream()
                    .map(user -> new UserResponseDTO(
                        user.getId().toString(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.isActive()
                    ))
                    .collect(Collectors.toList());
        
    }

   

}




