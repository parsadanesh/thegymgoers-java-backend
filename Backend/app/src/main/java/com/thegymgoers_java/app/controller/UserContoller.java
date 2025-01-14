package com.thegymgoers_java.app.controller;

import com.thegymgoers_java.app.model.Workout;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final UserService userService;

    @Autowired
    public UserContoller(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users/{username}/workouts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getWorkouts(@PathVariable String username){

        // Attempting to find list of workouts based on a user's username
        List<Workout> workoutList = userService.getWorkouts(username);

        // Returns the valid list of workouts
        if(workoutList != null){
            return new ResponseEntity<>(workoutList, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/users/{username}/workouts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addWorkout(@PathVariable String username, @Valid @RequestBody Workout workoutToAdd){

        // Attempting to find list of workouts based on a user's username
        User updatedUser = userService.addWorkout(username, workoutToAdd);

        // Returns the valid list of workouts
        if(!(updatedUser == null)){
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("/users/{username}/workouts/{_id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteWorkout(@PathVariable String username, @PathVariable String _id){
        User updatedUser = userService.deleteWorkout(username, _id);

        // Returns the valid list of workouts
        if(!(updatedUser == null)){
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);


    }

}
