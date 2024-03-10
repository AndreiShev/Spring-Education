package com.example.tasktracker.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractValidationHandler<T, Validator> {
    private final Class<T> validationClass;
    private final org.springframework.validation.Validator validator;

    public AbstractValidationHandler(Class<T> validationClass, org.springframework.validation.Validator validator) {
        this.validationClass = validationClass;
        this.validator = validator;
    }

     public T validateEntity(T body) {
         Errors errors = new BeanPropertyBindingResult(body, this.validationClass.getName());
         this.validator.validate(body, errors);

         if (errors == null || errors.getAllErrors().isEmpty()) {
             return body;
         } else {
             List<String> errorMessages = new ArrayList<>();
             for (ObjectError item: errors.getAllErrors()) {
                 errorMessages.add(item.getDefaultMessage());
             }
             String errorMessage = String.join("; ", errorMessages);
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
         }
     }
}
