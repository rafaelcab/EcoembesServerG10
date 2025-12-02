package com.ecoembes.api.facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        String msg = ex.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (msg.contains("no encontrada") || msg.contains("no encontrado")) {
            status = HttpStatus.NOT_FOUND;
        } else if (msg.contains("Token") || msg.contains("Credenciales")) {
            status = HttpStatus.UNAUTHORIZED; // Error 401 (Token mal)
        } else if (msg.contains("requerido")) {
            status = HttpStatus.BAD_REQUEST;  // Error 400 (Falta dato)
        }

        return ResponseEntity.status(status).body(Map.of("error", msg));
    }
}