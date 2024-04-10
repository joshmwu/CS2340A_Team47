package com.example.myapplication.models;

import java.util.ArrayList;

public class ShoppingListData {
    private volatile static ShoppingListData instance;
    private ArrayList<Ingredient> shoppingList;
    private ShoppingListData() {}

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
}
