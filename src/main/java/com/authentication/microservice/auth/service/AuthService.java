package com.authentication.microservice.auth.service;

import com.authentication.microservice.auth.JwtUtil;
import com.authentication.microservice.dto.RegistrationRequestDTO;
import com.authentication.microservice.dto.RegistrationResponseDTO;
import com.authentication.microservice.entity.RefreshTokenEntity;
import com.authentication.microservice.entity.UserEntity;
import com.authentication.microservice.enums.RoleEnum;
import com.authentication.microservice.exception.AccountNotActiveException;
import com.authentication.microservice.repository.UserRepository;
import com.authentication.microservice.service.EmailService;

import com.authentication.microservice.dto.AuthRequestDTO;
import com.authentication.microservice.dto.ChangePasswordDTO;
import com.authentication.microservice.dto.ConfirmPasswordResetDTO;
import com.authentication.microservice.dto.EmailDTO;
import com.authentication.microservice.dto.LoginResponseDTO;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;


@Service
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, 
        EmailService emailService
    ){
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }


    public RegistrationResponseDTO registerUser(RegistrationRequestDTO req) {
        // check if email already exists in db
       UserEntity user = userRepo.findByEmail(req.email())
                                            .orElse(null);
                            
        if (user != null){
            throw new RuntimeException("Email address already exists"); //EmailAlreadyExistsException
        }

        UserEntity newUser = UserEntity.builder()
                                        .firstName(req.firstName())
                                        .lastName(req.lastName())
                                        .email(req.email())
                                        .password(passwordEncoder.encode(req.password()))
                                        .build();

        //set User as admin
        if(req.specialCode() == null){
            newUser.setRole(RoleEnum.USER);
        }else{

             if(req.specialCode().equals("ADMINSECRETDEVELOPMENTCODE")){
            newUser.setRole(RoleEnum.ADMIN);
            }else{
                newUser.setRole(RoleEnum.USER);
            }
        }


        // persist in db
        userRepo.save(newUser);

        //build and return response dto
        RegistrationResponseDTO res = new RegistrationResponseDTO(
            "Success",
            "User registered sucessfully"
        );
        return res;

    }
    


    public LoginResponseDTO loginUser(AuthRequestDTO req) {
        // check if email exist in db
        UserEntity user = userRepo.findByEmail(req.email())
                .orElse(null);

        if (user == null){
            throw new IllegalArgumentException("invalid email or password"); // WrongInformationException
        }

        // check if user is active
        if (user.isActive() == false){
            throw new AccountNotActiveException("your account is deactivated, contact admin");
        }
        
        // check if password matches
        if(passwordEncoder.matches(req.password(), user.getPassword())){
            
            String token = jwtUtil.generateToken(user);
            String refresh = jwtUtil.generateRefreshToken(user);
            String role = user.getRole().toString();

            RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
            refreshTokenEntity.setRefreshToken(refresh);
            refreshTokenEntity.setUserEntity(user);
            user.addRefreshToken(refreshTokenEntity);

            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(
                "success",
                "user login is successfull",
                token,
                refresh,
                role
            );

            return loginResponseDTO;
        }
        return null;
    }


        public void changePassword(ChangePasswordDTO changePasswordDTO){
            Authentication auth = SecurityContextHolder
                                .getContext()
                                .getAuthentication();
                if (auth == null || !auth.isAuthenticated()){
                    throw new RuntimeException("User not authenticated"); // user not authenticated exception 
                }

                UserEntity user = (UserEntity) auth.getPrincipal();
                
                // validate password
                if (passwordEncoder.matches(changePasswordDTO.oldPassword(), user.getPassword())){
                    user.setPassword(passwordEncoder.encode(changePasswordDTO.newPassword()));
                }else{
                    throw new RuntimeException("password change was unsuccessfull");
                }
        }


        @Transactional
        public void resetPassword(EmailDTO emailDTO){
        UserEntity user = userRepo.findByEmail(emailDTO.email())
                                        .orElseThrow(() -> new RuntimeException("user not found"));

        // generate, save and send reset token to user email address
       String resetToken = UUID.randomUUID().toString();
        try{

        user.getPasswordResetEntity().setRestToken(resetToken);
        userRepo.save(user);
            
        emailService.sendMail(
            user.getEmail(), 
            "Password reset",
           "You have requested a password reset. click on the link provided to reset your password. www.frontendHostedpage.html?token=" + resetToken   
        );
       } catch(Exception e){
        throw new RuntimeException("Unable to send email"); 
       }
    }


        public void confirmPasswordReset(ConfirmPasswordResetDTO confirmPasswordResetDTO) {
            // check validity of token by retrieving from DB

            String token = null;
            
        }

}

    








