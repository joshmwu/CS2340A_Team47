package com.example.myapplication.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;


public class LoginData {
    // private instance variables and methods associated with logging in
    private volatile static LoginData instance;
    private String username;
    private String password;
    private boolean loggedIn;

    private LoginData() {}
    public static LoginData getInstance() {
        if (instance == null) {
            synchronized (LoginData.class) {
                if (instance == null) {
                    instance = new LoginData();
                }
            }
        }
        return instance;
    }


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
}
