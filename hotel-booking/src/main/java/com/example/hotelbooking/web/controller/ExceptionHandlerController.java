package com.example.hotelbooking.web.controller;

import com.example.hotelbooking.exception.BookingException;
import com.example.hotelbooking.exception.EntityAlreadyExistsException;
import com.example.hotelbooking.exception.EntityNotFoundException;
import com.example.hotelbooking.web.model.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(exception.getLocalizedMessage()));
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(EntityAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(exception.getLocalizedMessage()));
    }

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(BookingException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(exception.getLocalizedMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> notValid(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String errorMessage = String.join("; ", errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));
    }
}
