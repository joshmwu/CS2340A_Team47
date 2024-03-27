package com.example.myapplication.viewmodels;

import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.models.RecipeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public Map<String, ArrayList<String>> getRecipeMap() {
        return recipeData.getRecipeMap();
    }

    public void storeRecipe(Map<String, ArrayList<String>> recipeMap) {
        //store into database
    }
}
