package com.connectritam.fooddonation.userservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectritam.fooddonation.userservice.dto.UsersDTO;
import com.connectritam.fooddonation.userservice.service.UserService;

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
    public ResponseEntity<List<UsersDTO>> getUsers(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        List<UsersDTO> userList = userService.getAllUsers(page, size, sortField, sortDirection);
        return ResponseEntity.ok(userList);
    }

}
