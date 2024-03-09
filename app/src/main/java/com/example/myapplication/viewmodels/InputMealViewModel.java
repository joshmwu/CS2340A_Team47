package com.example.myapplication.viewmodels;

import com.example.myapplication.models.MealData;
import com.example.myapplication.models.UserData;

public class InputMealViewModel {
    private static InputMealViewModel instance;
    private final MealData mealData;
    private static int day;

    public InputMealViewModel() {
        mealData = new MealData();
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
    public void setMealData(String mealName, int calories) {
        this.mealData.setMealData(mealName, calories);
    }
    public int getDay() {
        return day;
    }
    public void setDay(int newDay) {
        day = newDay;
    }
}
