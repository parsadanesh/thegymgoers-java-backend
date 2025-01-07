package com.thegymgoers_java.app.controller;

import com.thegymgoers_java.app.model.Workout;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class UserContoller {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserService userService;

    @Autowired
    public UserContoller(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users/{username}/workouts")
    public ResponseEntity<?> getWorkouts(@PathVariable String username){
        System.out.println(username);
        List<Workout> workoutList = userService.getWorkouts(username);
        if(!(workoutList == null)){
            return new ResponseEntity<>(workoutList, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

}
