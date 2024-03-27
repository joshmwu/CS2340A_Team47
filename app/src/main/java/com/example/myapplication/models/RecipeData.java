package com.example.myapplication.models;

import java.util.ArrayList;
import java.util.List;

public class RecipeData {
    private String recipeName;
    private List<String> ingredientEntries = new ArrayList<>();
    public String getRecipeName() {
        return this.recipeName;
    }
}
