package com.nasr.jwtclient.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = JwtNotValidException.class)
    public ResponseEntity<String> jwtNotValid(JwtNotValidException e){
        return ResponseEntity
                .badRequest().body(e.getMessage());
    }
}
