package com.example.myapplication.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

import com.example.myapplication.models.Ingredient;
import com.example.myapplication.models.PantryData;
import com.example.myapplication.models.ShoppingListData;
import com.example.myapplication.viewmodels.IngredientsViewModel;
import com.example.myapplication.viewmodels.ShoppingListViewModel;


import android.widget.Button;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import org.checkerframework.checker.units.qual.A;

public class ShoppingListScreenFrag extends Fragment {
    private Button addToShoppingListButton;
    private Button buyItemsButton;
    private RecyclerView ingRecyclerView;
    private ShoppingListData shoppingListData = ShoppingListData.getInstance();
    private ShoppingListViewModel shoppingListViewModel = ShoppingListViewModel.getInstance();
    private IngredientsViewModel ingredientsViewModel = IngredientsViewModel.getInstance();
    private ShoppingListAdapter adapter;
    private List<String> itemEntries = createItemEntries();

    public List<String> createItemEntries() {
        ArrayList<Ingredient> ingredients = shoppingListData.getShoppingList();
        List<String> itemEntries = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            String a = ingredient.getName() + " - " + ingredient.getQuantity();
            itemEntries.add(a);
        }
        return itemEntries;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_shopping_list_screen, container, false);
        addToShoppingListButton = root.findViewById(R.id.addToShoppingListButton);
        buyItemsButton = root.findViewById(R.id.buyItemsButton);
        ingRecyclerView = root.findViewById(R.id.shoppingListRecyclerView);
        itemEntries = createItemEntries();
        adapter = new ShoppingListAdapter(itemEntries);
        adapter.notifyDataSetChanged();
        ingRecyclerView.setAdapter(adapter);
        ingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        buyItemsButton.setOnClickListener(v -> {
            List<String> checkedItems = adapter.getCheckedItems();
            for (String a : checkedItems) {
                String name = ShoppingListScreenFrag.getItemName(a);
                int quantity = ShoppingListScreenFrag.getItemQuantity(a);
                ingredientsViewModel.addIngredient(name, quantity, 0); // this causes an issue
                shoppingListViewModel.removeShoppingListItem(name, quantity);
            }
            // remove from SP database, add to P database
        });

        addToShoppingListButton.setOnClickListener(v -> {
            replaceFragment(new AddShoppingListScreenFrag()); // change to shopping list!
        });

        return root;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }

    private static String getItemName(String item) {
        String name = "";
        for (int i = 0; i < item.length() && !item.substring(i, i+1).equals(" "); i++) {
            name = name + item.substring(i, i+1);
        }
        return name;
    }
    private static int getItemQuantity(String item) {
        String quantity = "";
        for (int i = item.length() - 1; i < item.length() && !item.substring(i, i+1).equals(" "); i--) {
            quantity = item.substring(i, i+1) + quantity;
        }
        return Integer.valueOf(quantity);
    }
}