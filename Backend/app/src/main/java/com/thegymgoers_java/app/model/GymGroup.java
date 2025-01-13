package com.thegymgoers_java.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("gymgroup")
public class GymGroup {

    @Id
    @JsonProperty("_id")
    private String _id;

//    @NotEmpty
//    @JsonProperty("admins")
//    private List<User> admins;
//
//    @JsonProperty("members")
//    private List<User> members;

    @NotEmpty
    @JsonProperty("admins")
    private List<String> admins;

    @JsonProperty("members")
    private List<String> members;

    // I have to make a decision on to store the user object with the gym groups, or I can send through the
    // user's mongo id to store and use that to access the user object.


}
