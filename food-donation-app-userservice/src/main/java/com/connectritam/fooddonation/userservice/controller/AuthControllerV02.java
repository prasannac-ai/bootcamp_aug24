package com.connectritam.fooddonation.userservice.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.connectritam.fooddonation.userservice.dto.CreateUsersDTO;
import com.connectritam.fooddonation.userservice.mapper.UserMapper;
import com.connectritam.fooddonation.userservice.model.Users;
import com.connectritam.fooddonation.userservice.service.CustomUserDetailsService;
import com.connectritam.fooddonation.userservice.service.UserService;
import com.connectritam.fooddonation.userservice.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;

import com.connectritam.fooddonation.userservice.model.AuthenticationResponse;

@RestController
@RequestMapping("/api/v0.2/auth")
public class AuthControllerV02 {

    private static final Logger logger = LoggerFactory.getLogger(AuthControllerV01.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired // STUDENT to review @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<CreateUsersDTO> createUser(@RequestBody CreateUsersDTO userDTO) {

        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Users createdUser = userService.createUser(userDTO);

        CreateUsersDTO userDTOUpdated = UserMapper.INSTANCE.toCreateUsersDTO(createdUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(userDTOUpdated); 
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody Users user) {
        String jwt = null;
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            jwt = jwtUtil.generateToken(userDetails);

        } catch (BadCredentialsException e) {
            // STUDENT to implement AuthenticationException
            throw new RuntimeException("Incorrect username or password");
        } catch (ExpiredJwtException e) {
            logger.error("Token validation failed for user: " + user.getEmail() + ". Generating new token.", e);
        }

        return ResponseEntity.ok(new AuthenticationResponse(jwt));

    }

}