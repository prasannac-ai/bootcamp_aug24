package com.connectritam.fooddonation.userservice.util;

import org.junit.jupiter.api.Test;

import com.connectritam.fooddonation.userservice.util.HashGenerator;

import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

public class HashGeneratorTest {

    @Test
    public void testGenerateETag_withValidInput() {
        // Given
        String data = "My unit test helps to find bugs early. Additionally it helps to enjoy my vacation peacefully.";

        // When

        // Then
        // Manually compute the expected hash using SHA-256 and Base64 encoding

    }

    @Test
    public void testGenerateETag_withEmptyInput() {
        // Given

        // When

        // Then
        // Manually compute the expected hash for an empty input

    }

    @Test
    public void testGenerateETag_withNullInput() {
        // Given
        byte[] responseBody = null;

        // When / Then
        // Since the method does not handle null, it will throw a NullPointerException.
        // this just for demonstration on the bug in the code.
        // In a real-world scenario, you should handle this case in the code and NPE
        // should not be thrown.
        assertThrows(NullPointerException.class, () -> {
            HashGenerator.generateETag(responseBody);
        });
    }
}