package com.connectritam.fooddonation.userservice.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.connectritam.fooddonation.userservice.dto.CreateUsersDTO;
import com.connectritam.fooddonation.userservice.mapper.UserMapper;
import com.connectritam.fooddonation.userservice.model.Users;
import com.connectritam.fooddonation.userservice.service.UserService;

@RestController
@RequestMapping("/api/v0.1/auth")
public class AuthControllerV01 {

    private static final Logger logger = LoggerFactory.getLogger(AuthControllerV01.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<CreateUsersDTO> createUser(@RequestBody CreateUsersDTO userDTO) {

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Users createdUser = userService.createUser(userDTO);

        CreateUsersDTO userDTOUpdated = UserMapper.INSTANCE.toCreateUsersDTO(createdUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(userDTOUpdated); // NOTE only to demonstrate the hashed password.
    }

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody Users user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect username or password");
        }

        return ResponseEntity.ok().body("Login successful");

    }

}