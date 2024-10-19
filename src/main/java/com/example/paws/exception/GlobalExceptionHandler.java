package com.example.paws.rest;

import com.example.paws.exception.InvalidCredentialsException;
import com.example.paws.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("USER_ALREADY_EXISTS", ex.getMessage(), HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("INVALID_CREDENTIALS", ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("SERVER_ERROR", "An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Inner class for structured error response
    public static class ErrorResponse {
        private String code;
        private String message;
        private int status;

        public ErrorResponse(String code, String message, int status) {
            this.code = code;
            this.message = message;
            this.status = status;
        }

        // Getters and Setters (optional)
    }
}
