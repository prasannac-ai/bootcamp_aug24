package com.connectritam.fooddonation.userservice.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashGenerator {

    private HashGenerator() {
        // Private constructor to prevent instantiation
    }

    public static String generateETag(byte[] responseBody) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(responseBody);
            return "\"" + Base64.getEncoder().encodeToString(hash) + "\"";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
