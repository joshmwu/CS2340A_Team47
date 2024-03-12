package com.example.myapplication.models;

import com.google.firebase.database.FirebaseDatabase;

public class FirebaseService {
    private static FirebaseService instance;
    private FirebaseDatabase database;

    private FirebaseService() {
        database = FirebaseDatabase.getInstance();
    }

    public static FirebaseService getInstance() {
        if (instance == null) {
            instance = new FirebaseService();
        }
        return instance;
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return database;
    }

}