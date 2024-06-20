package com.example.demo.payload.response;


import lombok.Getter;

@Getter
public class InvalidLoginResponce {

    private String username;
    private String password;

    public InvalidLoginResponce() {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }
}
