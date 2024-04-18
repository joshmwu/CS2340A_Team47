package com.example.myapplication.viewmodels;
import com.example.myapplication.models.FirebaseService;

import java.util.ArrayList;
import java.util.HashMap;

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
//  OLD IMPLEMENTATION
//    public void addNewRecipe(String name, HashMap<String, Integer> ingredientMap) {
//        firebaseService.addNewRecipe(name, ingredientMap);
//    }

    public void addNewRecipe(String name, ArrayList<String[]> ingredientMap) {
        firebaseService.addNewRecipe(name, ingredientMap);
    }
}
