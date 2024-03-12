package com.example.myapplication.models;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginData {
    // private instance variables and methods associated with logging in
    private String username;
    private String password;
    private boolean loggedIn;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference loginRef = database.getReference("Users");

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

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    public boolean getLoggedIn() {
        return loggedIn;
    }
    public FirebaseDatabase getDBInstance(){
        return this.database;
    }
}
