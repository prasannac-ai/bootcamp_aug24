package com.connectritam.fooddonation.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.connectritam.fooddonation.userservice.dto.UsersDTO;
import com.connectritam.fooddonation.userservice.model.Users;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password", ignore = true)
    UsersDTO toDTO(Users user);

    UsersDTO toFullDTO(Users user);

    @Mappings({
            @Mapping(target = "password", ignore = true),

    })
    Users toEntity(UsersDTO userDTO);

    Users toFullEntity(UsersDTO user);

}