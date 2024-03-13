package com.example.myapplication.models;


import java.lang.reflect.Array;
import java.util.ArrayList;
import com.github.mikephil.charting.data.Entry;

public class MealData {
    private String mealName;
    private ArrayList<String> meals = new ArrayList<>();
    private ArrayList<Entry> caloriesByDay = new ArrayList<>();
    private int mealCalories;

    public void setMealData(String name, int calories) {
        this.mealName = name;
        this.mealCalories = calories;
        meals.add(name);
    }
    public ArrayList<String> getMeals() {
        return meals;
    }
    public MealData getMealData(){
        return this;
    }
    public ArrayList<Entry> getCaloriesByDay() {
        return caloriesByDay;
    }

    public String getMealName(){
        return this.mealName;
    }
    public int getMealCalories(){
        return this.mealCalories;
    }
    public void resetMealCalories() {
        mealCalories = 0;
    }

}
