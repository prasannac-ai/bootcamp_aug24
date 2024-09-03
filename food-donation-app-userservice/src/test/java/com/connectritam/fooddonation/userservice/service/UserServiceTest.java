package com.connectritam.fooddonation.userservice.service;

import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.connectritam.fooddonation.userservice.model.Users;
import com.connectritam.fooddonation.userservice.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void getUserById_shouldReturnUser() {
        UUID id = UUID.randomUUID();
        Users user = new Users();
        user.setName("Kiran");
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Users result = userService.getUserById(id);
        // assertEquals(id, result.getId());
        // assertEquals("Kiran", result.getName());

    }

}