package com.jeewaeducation.user_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ForeignKeyConstraintViolationException extends RuntimeException {
    public ForeignKeyConstraintViolationException(String message) {
        super(message);
    }
}