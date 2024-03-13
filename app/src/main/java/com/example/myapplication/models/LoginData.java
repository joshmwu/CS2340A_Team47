package com.example.myapplication.models;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginData {
    // private instance variables and methods associated with logging in
    private String username;
    private String password;
    private boolean loggedIn;
    private FirebaseService firebaseService = FirebaseService.getInstance();

    public String getUsername() {
        DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
        final boolean verified;
        userRef.child(username).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error finding username", task.getException());
                } else {
                    username = String.valueOf(task.getResult().getValue());
                }
            }
        });
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
