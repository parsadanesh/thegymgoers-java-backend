package com.thegymgoers_java.app.service;

import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(User user){
        User addedUser = userRepository.save(user);

        if(user.getUsername() == null || user.getUsername().trim().isEmpty() ||
                user.getEmailAddress() == null || user.getEmailAddress().trim().isEmpty()){
            // throw some error
            throw new IllegalArgumentException("User details cannot not be empty or null");
//            System.out.println("invalid username or email");
        }

        if(!userRepository.findByUsername(user.getUsername()).isEmpty() || !userRepository.findByEmailAddress(user.getEmailAddress()).isEmpty()){
            return null;
        }

        System.out.println(addedUser);

        return addedUser;
    }
}
