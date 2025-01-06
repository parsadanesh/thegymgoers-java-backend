package com.thegymgoers_java.app.controller;

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

    private final UserService userService;

    @Autowired
    public UserContoller(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user){
//        try {
            User newUser = userService.register(user);
            if(newUser == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Reg failed");
            }
//        }catch (Exception e) {
//            System.out.println(e);
//        }


        return ResponseEntity.ok("User Reg Successful");
    }
}
