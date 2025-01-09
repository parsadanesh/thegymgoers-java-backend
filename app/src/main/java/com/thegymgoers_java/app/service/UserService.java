package com.thegymgoers_java.app.service;

import com.thegymgoers_java.app.model.Workout;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public User register(User user){

        // Throws an exception if the user's email/username used to register is null or empty
        if(user.getUsername() == null || user.getUsername().trim().isEmpty() ||
                user.getEmailAddress() == null || user.getEmailAddress().trim().isEmpty()){
            throw new IllegalArgumentException("User details cannot not be empty or null");
        }

        // Returns null if a user already exists with the same email/username
        if(!userRepository.findByUsername(user.getUsername()).isEmpty() || !userRepository.findByEmailAddress(user.getEmailAddress()).isEmpty()){
            return null;
        }

        // Encodes the password before saving the user to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User addedUser = userRepository.save(user);
        return addedUser;
    }

    public User login(User userLogin){

        // Throws an exception if the user's email/username used to register is null or empty
        if(userLogin.getUsername() == null || userLogin.getUsername().trim().isEmpty() ||
                userLogin.getEmailAddress() == null || userLogin.getEmailAddress().trim().isEmpty()){
            throw new IllegalArgumentException("User details cannot not be empty or null");
        }

        // Checks if a user exists with the same username
        if(!userRepository.findByUsername(userLogin.getUsername()).isEmpty()){
            User user = userRepository.findByUsername(userLogin.getUsername()).get();

            // method checks if the raw password matches the stored encoded password
            if (!(passwordEncoder.matches(userLogin.getPassword(), user.getPassword()))){
                throw new IllegalArgumentException("Incorrect password: " + userLogin.getPassword());
            }

            return user;
        }else {
            throw new IllegalArgumentException("Incorrect Username: " + userLogin.getUsername());
        }
    }

    public List<Workout> getWorkouts(String username){
        User user = userRepository.findByUsername(username).get();
//        System.out.println(user);

        return user.getWorkoutsList();
    }

    public User addWorkout(String username, Workout workoutToAdd){
        // Find the user
        User user = null;
        workoutToAdd.setDataCreated(LocalDateTime.now().toString());

        if(userRepository.findByUsername(username).isPresent()){
            user = userRepository.findByUsername(username).get();
            // Add the new workout
            user.addWorkout(workoutToAdd);
            // Save updates to database
            userRepository.save(user);
        }

        return user;


    }
}
