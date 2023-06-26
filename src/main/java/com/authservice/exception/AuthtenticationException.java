package com.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthtenticationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public AuthtenticationException(String message) {
        super(message);
    }
}

