package com.jeewaeducation.user_management.exception;

public class DuplicateKeyException extends RuntimeException {
    public DuplicateKeyException(String message) {
        super(message);
    }
}
