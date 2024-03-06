package com.example.myapplication.viewmodels;

import android.text.Editable;

import com.example.myapplication.models.LoginData;

public class LoginScreenViewModel {
    private static LoginScreenViewModel instance;
    private final LoginData loginData;

    public LoginScreenViewModel() {
        loginData = new LoginData();
    }

    public static synchronized LoginScreenViewModel getInstance() {
        if (instance == null) {
            instance = new LoginScreenViewModel();
        }
        return instance;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    // update methods for username, password ?

    //returns true if its not whitespace, null, or empty
    public void updateLoginData(String user, String pass) {
        this.loginData.setUsername(user);
        this.loginData.setPassword(pass);
    }
    public boolean checkNoInput(Editable input) {
        return input.toString().isEmpty();
    }
    public boolean checkWhitespace(Editable input) {
        return !input.toString().isEmpty() && input.toString().contains(" ");
    }
}
