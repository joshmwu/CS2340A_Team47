package com.example.myapplication.viewmodels;

import com.example.myapplication.models.FirebaseService;
//import com.example.myapplication.models.RecipeData;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputRecipeViewModel {
    private FirebaseService firebaseService;
//    private final RecipeData recipeData;
    private static InputRecipeViewModel instance;

    private InputRecipeViewModel() {
        firebaseService = FirebaseService.getInstance();
//        recipeData = new RecipeData();
    }

    public static synchronized InputRecipeViewModel getInstance() {
        if (instance == null) {
            instance = new InputRecipeViewModel();
        }
        return instance;
    }
    public void addNewRecipe(String name, HashMap<String, Integer> ingredientMap) {
        DatabaseReference userRef = firebaseService.getDBReference("Recipes");
        for (Map.Entry<String, Integer> entry : ingredientMap.entrySet()) {
            String ingredient = entry.getKey();
            Integer quantity = entry.getValue();
            userRef.child(name).child(ingredient).setValue(quantity);
        }
    }
}
