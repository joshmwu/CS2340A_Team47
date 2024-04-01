package com.example.myapplication.viewmodels;


import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.models.UserData;
import com.google.firebase.database.DatabaseReference;

public class PersonalInfoViewModel {
    private static PersonalInfoViewModel instance;
    private final UserData userData;
    private static FirebaseService firebaseService;

    public PersonalInfoViewModel() {
        userData = new UserData();
        firebaseService = FirebaseService.getInstance();
    }

    public static synchronized PersonalInfoViewModel getInstance() {
        if (instance == null) {
            instance = new PersonalInfoViewModel();
        }
        return instance;
    }

    public UserData getUserData() {
        return userData;
    }

    public static FirebaseService getFirebaseService() {
        return firebaseService;
    }

    public void updateData(String username, int height, int weight,
                           int age, String gender, int calorieGoal) {
        this.userData.setHeight(height);
        this.userData.setWeight(weight);
        this.userData.setAge(age);
        this.userData.setGender(gender);
        this.userData.setCalorieGoal(calorieGoal);
        DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
        userRef.child(username).child("height").setValue(height);
        userRef.child(username).child("weight").setValue(weight);
        userRef.child(username).child("age").setValue(age);
        userRef.child(username).child("gender").setValue(gender);
        userRef.child(username).child("calorieGoal").setValue(calorieGoal);
    }

}
