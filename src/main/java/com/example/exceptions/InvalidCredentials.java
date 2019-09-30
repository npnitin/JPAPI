package com.example.exceptions;

public class InvalidCredentials extends Exception {

    private String message;

    public InvalidCredentials(String message){
        super(message);
    }
}
