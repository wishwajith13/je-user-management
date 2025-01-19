package com.jeewaeducation.user_management.exception;

import com.jeewaeducation.user_management.utility.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardResponse> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(404, "Not Found", exception.getMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<StandardResponse> handleDuplicateKeyException(DuplicateKeyException exception) {
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(404, "Not Found", exception.getMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ForeignKeyConstraintViolationException.class)
    public ResponseEntity<?> handleForeignKeyConstraintViolationException(ForeignKeyConstraintViolationException ex, WebRequest request) {
        return new ResponseEntity<>(
                new StandardResponse(409, "Conflict", ex.getMessage()), HttpStatus.CONFLICT
        );
    }
}
