package com.example.myapplication.viewmodels;

import android.text.Editable;

import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.models.LoginData;

public class LoginScreenViewModel {
    private static volatile LoginScreenViewModel instance;
    private final LoginData loginData = LoginData.getInstance();
    private FirebaseService firebaseService = FirebaseService.getInstance();

    private LoginScreenViewModel() { }

    public static synchronized LoginScreenViewModel getInstance() {
        if (instance == null) {
            instance = new LoginScreenViewModel();
        }
        return instance;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void addNewUser(String username, String password) {
        this.loginData.setUsername(username);
        firebaseService.addUser(username, password);
    }

    public void checkUserValidity(String username, String password) {
        firebaseService.checkUserValidity(username, password);
    }

    public void setInitialData(String username) {
        firebaseService.setUsername(username); // set the local username
        firebaseService.setAllData();
    }
    public boolean checkNoInput(Editable input) {
        return input.toString().isEmpty();
    }

    public boolean checkWhitespace(Editable input) {
        return !input.toString().isEmpty() && input.toString().contains(" ");
    }
}
