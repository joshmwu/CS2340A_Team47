package com.example.myapplication.viewmodels;

import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.models.PantryData;

public class IngredientsViewModel {
    private static IngredientsViewModel instance;
    private FirebaseService firebaseService = FirebaseService.getInstance();
    private IngredientsViewModel() { }
    private PantryData pantryData = PantryData.getInstance();

    public static synchronized IngredientsViewModel getInstance() {
        if (instance == null) {
            instance = new IngredientsViewModel();
        }
        return instance;
    }

    public PantryData getPantryData() {
        return pantryData;
    }

    public void addIngredient(String name, int quantity, int calories) {
        firebaseService.addIngredient(name, quantity, calories);
    }

    public void removeIngredient(String name, int quantity) {
        firebaseService.removeIngredient(name, quantity);
    }
    public static boolean checkValidity(String string) {
        if (string == null) {
            return false;
        } else if (string.isEmpty()) {
            return false;
        } else if (string.isBlank()) {
            return false;
        }
        return true;
    }
}
