package com.example.myapplication.viewmodels;
import com.example.myapplication.models.FirebaseService;

import java.util.ArrayList;

public class InputRecipeViewModel {
    private FirebaseService firebaseService = FirebaseService.getInstance();
    private static InputRecipeViewModel instance;

    private InputRecipeViewModel() {
        firebaseService = FirebaseService.getInstance();
    }

    public static synchronized InputRecipeViewModel getInstance() {
        if (instance == null) {
            instance = new InputRecipeViewModel();
        }
        return instance;
    }

    public void addNewRecipe(String name, ArrayList<String[]> ingredientMap) {
        firebaseService.addNewRecipe(name, ingredientMap);
    }
}
