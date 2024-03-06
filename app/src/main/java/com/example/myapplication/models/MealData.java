package com.example.myapplication.models;

import java.util.ArrayList;

public class MealData {
    private String mealName;
    private ArrayList<String> meals = new ArrayList<>();
    private int mealCalories;

    public void setMealData(String name, int calories){
        this.mealName=name;
        this.mealCalories=calories;
        meals.add(name);
    }
    public MealData getMealData(){
        return this;
    }

    public String getMealName(){
        return this.mealName;
    }
    public int getMealCalories(){
        return this.mealCalories;
    }

}
