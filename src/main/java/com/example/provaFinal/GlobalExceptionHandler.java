package com.example.provaFinal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String,Object>> handle(ResponseStatusException ex) {

        Map<String,Object> erro = new HashMap<>();
        erro.put("status", ex.getStatusCode().value());
        erro.put("message", ex.getReason());

        return new ResponseEntity<>(erro, ex.getStatusCode());
    }
}