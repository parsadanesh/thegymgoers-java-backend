package com.thegymgoers_java.app.payload.request;

import jakarta.validation.constraints.NotEmpty;

public class NewGymGroupRequest {

    @NotEmpty
    private String username;

    @NotEmpty(message = "GymGroup must have a name")
    private String groupName;

    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty String username) {
        this.username = username;
    }

    public @NotEmpty(message = "GymGroup must have a name") String getGroupName() {
        return groupName;
    }

    public void setGroupName(@NotEmpty(message = "GymGroup must have a name") String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "NewGymGroupRequest{" +
                "username='" + username + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
