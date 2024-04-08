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


import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
public class IngredientsScreenFrag extends Fragment {
    private Button addToPantryButton;
    private RecyclerView ingRecyclerView;
    private PantryData pantryData = PantryData.getInstance();
    private IngredientAdapter adapter;
    private List<String> ingredientEntries = createIngredientEntries();
    private TextView tv;

    public List<String> createIngredientEntries() {
        ArrayList<Ingredient> ingredients = pantryData.getIngredientList();
        List<String> ingredientEntries = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            String a = ingredient.getName() + " - " + ingredient.getQuantity();
            ingredientEntries.add(a);
        }
        return ingredientEntries;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ingredients_screen, container, false);
        addToPantryButton = root.findViewById(R.id.addToPantryButton);
        ingRecyclerView = root.findViewById(R.id.ingredientsRecyclerView);
        ingredientEntries = createIngredientEntries();
//        Log.d("mytag", ingredientEntries.toString());
        adapter = new IngredientAdapter(ingredientEntries);
        adapter.notifyDataSetChanged();
        ingRecyclerView.setAdapter(adapter);
        ingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addToPantryButton.setOnClickListener(v -> {
            replaceFragment(new AddIngredientsScreenFrag());
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
}