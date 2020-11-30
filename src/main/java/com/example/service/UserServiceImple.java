package com.example.service;

import com.example.exceptions.InvalidCredentials;
import com.example.exceptions.UserAlreadyExistsWithEmail;
import com.example.models.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public User createUser(User user) throws UserAlreadyExistsWithEmail {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser == null){
            return userRepository.save(user);
        }else{
            throw new UserAlreadyExistsWithEmail("User already exists with email:"+user.getEmail());
        }

    }

    @Override
    public User doLoin(User user) throws InvalidCredentials {

        User existingUser = userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
        if(null!=existingUser){
            return existingUser;
        }
       throw new InvalidCredentials("Invalid credentials");
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }
}
