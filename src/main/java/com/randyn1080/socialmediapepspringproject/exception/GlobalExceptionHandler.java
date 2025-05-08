package com.randyn1080.socialmediapepspringproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GlobalExceptionHandler is a centralized exception handling class for the application.
 * It handles various types of exceptions thrown by the application and maps them
 * to appropriate HTTP responses with relevant status codes and error messages.
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles the exception when a duplicate username is detected in the system.
     * This method intercepts exceptions of type DuplicateUsernameException and
     * returns an appropriate HTTP response with status code 409 (Conflict) along
     * with the exception message.
     *
     * @param e the DuplicateUsernameException being handled
     * @return a ResponseEntity containing the HTTP status code 409 and the exception message
     */
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<Object> handleDuplicateUsername(DuplicateUsernameException e) {
        logger.warn("Duplicate username exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    /**
     * Handles the exception when an invalid password is detected in the system.
     * This method intercepts exceptions of type InvalidPasswordException and
     * returns an appropriate HTTP response with status code 400 (Bad Request)
     * along with the exception message.
     *
     * @param e the InvalidPasswordException being handled
     * @return a ResponseEntity containing the HTTP status code 400 and the exception message
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> handleInvalidPassword(InvalidPasswordException e) {
        logger.warn("Invalid password exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Handles the exception when an invalid username is detected in the system.
     * This method intercepts exceptions of type InvalidUsernameException and
     * returns an appropriate HTTP response with status code 400 (Bad Request)
     * along with the exception message.
     *
     * @param e the InvalidUsernameException being handled
     * @return a ResponseEntity containing the HTTP status code 400 and the exception message
     */
    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<Object> handleInvalidUsername(InvalidUsernameException e) {
        logger.warn("Invalid username exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Handles the exception when authentication fails in the system.
     * This method intercepts exceptions of type AuthenticationFailedException
     * and returns an appropriate HTTP response with status code 401 (Unauthorized)
     * along with the exception message.
     *
     * @param e the AuthenticationFailedException being handled
     * @return a ResponseEntity containing the HTTP status code 401 and the exception message
     */
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Object> handleAuthenticationFailed(AuthenticationFailedException e) {
        logger.warn("Authentication failed exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Handles the exception when an invalid message is detected in the system.
     * This method intercepts exceptions of type InvalidMessageException and
     * returns an appropriate HTTP response with status code 400 (Bad Request)
     * along with the exception message.
     *
     * @param e the InvalidMessageException being handled
     * @return a ResponseEntity containing the HTTP status code 400 and the exception message
     */
    @ExceptionHandler(InvalidMessageException.class)
    public ResponseEntity<Object> handleInvalidMessage(InvalidMessageException e) {
        logger.warn("Invalid message exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Handles unexpected exceptions that are not explicitly handled by other methods.
     * This method intercepts exceptions of type Exception and returns a generic
     * HTTP response with status code 500 (Internal Server Error) along with a
     * message indicating an error has occurred.
     *
     * @param e the Exception being handled
     * @return a ResponseEntity containing the HTTP status code 500 and a generic error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception e) {
        logger.error("Unexpected exception: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + e.getMessage());
    }
}
