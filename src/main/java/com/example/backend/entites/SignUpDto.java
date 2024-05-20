package com.example.backend.entites;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDto {

//    @NotEmpty
    private String username;

//    @NotEmpty
    private String email;
//    @NotEmpty
    private char[] password;

}
