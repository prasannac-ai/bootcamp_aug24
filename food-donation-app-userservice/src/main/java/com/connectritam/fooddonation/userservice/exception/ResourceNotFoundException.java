package com.connectritam.fooddonation.userservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(ErrorKey errorKey) {
        super(errorKey.USER_NOT_FOUND);
    }
}