package com.connectritam.fooddonation.userservice.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.connectritam.fooddonation.userservice.dto.CreateUsersDTO;
import com.connectritam.fooddonation.userservice.dto.UsersDTO;
import com.connectritam.fooddonation.userservice.exception.ErrorResponse;
import com.connectritam.fooddonation.userservice.exception.ResourceNotFoundException;
import com.connectritam.fooddonation.userservice.mapper.UserMapper;
import com.connectritam.fooddonation.userservice.model.Users;
import com.connectritam.fooddonation.userservice.service.UserService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UsersDTO>> getUsers() {
        List<UsersDTO> userList = userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable UUID id) {
        Users user = userService.getUserById(id);
        try {
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<UsersDTO> createUser(@RequestBody CreateUsersDTO userDTO) {

        Users createdUser = userService.createUser(userDTO);
        UsersDTO userDTOUpdated = UserMapper.INSTANCE.toDTO(createdUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(userDTOUpdated);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UsersDTO> updateUser(@PathVariable UUID id, @RequestBody CreateUsersDTO userDetailsDTO) {
        try {
            Users updatedUser = userService.updateUser(id, userDetailsDTO);

            UsersDTO userDTOUpdated = UserMapper.INSTANCE.toDTO(updatedUser);

            return ResponseEntity.ok(userDTOUpdated);
        } catch (RuntimeException e) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
