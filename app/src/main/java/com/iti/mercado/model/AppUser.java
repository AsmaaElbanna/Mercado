package com.iti.mercado.model;

public class AppUser {
    private String username ;

    public AppUser() {
    }

    public AppUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
