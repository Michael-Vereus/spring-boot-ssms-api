package com.ver.ssms.exception;

import com.ver.ssms.dto.BodyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // Global Exception Handler :o, i saw this one from the spring.io blog
    public ResponseEntity<BodyResponse<String>> handleGeneralException(Exception e){
        return ResponseEntity.status(400).
                body(BodyResponse.fail(Map.of("An Unexpected Error occured : ", e.getMessage())));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<BodyResponse<String>> handleNotFound(NoHandlerFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BodyResponse.fail(Map.of("Route Error", "The path " + e.getRequestURL() + " does not exist.")));
    }
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<BodyResponse<String>> handleUsernameTaken(UsernameAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(BodyResponse.fail(Map.of("error", e.getMessage())));
    }
}
