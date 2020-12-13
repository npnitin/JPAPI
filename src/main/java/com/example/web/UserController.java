package com.example.web;

import com.example.exceptions.InvalidCredentials;
import com.example.exceptions.UserAlreadyExistsWithEmail;
import com.example.models.User;
import com.example.service.UserService;
import com.pcloud.sdk.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public User doLogin(@RequestBody User user) throws InvalidCredentials, IOException, ApiError {
        return userService.doLoin(user);
    }
    @GetMapping("/validateEmail/{userId}")
    public String validateEmail(@PathVariable("userId") String userId){
        userService.activateUser(userId);
        return "https://referraljobz.com";
    }
    @GetMapping("/image/{path}")
    public ResponseEntity<Resource> getImage(@PathVariable("path") String imagePath) throws IOException {
        File file = new File(imagePath);

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
    @GetMapping("/resume")
    public ResponseEntity<Resource> getResume(@RequestParam("path") String resumePath) throws IOException {
       File file = new File(resumePath);
       URL url = new URL(resumePath);
        InputStreamResource resource = new InputStreamResource(url.openStream());

        return ResponseEntity.ok()
                .contentLength(resumePath.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
