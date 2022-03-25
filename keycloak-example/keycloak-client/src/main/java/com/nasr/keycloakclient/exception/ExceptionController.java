package com.nasr.keycloakclient.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = JwtNotValidToken.class)
    public ResponseEntity<String> jwtNotValidTokenException(JwtNotValidToken e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
