package com.thegymgoers_java.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document("users")
public class User {

    // Fields

    @Id
    @JsonProperty("_id")
    private String _id;

    @JsonProperty("username")
    @NotEmpty(message = "User needs a username")
    private String username;

    @JsonProperty("emailAddress")
    @NotEmpty(message = "User needs an email address")
    private String emailAddress;

    @JsonProperty("password")
//    @NotEmpty(message = "Account needs an password")
    private String password;

    @JsonProperty("workoutsList")
    private List<Workout> workoutsList = new ArrayList<>();

    private Set<ERole> roles = new HashSet<>();


    public User(String username, String emailAddress, String password){
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    // Getters and setters

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress(){
        return emailAddress;
    }

    public void setPassword(String passwordToSet){
        this.password = passwordToSet;
    }

    public String getPassword(){
        return this.password;
    }

    public List<Workout> getWorkoutsList(){
        return this.workoutsList;
    }

    public void setWorkoutsList(List<Workout> workoutsList){
        this.workoutsList = workoutsList;
    }

    public Set<ERole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ERole> roles) {
        this.roles = roles;
    }

    public void addWorkout(Workout workoutToAdd){
        this.workoutsList.add(workoutToAdd);
    }
}
