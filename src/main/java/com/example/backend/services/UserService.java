package com.example.backend.services;

import com.example.backend.config.UserAuthenticationProvider;
import com.example.backend.dtos.CredentialsDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entites.SignUpDto;
import com.example.backend.entites.UserEntity;
//import com.example.backend.enums.Role;
import com.example.backend.enums.Role;
import com.example.backend.exceptions.AppException;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;//for

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDto intoUserDto(UserEntity user) {
        if ( user == null ) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public UserEntity signUpToUser(SignUpDto signUpDto) {
        if ( signUpDto == null ) {
            return null;
        }

        return UserEntity.builder()
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(new String(signUpDto.getPassword())) // Помните, что хранение пароля в виде char[] не самое безопасное решение
                .build();
    }

    public UserDto login(CredentialsDto credentialsDto) {
        UserEntity user = userRepository.findByUsername(credentialsDto.getUsername())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        String roleName = user.getRole().name();
        String mail = user.getEmail();
        logger.info("Role name2: {}", roleName);
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return intoUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = signUpToUser(userDto);
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        UserEntity savedUser = userRepository.save(user);
        return intoUserDto(savedUser);
    }

    public UserDto findByLogin(String login) {
        UserEntity user = userRepository.findByUsername(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

}