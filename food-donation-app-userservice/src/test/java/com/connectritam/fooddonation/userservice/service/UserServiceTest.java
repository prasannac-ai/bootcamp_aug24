package com.connectritam.fooddonation.userservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    void testFindUserById_whenUserNotFound() {
        UUID id = UUID.randomUUID();

        // Mock the behavior to return an empty Optional
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Call the method under test
        Optional<Users> foundUser = userService.getUserById(id);

        // Assert that the result is empty
        assertFalse(foundUser.isPresent());

    }

    @Test
    void getUserById_shouldReturnUser() {
        UUID id = UUID.randomUUID();
        Users user = new Users();
        user.setName("Kiran");
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Optional<Users> result = userService.getUserById(id);
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("Kiran", result.get().getName());
        assertTrue(result.isPresent());
    }

}