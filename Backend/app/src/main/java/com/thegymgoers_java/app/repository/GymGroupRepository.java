package com.thegymgoers_java.app.repository;

import com.thegymgoers_java.app.model.GymGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GymGroupRepository extends MongoRepository<GymGroup, String> {

    Optional<GymGroup> findByGroupName(String groupName);

    @Query("{ 'members': ?0 }")
    List<GymGroup> findAllByMembersContains(String username);
}
