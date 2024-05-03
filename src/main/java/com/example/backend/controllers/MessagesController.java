package com.example.backend.controllers;

import com.example.backend.dtos.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class MessagesController {


    @GetMapping("/messages")
    public ResponseEntity<List<String>> messages() {
        return ResponseEntity.ok(Arrays.asList("fin", "land"));
    }

    @GetMapping("/protected/messages")
    public ResponseEntity<List<String>> protectedMessage() {
        return ResponseEntity.ok(Arrays.asList("adddd", "miin"));
    }

}