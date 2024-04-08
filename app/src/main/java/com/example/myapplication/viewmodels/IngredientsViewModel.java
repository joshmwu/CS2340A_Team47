package com.example.myapplication.viewmodels;

import com.example.myapplication.models.FirebaseService;

public class IngredientsViewModel {
    private static IngredientsViewModel instance;
    private FirebaseService firebaseService = FirebaseService.getInstance();
    private IngredientsViewModel() {}

    public static synchronized IngredientsViewModel getInstance() {
        if (instance == null) {
            instance = new IngredientsViewModel();
        }
        return instance;
    }

    public void addIngredient(String name, int quantity, int calories) {
        firebaseService.addIngredient(name, quantity, calories);
    }

    public void removeIngredient(String name, int quantity) {
        firebaseService.removeIngredient(name, quantity);
    }
}
