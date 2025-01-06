package com.thegymgoers_java.app.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UserContoller {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserService userService;

    @Autowired
    public UserContoller(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user){
        try {
            User newUser = userService.register(user);
            if(newUser == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with that email/username already exists");
            }
        }catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Enter A Valid User details");
        }
        return ResponseEntity.ok("User Reg Successful");
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody User userToLogin){
        try{
            User user = userService.login(userToLogin);
            if(!(user == null)) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}
