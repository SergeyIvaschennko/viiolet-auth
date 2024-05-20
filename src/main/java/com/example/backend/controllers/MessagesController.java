package com.example.backend.controllers;

import com.example.backend.dtos.MessageDto;
import com.example.backend.entites.UserEntity;
import com.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class MessagesController {

    private UserRepository userRepository;

    @Autowired
    public MessagesController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/find/{username}")
    public ResponseEntity<UserEntity> find(@PathVariable String username) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<String>> messages() {
        return ResponseEntity.ok(Arrays.asList("fin", "land"));
    }

    @GetMapping("/protected/messages")
    public ResponseEntity<List<String>> protectedMessage() {
        return ResponseEntity.ok(Arrays.asList("adddd", "miin"));
    }

}