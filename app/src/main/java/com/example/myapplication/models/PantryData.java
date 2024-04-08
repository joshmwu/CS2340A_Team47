package com.example.myapplication.models;

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
}
