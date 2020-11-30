package com.example.web;

import com.example.exceptions.InvalidCredentials;
import com.example.exceptions.UserAlreadyExistsWithEmail;
import com.example.models.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/jobportal/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User user) throws UserAlreadyExistsWithEmail {

        return userService.createUser(user);
    }
    @PostMapping("/update")
    public User updateUser(@RequestBody User user){
        return userService.update(user);
    }

    @PostMapping("/login")
    public User doLogin(@RequestBody User user) throws InvalidCredentials {
        return userService.doLoin(user);
    }
}
