package com.fullstack.api.exception;

import com.fullstack.api.error.PersonError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(PersonServiceException.class)
    public ResponseEntity<PersonError> mapException(PersonServiceException ex) {
        PersonError object = new PersonError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(object, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
