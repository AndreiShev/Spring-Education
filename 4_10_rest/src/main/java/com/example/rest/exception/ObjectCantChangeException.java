package com.example.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ObjectCantChangeException extends RuntimeException {
    public ObjectCantChangeException(String message) {
        super(message);
    }
}
