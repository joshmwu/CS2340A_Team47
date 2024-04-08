package com.example.myapplication.models;

public class Ingredient {
    private String name;
    private int calories;
    private int quantity;
    public Ingredient(String name, int calories, int quantity) {
        this.name = name;
        this.calories = calories;
        this.quantity = quantity;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
