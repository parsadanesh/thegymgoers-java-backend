package com.thegymgoers_java.app.payload.request;

import jakarta.validation.constraints.NotEmpty;

public class NewGymGroupRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String groupName;

    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public @NotEmpty String getGroupName() {
        return groupName;
    }

    public void setGroupName(@NotEmpty String groupName) {
        this.groupName = groupName;
    }
}
