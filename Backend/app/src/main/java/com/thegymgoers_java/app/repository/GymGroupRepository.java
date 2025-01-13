package com.thegymgoers_java.app.repository;

import com.thegymgoers_java.app.model.GymGroup;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GymGroupRepository extends MongoRepository<GymGroup, String> {
}
