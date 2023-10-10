package com.example.platorium_assignment.error;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JsonErrorHandler {

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<String> handleDeserializationError(JsonMappingException e) {
        return ResponseEntity.badRequest().body("Invalid input provided for the discount type.");
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<String> handleParseError(JsonParseException e) {
        return ResponseEntity.badRequest().body("Error parsing the provided JSON.");
    }
}