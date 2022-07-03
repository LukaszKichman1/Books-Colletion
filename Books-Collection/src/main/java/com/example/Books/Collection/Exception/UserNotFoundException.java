package com.example.Books.Collection.Exception;

public class UserNotFoundException extends RuntimeException {

    private String errorMessage;

    public UserNotFoundException (String message) {
        super(message);
    }


}

