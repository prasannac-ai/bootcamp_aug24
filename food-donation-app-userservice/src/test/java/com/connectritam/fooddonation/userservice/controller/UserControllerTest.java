package com.connectritam.fooddonation.userservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.connectritam.fooddonation.userservice.model.Users;
import com.connectritam.fooddonation.userservice.service.UserService;

//TODO FIXIT
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private Users user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new Users();
        user.setId(UUID.randomUUID());
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
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
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(Users.class))).thenReturn(user);

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
        when(userService.updateUser(eq(userId), any(Users.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Updated\"," +
                        "\"email\":\"john.updated@example.com\"," +

                        "\"role\":\"Admin\"," +
                        "\"mobile\":\"1234567890\"," +
                        "\"address\":\"123 Main St, Anytown\"," +
                        "\"latitude\":34.0522," +
                        "\"longitude\":-118.2437}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

    @Test
    void testUpdateUseNotFound() throws Exception {
        UUID userId = user.getId();
        Users updatedUser = new Users();

        when(userService.updateUser(eq(userId), any(Users.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/api/v1/users/{id}", updatedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Updated\"," +
                        "\"email\":\"john.updated@example.com\"," +

                        "\"role\":\"Admin\"," +
                        "\"mobile\":\"1234567890\"," +
                        "\"address\":\"123 Main St, Anytown\"," +
                        "\"latitude\":34.0522," +
                        "\"longitude\":-118.2437}"))
                .andExpect(status().isNotFound());
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
        doThrow(new RuntimeException("Internal Server")).when(userService).deleteUser(userId);

        mockMvc.perform(delete("/api/v1/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testUpdateUserException() throws Exception {
        UUID userid = user.getId();
        doThrow(new RuntimeException("User not  found")).when(userService).updateUser(any(UUID.class),
                any(Users.class));

        mockMvc.perform(put("/api/v1/users/{id}", userid).contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John Updated\"," +
                        "\"email\":\"john.updated@example.com\"," +
                        "\"role\":\"Admin\"," +
                        "\"mobile\":\"1234567890\"," +
                        "\"address\":\"123 Main St, Anytown\"," +
                        "\"latitude\":34.0522," +
                        "\"longitude\":-118.2437}"))
                .andExpect(status().isNotFound());
    }
}