package com.example.Books.Collection.Exception;

public class InvalidTokenException extends RuntimeException {

    private String errorMessage;

    public InvalidTokenException(String message) {
        super(message);
    }


}
