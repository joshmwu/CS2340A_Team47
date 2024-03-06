package com.example.myapplication.viewmodels;

import com.example.myapplication.models.MealData;
import com.example.myapplication.models.UserData;

public class InputMealViewModel {
    private static InputMealViewModel instance;
    private final MealData mealData;

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

    public void setMealData(String mealName, int calories) {
        this.mealData.setMealData(mealName, calories);
    }
}
