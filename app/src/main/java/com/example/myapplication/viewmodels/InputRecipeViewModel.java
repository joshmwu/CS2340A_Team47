package com.example.myapplication.viewmodels;

import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.models.RecipeData;

import java.util.List;

public class InputRecipeViewModel {
    private FirebaseService firebaseService;
    private final RecipeData recipeData;
    private static InputRecipeViewModel instance;

    private InputRecipeViewModel() {
        firebaseService = FirebaseService.getInstance();
        recipeData = new RecipeData();
    }

    public static synchronized InputRecipeViewModel getInstance() {
        if (instance == null) {
            instance = new InputRecipeViewModel();
        }
        return instance;
    }
    public String getRecipeName() {
        return recipeData.getRecipeName();
    }

    public void storeRecipe(String recipeName, List<String> ingredientEntries) {
        //store into database
    }
}
