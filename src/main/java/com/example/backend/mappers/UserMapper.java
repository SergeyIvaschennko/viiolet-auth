package com.example.backend.mappers;

import com.example.backend.dtos.UserDto;
import com.example.backend.entites.SignUpDto;
import com.example.backend.entites.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(UserEntity user);

    @Mapping(target = "password", ignore = true)
    UserEntity signUpToUser(SignUpDto signUpDto);

}
