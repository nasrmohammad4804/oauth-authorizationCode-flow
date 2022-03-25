package com.nasr.keycloakclient.exception;

public class JwtNotValidToken extends RuntimeException{
    public JwtNotValidToken(String message) {
        super(message);
    }
}
