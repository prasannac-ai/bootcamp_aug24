package com.connectritam.fooddonation.userservice.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

        // Verify that the method was called with the correct parameters
        verify(userRepository).findById(id);
    }

    @Test
    void getUserById_shouldReturnUser() {
        UUID id = UUID.randomUUID();
        Users user = new Users();
        user.setName("prasanna");
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Optional<Users> result = userService.getUserById(id);
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        assertEquals("prasanna", result.get().getName());

        assertTrue(result.isPresent());
    }


    

}