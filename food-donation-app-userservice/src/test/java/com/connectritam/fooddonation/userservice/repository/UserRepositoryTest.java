package com.connectritam.fooddonation.userservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.connectritam.fooddonation.userservice.model.Users;
import com.connectritam.fooddonation.userservice.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("test") // This activates application-test.properties
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:13.2-alpine:///testdb"
})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void testSaveAndFindUser() {
        UUID userId = UUID.randomUUID();
        Users user = new Users();
        // user.setId(userId);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setMobile("00000000000");
        user.setPassword("secure");
        user.setRole("DONOR");

        try {
            userRepository.save(user);
            entityManager.flush();
            entityManager.clear(); // Clear the persistence context
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertThat(user.getId()).isNotNull();
        System.out.println("User ID!" + user.getId().toString());
        Optional<Users> foundUser = userRepository.findById(user.getId());
        if (foundUser.isEmpty()) {
            System.out.println("User not found");
        }

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void testFindUserById_whenUserNotFound() {

    }
}