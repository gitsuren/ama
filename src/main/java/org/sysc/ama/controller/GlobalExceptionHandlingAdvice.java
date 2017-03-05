package org.sysc.ama.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandlingAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<FieldError> processValidationError(ConstraintViolationException ex) {
        List<FieldError> errors = new ArrayList<FieldError>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations())
        {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.add(new FieldError("name",propertyPath,

                    "Invalid "+ propertyPath + "(" + message + ")"));
        }
        return errors;
    }
}
