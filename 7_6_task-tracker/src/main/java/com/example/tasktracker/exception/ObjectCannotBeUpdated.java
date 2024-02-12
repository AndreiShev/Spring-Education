package com.example.tasktracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class ObjectCannotBeUpdated extends RuntimeException {
    public ObjectCannotBeUpdated(String message) {
        super(message);
    }
}
