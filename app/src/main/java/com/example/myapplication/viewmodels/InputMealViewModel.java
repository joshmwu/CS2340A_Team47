package com.example.myapplication.viewmodels;

import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.models.MealData;
import com.example.myapplication.models.UserData;
import com.example.myapplication.models.FirebaseService;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class InputMealViewModel {
    private static InputMealViewModel instance;
    private final MealData mealData;
    private static int day;
    private final FirebaseService firebaseService;
    Map<String, Integer> mealMap = new HashMap<>();

    private InputMealViewModel() {
        mealData = new MealData();
        firebaseService = FirebaseService.getInstance();
    }
    public static synchronized InputMealViewModel getInstance() {
        if (instance == null) {
            instance = new InputMealViewModel();
        }
        return instance;
    }
    public String getMealName() {
        return mealData.getMealName();
    }
    public int getMealCalories(){return mealData.getMealCalories();}
    public MealData getMealData() {
        return mealData;
    }
    public void setMealData(String username, String mealName, int calories) {
        DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
        DatabaseReference daysRef = userRef.child(username).child("meals").child("days");
        mealMap.put(mealName, calories); // Set calories directly under mealName
        daysRef.child("" + day).setValue(mealMap);

        //DatabaseReference usernameRef = firebaseService.getFirebaseDatabase().getReference(username);
        //DatabaseReference mealRef = usernameRef.child("meals").push();
        //mealRef.child("mealName").setValue(mealName);
        //mealRef.child("calories").setValue(calories);
        //usernameRef.child(username).child(username).child("meal").setValue(mealName);
        //userRef.child(username).child(username).child("meal").child(mealName).child("meal calories").setValue(calories);
        this.mealData.setMealData(mealName, calories);
    }
    public int getDay() {
        return day;
    }
    public void setDay(int newDay) {
        day = newDay;
    }
}
