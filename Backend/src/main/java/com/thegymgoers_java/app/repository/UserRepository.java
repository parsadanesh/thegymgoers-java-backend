package com.thegymgoers_java.app.repository;

import com.thegymgoers_java.app.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAddress(String emailAddress);
}
