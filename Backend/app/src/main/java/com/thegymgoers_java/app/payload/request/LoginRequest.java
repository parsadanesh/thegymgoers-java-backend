package com.thegymgoers_java.app.payload.request;

import jakarta.validation.constraints.NotEmpty;

public class LoginRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty String username) {
        this.username = username;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }
}
