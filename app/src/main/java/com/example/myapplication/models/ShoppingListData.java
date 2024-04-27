package com.example.myapplication.models;

import android.util.Log;

import java.util.ArrayList;

public class ShoppingListData {
    private static volatile ShoppingListData instance;
    private ArrayList<Ingredient> shoppingList;
    private ShoppingListData() { }

    public static ShoppingListData getInstance() {
        if (instance == null) {
            synchronized (ShoppingListData.class) {
                if (instance == null) {
                    instance = new ShoppingListData();
                }
            }
        }
        return instance;
    }

    public ArrayList<Ingredient> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ArrayList<Ingredient> shoppingList) {
        this.shoppingList = shoppingList;
    }
    public int getCaloriesFromName(String name) {
        int calories = 0;
        for (Ingredient a : shoppingList) {
            Log.d("calories", String.valueOf(a.getCalories()));
            if (a.getName().equals(name)) {
                calories = a.getCalories();
            }
        }
        Log.d("End of getcalories", "test");
        return calories;
    }
}
