package com.example.service;

import com.example.exceptions.InvalidCredentials;
import com.example.exceptions.UserAlreadyExistsWithEmail;
import com.example.models.User;

public interface UserService {

    public User createUser(User user) throws UserAlreadyExistsWithEmail;
    public User doLoin(User user) throws InvalidCredentials;

}
