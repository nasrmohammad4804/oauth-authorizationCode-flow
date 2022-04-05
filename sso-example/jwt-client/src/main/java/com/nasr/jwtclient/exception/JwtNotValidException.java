package com.nasr.jwtclient.exception;

public class JwtNotValidException extends RuntimeException{

    public JwtNotValidException(String message) {
        super(message);
    }
}
