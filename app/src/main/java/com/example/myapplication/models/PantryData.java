package com.example.myapplication.models;

import android.util.Log;

import java.util.ArrayList;

public class PantryData {
    private volatile static PantryData instance;
    private ArrayList<Ingredient> ingredientList;
    private PantryData() {}

    public static PantryData getInstance() {
        if (instance == null) {
            synchronized (PantryData.class) {
                if (instance == null) {
                    instance = new PantryData();
                }
            }
        }
        return instance;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public int getCaloriesFromName(String name) {
        int calories = 0;
        for (Ingredient a : ingredientList) {
            Log.d("calories", String.valueOf(a.getCalories()));
            if (a.getName().equals(name)) {
                calories = a.getCalories();
            }
        }
        Log.d("End of getcalories", "test");
        return calories;
    }
    public int getQuantityFromName(String name) {
        int quantity = 0;
        for (Ingredient a : ingredientList) {
            Log.d("quantity", String.valueOf(a.getQuantity()));
            if (a.getName().equals(name)) {
                quantity = a.getQuantity();
            }
        }
        Log.d("End of getquantity", "test");
        return quantity;
    }
}
