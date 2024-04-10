package com.example.myapplication.viewmodels;

import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.views.ShoppingListScreenFrag;

public class ShoppingListViewModel {
    private static ShoppingListViewModel instance;
    private FirebaseService firebaseService = FirebaseService.getInstance();
    private ShoppingListViewModel() {}

    public static synchronized ShoppingListViewModel getInstance() {
        if (instance == null) {
            instance = new ShoppingListViewModel();
        }
        return instance;
    }

    public void addShoppingListItem(String name, int quantity) {
        firebaseService.addShoppingListItem(name, quantity);
    }

    public void removeShoppingListItem(String name, int quantity) {
        firebaseService.removeShoppingListItem(name, quantity);
    }

}
