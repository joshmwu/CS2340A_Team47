package com.example.myapplication.models;

public class LowCalorieIngredientFactory implements IngredientFactory {
    @Override
    public IngredientType createIngredient() {
        return new LowCalorieIngredientType();
    }
}
