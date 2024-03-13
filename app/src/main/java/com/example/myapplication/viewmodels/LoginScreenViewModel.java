package com.example.myapplication.viewmodels;

import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.models.LoginData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class LoginScreenViewModel {
    private static LoginScreenViewModel instance;
    private final LoginData loginData;
    private final FirebaseService firebaseService;

    public LoginScreenViewModel() {
        loginData = new LoginData();
        firebaseService = FirebaseService.getInstance();
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
    public void addNewUser(String username, String password){
        this.loginData.setUsername(username);
        //updateLoginData(username, password);
        DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
        userRef.child(username).child("username").setValue(username);
        userRef.child(username).child("password").setValue(password);
    }

    public void checkUserValidity(String username, String password){
        DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
        final boolean verified;
        userRef.child(username).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error finding username", task.getException());
                }
                else {
                    if(String.valueOf(task.getResult().getValue()).equals(username)){
                        userRef.child(username).child("password").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error finding password", task.getException());
                                }
                                else {
                                    if(String.valueOf(task.getResult().getValue()).equals(password)) {
                                        loginData.setUsername("test");
                                        //updateLoginData(username, password);
                                        Log.e("verified","verified");
                                        loginData.setLoggedIn(true);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
        loginData.setUsername(username);
    }
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
