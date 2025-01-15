package com.thegymgoers_java.app.service;

import com.thegymgoers_java.app.model.GymGroup;
import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.payload.request.NewGymGroupRequest;
import com.thegymgoers_java.app.repository.GymGroupRepository;
import com.thegymgoers_java.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GymGroupService {

    @Autowired
    GymGroupRepository gymGroupRepository;

    @Autowired
    UserRepository userRepository;

    public GymGroup createGymGroup(String username, NewGymGroupRequest newGymGroupRequest) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("User details cannot not be empty or null");
        }

        if (newGymGroupRequest.getGroupName() == null || newGymGroupRequest.getGroupName().trim().isEmpty()) {
            throw new IllegalArgumentException("GymGroup must have a name");
        }

        if(gymGroupRepository.findByGroupName(newGymGroupRequest.getGroupName()).isPresent()){
            throw new Exception("GymGroup with that name exists");
        }

        GymGroup gymGroup = new GymGroup();
        gymGroup.setGroupName(newGymGroupRequest.getGroupName());
        User admin = userRepository.findByUsername(username).get();
        gymGroup.addAdmins(admin.getId());
        gymGroup.addMembers(admin.getId());
        return gymGroupRepository.save(gymGroup);
    }
}
