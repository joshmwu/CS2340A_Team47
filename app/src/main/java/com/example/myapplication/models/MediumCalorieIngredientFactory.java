package com.example.myapplication.models;

public class MediumCalorieIngredientFactory implements IngredientFactory {
    @Override
    public IngredientType createIngredient() {
        return new MediumCalorieIngredientType();
    }
}
