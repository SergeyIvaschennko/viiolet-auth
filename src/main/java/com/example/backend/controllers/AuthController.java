package com.example.backend.controllers;

import com.example.backend.config.UserAuthenticationProvider;
import com.example.backend.dtos.CredentialsDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entites.SignUpDto;
import com.example.backend.entites.UserEntity;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        logger.info("Gen Username: {}", credentialsDto.getUsername());
        UserDto userDto = userService.login(credentialsDto);
        logger.info("Main steppp: {}", userDto.getUsername());
//        userDto.setToken(userAuthenticationProvider.createToken(userDto.getLogin()));
        userDto.setToken(userAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid SignUpDto user) {
        UserDto createdUser = userService.register(user);
//        createdUser.setToken(userAuthenticationProvider.createToken(user.getLogin()));
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
    }

}
