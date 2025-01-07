package com.thegymgoers_java.app.controller;

import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user){
        try {
            // attempts to register the new user
            User newUser = userService.register(user);
            // returns null if a user already exists with the email/username used to register
            if(newUser == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with that email/username already exists");
            }
        }catch (Exception e) {
            // Exception thrown when the user's email/username is either empty or null
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Enter A Valid User details");
        }
        return ResponseEntity.ok("User Reg Successful");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody User userToLogin){
        try{
            // Attempts to login with a given user's details
            User user = userService.login(userToLogin);

            // If a user is returned, the login was successful
            if(!(user == null)) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }catch (Exception e){
            // Displays an error based on if the details are invalid
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
