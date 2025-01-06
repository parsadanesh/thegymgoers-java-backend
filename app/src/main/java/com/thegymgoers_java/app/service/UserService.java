package com.thegymgoers_java.app.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public User register(User user){


        if(user.getUsername() == null || user.getUsername().trim().isEmpty() ||
                user.getEmailAddress() == null || user.getEmailAddress().trim().isEmpty()){
            // throw some error
            throw new IllegalArgumentException("User details cannot not be empty or null");
//            System.out.println("invalid username or email");
        }

        if(!userRepository.findByUsername(user.getUsername()).isEmpty() || !userRepository.findByEmailAddress(user.getEmailAddress()).isEmpty()){
            return null;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User addedUser = userRepository.save(user);

//        System.out.println(addedUser);

        return addedUser;
    }

    public User login(User userLogin){
        if(!userRepository.findByUsername(userLogin.getUsername()).isEmpty()){
            User user = userRepository.findByUsername(userLogin.getUsername()).get(0);

            if (!(passwordEncoder.matches(userLogin.getPassword(), user.getPassword()))){
                throw new IllegalArgumentException("Incorrect password: " + userLogin.getPassword());
            }

            return user;
        }else {
            throw new IllegalArgumentException("Incorrect Username: " + userLogin.getUsername());
        }
    }

}
