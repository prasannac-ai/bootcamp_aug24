package com.connectritam.fooddonation.userservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.connectritam.fooddonation.userservice.dto.UsersDTO;
import com.connectritam.fooddonation.userservice.model.Users;
import com.connectritam.fooddonation.userservice.service.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UsersDTO user;
    private Users user1;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UsersDTO();
        user.setId(UUID.randomUUID());
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        user1 = new Users();
        user1.setId(UUID.randomUUID());
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
    }

    @Test
    void testGetUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void testGetUserById() throws Exception {
        UUID userId = user.getId();
        when(userService.getUserById(userId)).thenReturn(Optional.of(user1));

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(UsersDTO.class))).thenReturn(user1);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testUpdateUser() throws Exception {
        UUID userId = user.getId();
        Users updatedUser = new Users();
        updatedUser.setId(userId);
        updatedUser.setName("John Updated");
        updatedUser.setEmail("john.updated@example.com");
        when(userService.updateUser(eq(user.getId()), any(UsersDTO.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Updated\"," +
                        "\"email\":\"john.updated@example.com\"," +
                        "\"password\":\"newpassword123\"," +
                        "\"role\":\"Admin\"," +
                        "\"mobile\":\"1234567890\"," +
                        "\"jwtToken\":\"newJwtToken\"," +
                        "\"address\":\"123 Main St, Anytown\"," +
                        "\"latitude\":34.0522," +
                        "\"longitude\":-118.2437}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

    @Test
    void testDeleteUser() throws Exception {
        UUID userId = user.getId();
        doNothing().when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/v1/users/{id}", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        UUID userId = user.getId();
        doThrow(new RuntimeException("User not found")).when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}