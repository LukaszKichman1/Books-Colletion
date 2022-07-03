package com.example.Books.Collection.Exception;

public class UserCanNotBeCreateException extends RuntimeException {

    private String errorMessage;

    public UserCanNotBeCreateException(String message) {
        super(message);
    }


}

