package com.example.myapplication.models;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginData {
    // private instance variables and methods associated with logging in
    private String username;
    private String password;
    private String loginToken;
    private boolean loggedIn;
    private FirebaseAuth mAuth;
    FirebaseUser currUser;

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoginToken(String token){
        loginToken=token;
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
