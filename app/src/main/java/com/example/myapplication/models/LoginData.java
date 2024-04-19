package com.example.myapplication.models;


public class LoginData {
    // private instance variables and methods associated with logging in
    private static volatile  LoginData instance;
    private String username;
    private String password;
    private boolean loggedIn;

    private LoginData() { }
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
