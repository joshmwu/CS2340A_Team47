package com.example.myapplication.viewmodels;

import android.text.Editable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.models.LoginData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class LoginScreenViewModel {
    private volatile static LoginScreenViewModel instance;
    private final LoginData loginData = LoginData.getInstance();
    private FirebaseService firebaseService = FirebaseService.getInstance();

    private LoginScreenViewModel() {}

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
        DatabaseReference userRef = firebaseService.getDBReference("Users");
        userRef.child(username).child("username").
                get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error finding username", task.getException());
                            } else {
                                if (String.valueOf(task.getResult().getValue()).equals(username)) {
                                    userRef.child(username).child("password").get(

                                    ).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull
                                                                   Task<DataSnapshot> task) {
                                                if (!task.isSuccessful()) {
                                                    Log.e("firebase",
                                                            "Error finding password",
                                                            task.getException());
                                                } else {
                                                    if (String.valueOf(task.getResult(

                                                    ).getValue()).equals(password)) {
                                                        Log.e("verified", "verified");
                                                        loginData.setLoggedIn(true);
                                                    }
                                                }
                                            }
                                        });
                                }
                            }
                        }
                    });
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
