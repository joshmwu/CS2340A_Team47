package com.example.myapplication.models;

public class HighCalorieIngredientFactory implements IngredientFactory {
    @Override
    public IngredientType createIngredient() {
        return new HighCalorieIngredientType();
    }
}


