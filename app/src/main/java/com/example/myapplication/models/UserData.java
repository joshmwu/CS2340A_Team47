package com.example.myapplication.models;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class UserData extends LoginData {
    private int height;
    private int weight;
    private int age;
    private String gender = "No Input";

    private int calorieGoal = 2000;//default value
    private FirebaseService firebaseService = FirebaseService.getInstance();

    public int getHeight() {
        DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
        userRef.child(getUsername()).child("height").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Object heightObj = task.getResult().getValue();

                // Check if the height value exists and is an integer
                if (heightObj != null && heightObj instanceof Long) {
                    // Convert the height value to an integer
                    height = ((Long) heightObj).intValue();

                    // Now we have the height value
                    Log.d("firebase", "Height: " + height);
                }
            }
        });
        return height;
    }
    public int getWeight() {
        DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
        final boolean verified;
        userRef.child(getUsername()).child("weight").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Object weightObj = task.getResult().getValue();

                // Check if the height value exists and is an integer
                if (weightObj != null && weightObj instanceof Long) {
                    // Convert the height value to an integer
                    weight = ((Long) weightObj).intValue();

                    // Now we have the height value
                    Log.d("firebase", "Weight: " + weight);
                }
            }
        });
        return weight;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGender() {
        DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
        final boolean verified;
        userRef.child(getUsername()).child("gender").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error finding username", task.getException());
                } else {
                    gender = String.valueOf(task.getResult().getValue());
                }
            }
        });
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public int getAge(){
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setCalorieGoal(int calories){
        this.calorieGoal = calories;
    }
    public int getCalorieGoal() {
        DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
        final boolean verified;
        userRef.child(getUsername()).child("calorieGoal").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Object calorieGoalObj = task.getResult().getValue();

                // Check if the height value exists and is an integer
                if (calorieGoalObj != null && calorieGoalObj instanceof Long) {
                    // Convert the height value to an integer
                    calorieGoal = ((Long) calorieGoalObj).intValue();

                    // Now we have the height value
                    Log.d("firebase", "Weight: " + weight);
                }
            }
        });
        return calorieGoal;
    }
}
