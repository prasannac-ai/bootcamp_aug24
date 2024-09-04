package com.connectritam.fooddonation.userservice.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectritam.fooddonation.userservice.dto.UsersDTO;
import com.connectritam.fooddonation.userservice.exception.ErrorKey;
import com.connectritam.fooddonation.userservice.exception.ResourceNotFoundException;
import com.connectritam.fooddonation.userservice.mapper.UserMapper;
import com.connectritam.fooddonation.userservice.model.Users;
import com.connectritam.fooddonation.userservice.service.UserService;
import com.connectritam.fooddonation.userservice.util.HashGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v2/users")
@Tag(name = "Version 2", description = "Version 2 of the User API")
public class UserControllerV2 {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerV2.class);

    private final UserService userService;

    public UserControllerV2(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UsersDTO>> getUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        List<UsersDTO> userList = userService.getAllUsers(page, size, sortField, sortDirection);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable UUID id,
            @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false) String ifNoneMatch) {

        Users user = userService.getUserById(id);

        UsersDTO userDTO = UserMapper.INSTANCE.toDTO(user);

        // Serialize the userDTO to a byte array
        byte[] responseBody;
        try {
            responseBody = new ObjectMapper().writeValueAsBytes(userDTO);
        } catch (JsonProcessingException e) {
            // STUDENT add your own Exception as a best practice Example :
            // JsonSerializationException and capture that in UserServicesException
            throw new RuntimeException(ErrorKey.JSON_SER_ERR, e);
        }

        // Generate ETag
        String eTag = HashGenerator.generateETag(responseBody);

        // Check if ETag matches
        if (eTag.equals(ifNoneMatch)) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
        }

        return ResponseEntity.ok().eTag(eTag).body(userDTO);

    }

}
