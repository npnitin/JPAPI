package com.example.exceptions;

public class UserAlreadyExistsWithEmail extends Exception {

    private String errorMessage;

    public UserAlreadyExistsWithEmail(String errorMessage){
       super(errorMessage);
    }
}
