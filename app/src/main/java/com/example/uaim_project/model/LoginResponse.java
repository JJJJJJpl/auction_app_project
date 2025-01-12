package com.example.uaim_project.model;

public class LoginResponse {
    private String access_token;
    private String user_id;
    private String role;
    private String username;

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return access_token;
    }

    public String getUser_id() {
        return user_id;
    }
}
