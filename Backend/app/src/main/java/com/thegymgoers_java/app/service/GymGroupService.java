package com.thegymgoers_java.app.service;

import com.thegymgoers_java.app.model.GymGroup;
import com.thegymgoers_java.app.model.User;
import com.thegymgoers_java.app.model.Workout;
import com.thegymgoers_java.app.payload.request.NewGymGroupRequest;
import com.thegymgoers_java.app.repository.GymGroupRepository;
import com.thegymgoers_java.app.repository.UserRepository;
import com.thegymgoers_java.app.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymGroupService {

    @Autowired
    GymGroupRepository gymGroupRepository;

    @Autowired
    UserRepository userRepository;


    public GymGroup createGymGroup(String username, NewGymGroupRequest newGymGroupRequest) throws Exception {
        // Validate username
        ValidationUtil.validateUsername(username);

        // Validate group name
        if (newGymGroupRequest.getGroupName() == null || newGymGroupRequest.getGroupName().trim().isEmpty()) {
            throw new IllegalArgumentException("GymGroup must have a name");
        }

        // Check if a GymGroup with the same name already exists
        if(gymGroupRepository.findByGroupName(newGymGroupRequest.getGroupName()).isPresent()){
            throw new Exception("GymGroup with that name exists");
        }

        // Create new GymGroup
        GymGroup gymGroup = new GymGroup();
        gymGroup.setGroupName(newGymGroupRequest.getGroupName());
        User admin;

        // Check if the user exists
        if(userRepository.findByUsername(username).isPresent()){
            admin = userRepository.findByUsername(username).get();
        } else {
            throw new Exception("User not found");
        }

        // Add user as admin and member of the GymGroup
        gymGroup.addAdmins(admin.getUsername());
        gymGroup.addMembers(admin.getUsername());

        // Save and return the new GymGroup
        return gymGroupRepository.save(gymGroup);
    }

    public GymGroup joinGymGroup(String username, String groupName) throws Exception {
        GymGroup gymGroup;
        User user;

        // Validate username
        ValidationUtil.validateUsername(username);

        // Validate group name
        if (groupName == null || groupName.trim().isEmpty()) {
            throw new IllegalArgumentException("GymGroup must have a name");
        }

        // Check if both user and GymGroup exist
        if(userRepository.findByUsername(username).isPresent() && gymGroupRepository.findByGroupName(groupName).isPresent()){
            gymGroup = gymGroupRepository.findByGroupName(groupName).get();
            user = userRepository.findByUsername(username).get();
            gymGroup.addMembers(user.getUsername());
            return gymGroupRepository.save(gymGroup);
        } else {
            throw new Exception("User not found || GymGroup not found");
        }
    }

    public List<GymGroup> getGymGroups(String username) throws Exception {
        ValidationUtil.validateUsername(username);
        User user;

        if(userRepository.findByUsername(username).isPresent()){
            user = userRepository.findByUsername(username).get();
        }else {
            throw new Exception("User not found");
        }

        List<GymGroup> gymGroups = gymGroupRepository.findAllByMembersContains(user.getUsername());

        return gymGroups;

    }

    public List<Workout> getUsersWorkouts(String username) throws Exception {
        ValidationUtil.validateUsername(username);

        User user;

        if(userRepository.findByUsername(username).isPresent()){
            user = userRepository.findByUsername(username).get();
        }else {
            throw new Exception("User not found");
        }

        List<Workout> workoutList = user.getWorkoutsList();

        return workoutList;

    }
}
