package com.example.Books.Collection.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserCanNotBeCreateException.class)
    public ResponseEntity<Object> handleUserCanNotBeCreateException(UserCanNotBeCreateException ex, WebRequest webRequest) {
        return new ResponseEntity<Object>(new RequestError(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserCanNotBeActivationException.class)
    public ResponseEntity<Object> handleUserCanNotBeActivationException(UserCanNotBeActivationException ex, WebRequest webRequest) {
        return new ResponseEntity<Object>(new RequestError(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest webRequest) {
        return new ResponseEntity<Object>(new RequestError(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now()), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex, WebRequest webRequest) {
        return new ResponseEntity<Object>(new RequestError(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now()), HttpStatus.NOT_ACCEPTABLE);
    }



}