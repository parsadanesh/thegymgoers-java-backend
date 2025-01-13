package com.thegymgoers_java.app.payload.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public class NewUserRequest {

    @NotEmpty(message = "User needs a username")
    private String username;

    @NotEmpty(message = "User needs an email address")
    private String emailAddress;

    @NotEmpty(message = "User needs a password")
    private String password;

    private Set<String> role;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

}
