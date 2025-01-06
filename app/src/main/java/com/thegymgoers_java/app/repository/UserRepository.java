package com.thegymgoers_java.app.repository;

import com.thegymgoers_java.app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByUsername(String username);

    List<User> findByEmailAddress(String emailAddress);
}
