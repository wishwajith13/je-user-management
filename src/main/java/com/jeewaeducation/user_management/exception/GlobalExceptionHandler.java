package com.jeewaeducation.user_management.exception;

import com.jeewaeducation.user_management.utility.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardResponse> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(
                new StandardResponse(404, "Not Found", exception.getMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AlreadyAssignedException.class)
    public ResponseEntity<StandardResponse> handleAlreadyAssignedException(AlreadyAssignedException exception) {
        return new ResponseEntity<>(
                new StandardResponse(408, "Conflict", exception.getMessage()), HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<StandardResponse> handleDuplicateKeyException(DuplicateKeyException exception) {
        return new ResponseEntity<>(
                new StandardResponse(404, "Not Found", exception.getMessage()), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ForeignKeyConstraintViolationException.class)
    public ResponseEntity<?> handleForeignKeyConstraintViolationException(ForeignKeyConstraintViolationException ex, WebRequest request) {
        return new ResponseEntity<>(
                new StandardResponse(409, "Conflict", ex.getMessage()), HttpStatus.CONFLICT
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponse> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        StandardResponse response = new StandardResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                errorMap
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
