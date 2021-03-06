package org.sysc.ama.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.sysc.ama.controller.exception.BadRequestError;
import org.sysc.ama.controller.exception.EntityNotFoundException;
import org.sysc.ama.controller.exception.UnauthorizedAccessException;
import org.sysc.ama.controller.exception.UserCreationException;
import org.sysc.ama.model.UserFollowException;
import org.sysc.ama.model.UserUnfollowException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlingAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String processValidationError(ConstraintViolationException ex) {
        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations())
        {
            String propertyPath = violation.getPropertyPath().toString();
            message.append("Error in value \"").append(violation.getInvalidValue()).append("\" : ").append(violation.getMessage()).append("\n");
        }
        return message.toString();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> processValidationError(EntityNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND ).body("Bad Request: No " + ex.getEntityName() +
                " exists with that id");
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> processAuthorizationError(UnauthorizedAccessException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized: " + ex.getMessage());
    }

    @ExceptionHandler({BadRequestError.class, UserUnfollowException.class, UserCreationException.class, UserFollowException.class })
    public ResponseEntity<String> processBadRequestError (Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: " + ex.getMessage());
    }

}
