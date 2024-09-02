package com.connectritam.fooddonation.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.factory.Mappers;

import com.connectritam.fooddonation.userservice.dto.CreateUsersDTO;
import com.connectritam.fooddonation.userservice.dto.UsersDTO;
import com.connectritam.fooddonation.userservice.model.Users;
import com.fasterxml.jackson.annotation.JsonInclude;

@Mapper
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UsersDTO toDTO(Users user);

    CreateUsersDTO toCreateUsersDTO(Users user);

    UsersDTO toFullDTO(Users user);

    @Mapping(target = "password", ignore = true)
    Users toEntity(UsersDTO userDTO);

    Users toFullEntity(CreateUsersDTO user);

}