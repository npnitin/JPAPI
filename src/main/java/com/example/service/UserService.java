package com.example.service;

import com.example.exceptions.InvalidCredentials;
import com.example.exceptions.UserAlreadyExistsWithEmail;
import com.example.models.User;
import com.pcloud.sdk.ApiError;

import java.io.IOException;

public interface UserService {

    public User createUser(User user) throws UserAlreadyExistsWithEmail;
    public User doLoin(User user) throws InvalidCredentials, IOException, ApiError;
    public User update(User user);
    public void activateUser(String userId);
}
