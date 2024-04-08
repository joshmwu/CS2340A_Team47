package com.example.myapplication.viewmodels;

import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.models.MealData;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class InputMealViewModel {
    private static InputMealViewModel instance;
    private final MealData mealData;
    private static int day;
    private FirebaseService firebaseService = FirebaseService.getInstance();
    private Map<String, Integer> mealMap = new HashMap<>();
    private int totalDayCalories = 0;

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

    public int getMealCalories() {
        return mealData.getMealCalories();
    }

    public MealData getMealData() {
        return mealData;
    }

    public void setMealData(String username, String mealName, int calories) {
        DatabaseReference userRef = firebaseService.getDBReference("Users");
        DatabaseReference daysRef = userRef.child(username).child("meals").child("days");
        mealMap.put(mealName, calories); // Set calories directly under mealName
        daysRef.child("" + day).setValue(mealMap);
        addCaloriesToTotal(calories);
        this.mealData.setMealData(mealName, calories);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int newDay) {
        day = newDay;
    }

    public void clearMap() {
        mealMap.clear();
    }

    public int getTotalDayCalories() {
        return totalDayCalories;
    }

    public void resetTotalDayCalories() {
        totalDayCalories = 0;
    }

    public void addCaloriesToTotal(int calories) {
        totalDayCalories += calories;
    }
}
