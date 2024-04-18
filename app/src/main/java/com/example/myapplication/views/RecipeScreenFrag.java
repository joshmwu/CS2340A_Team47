package com.example.myapplication.views;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.IngredientsViewModel;
import com.example.myapplication.viewmodels.InputRecipeViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.SearchView;

public class RecipeScreenFrag extends Fragment {
    private EditText recipeNameEditText;
    private EditText ingredientEditText;
    private EditText caloriesEditText;
    private EditText quantityEditText;
    private Button addIngredientButton;
    private Button submitRecipeButton;
    private Button seeAllRecipesButton;
    private RecyclerView selectedIngredientsRecyclerView;
    private IngredientAdapter adapter;
    private InputRecipeViewModel recipeViewModel = InputRecipeViewModel.getInstance();
    private List<String> ingredientEntries = new ArrayList<>(); // used for display purposes
    //private HashMap<String, Integer> ingredientMap = new HashMap<>(); // used for database
    private ArrayList<String[]> ingredientMap = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_screen, container, false);
        recipeNameEditText = view.findViewById(R.id.recipeNameEditText);
        ingredientEditText = view.findViewById(R.id.ingredientEditText);
        caloriesEditText = view.findViewById(R.id.calorieEditText);
        quantityEditText = view.findViewById(R.id.quantityEditText);
        addIngredientButton = view.findViewById(R.id.addIngredientButton);
        submitRecipeButton = view.findViewById(R.id.submitRecipeButton);
        seeAllRecipesButton = view.findViewById(R.id.seeAllRecipesButton);

        selectedIngredientsRecyclerView = view.findViewById(R.id.selectedIngredientsRecyclerView);
        adapter = new IngredientAdapter(ingredientEntries);
        selectedIngredientsRecyclerView.setAdapter(adapter);
        selectedIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//      PAST IMPLEMENTATION
//        addIngredientButton.setOnClickListener(v -> {
//            String ingredient = ingredientEditText.getText().toString().trim();
//            String calories = caloriesEditText.getText().toString().trim();
//            String quantity = quantityEditText.getText().toString().trim();
//            if (!ingredient.isEmpty() && !calories.isEmpty() && !quantity.isEmpty() && !ingredient.contains("-")) {
//                String ingredientEntry = ingredient + " - " + quantity + " - " + calories;
//                ingredientEntries.add(ingredientEntry);
//                adapter.notifyDataSetChanged();
//                // add to hashmap
//                ingredientMap.put(ingredient, Integer.valueOf(quantity));
//                // Clear EditText fields after adding ingredient
//                ingredientEditText.setText("");
//                quantityEditText.setText("");
//            } else {
//                Toast.makeText(getContext(), "Please enter a valid name and quantity.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });


        addIngredientButton.setOnClickListener(v -> {
            String ingredient = ingredientEditText.getText().toString().trim();
            String quantity = quantityEditText.getText().toString().trim();
            String calories = caloriesEditText.getText().toString().trim();
            if (!ingredient.isEmpty() && !calories.isEmpty() && !quantity.isEmpty() && !ingredient.contains("-")) {
                String ingredientEntry = ingredient + " - " + quantity + " - " + calories;
                ingredientEntries.add(ingredientEntry);
                adapter.notifyDataSetChanged();
                String[] ingredientDetails = new String[3];
                ingredientDetails[0] = ingredient;
                ingredientDetails[1] = quantity;
                ingredientDetails[2] = calories;
                ingredientMap.add(ingredientDetails);
                ingredientEditText.setText("");
                quantityEditText.setText("");
                caloriesEditText.setText("");
            } else {
                Toast.makeText(getContext(), "Please enter a valid name and quantity.",
                        Toast.LENGTH_SHORT).show();
            }
        });
//  OLD IMPLEMENTATION
//        submitRecipeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String recipeName = recipeNameEditText.getText().toString().trim();
//                if (!recipeName.isEmpty()) {
//                    ingredientEntries.clear();
//                    adapter.notifyDataSetChanged();
//                    recipeNameEditText.setText("");
//                    ingredientEditText.setText("");
//                    quantityEditText.setText("");
//                    recipeViewModel.addNewRecipe(recipeName, ingredientMap);
//                    Toast.makeText(getContext(), "Submitted!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), "Please enter a name for your recipe.",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        submitRecipeButton.setOnClickListener(v -> {
            String recipeName = recipeNameEditText.getText().toString().trim();

            if (!recipeName.isEmpty()) {
                recipeViewModel.addNewRecipe(recipeName, ingredientMap);
                ingredientMap.clear();
                recipeNameEditText.setText("");
                ingredientEntries.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(),
                        "Please enter a valid name, quantity, and calories to add.",
                        Toast.LENGTH_SHORT).show();
            }

        });

        seeAllRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new GlobalCookbookScreenFrag());
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
}
