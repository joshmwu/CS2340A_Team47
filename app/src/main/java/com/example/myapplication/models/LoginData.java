package com.example.myapplication.models;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginData {
    // private instance variables and methods associated with logging in
    private String username;
    private String loginToken;
    private boolean loggedIn;
    private FirebaseAuth mAuth;
    FirebaseUser currUser;

    public void setLoginToken(String token){
        loginToken=token;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String name){
        this.username = name;
    }
    public void setLoggedIn(boolean loggedIn){
        this.loggedIn=loggedIn;
    }
    public boolean getLoggedIn(){
        return loggedIn;
    }
    public FirebaseUser getCurrUser(){
        return currUser;
    }
}
