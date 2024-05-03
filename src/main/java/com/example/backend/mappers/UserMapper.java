package com.example.backend.mappers;

import com.example.backend.dtos.UserDto;
import com.example.backend.entites.SignUpDto;
import com.example.backend.entites.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
