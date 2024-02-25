package com.example.myapplication.viewmodels;
import com.example.myapplication.models.LoginData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreenViewModel {
    private static LoginScreenViewModel instance;
    private final  LoginData loginData;

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

}
