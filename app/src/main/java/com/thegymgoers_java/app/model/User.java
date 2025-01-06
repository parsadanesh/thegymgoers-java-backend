package com.thegymgoers_java.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class User {

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


    public User(String username, String emailAddress, String password){
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
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
}
