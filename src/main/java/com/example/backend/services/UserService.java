package com.example.backend.services;

import com.example.backend.config.UserAuthenticationProvider;
import com.example.backend.dtos.CredentialsDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entites.SignUpDto;
import com.example.backend.entites.User;
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

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDto intoUserDto(User user) {
        if ( user == null ) {
            return null;
        }
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .role(user.getRole().name())
                .build();
    }

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        String roleName = user.getRole().name();
        logger.info("Role name2: {}", roleName);
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
//            return userMapper.toUserDto(user);
            return intoUserDto(user);
        }
        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));
        User savedUser = userRepository.save(user);
        return intoUserDto(user);
//        return userMapper.toUserDto(savedUser);
    }

    public UserDto findByLogin(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

}