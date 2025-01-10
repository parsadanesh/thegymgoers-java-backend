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

    @NotEmpty
    @JsonProperty("admins")
    private List<User> admins;

    @JsonProperty("members")
    private List<User> members;
}
