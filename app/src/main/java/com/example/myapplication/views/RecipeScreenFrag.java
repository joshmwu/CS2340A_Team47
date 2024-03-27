package com.example.myapplication.views;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.InputRecipeViewModel;
import com.example.myapplication.viewmodels.LoginScreenViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecipeScreenFrag extends Fragment {
    private EditText recipeNameEditText;
    private EditText ingredientEditText;
    private EditText quantityEditText;
    private Button addIngredientButton;
    private Button submitRecipeButton;
    private RecyclerView selectedIngredientsRecyclerView;
    private IngredientAdapter adapter;
    private InputRecipeViewModel vModel = InputRecipeViewModel.getInstance();
    private List<String> ingredientEntries = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_screen, container, false);
        recipeNameEditText = view.findViewById(R.id.recipeNameEditText);
        ingredientEditText = view.findViewById(R.id.ingredientEditText);
        quantityEditText = view.findViewById(R.id.quantityEditText);
        addIngredientButton = view.findViewById(R.id.addIngredientButton);
        submitRecipeButton = view.findViewById(R.id.submitRecipeButton);

        selectedIngredientsRecyclerView = view.findViewById(R.id.selectedIngredientsRecyclerView);
        adapter = new IngredientAdapter(ingredientEntries);
        selectedIngredientsRecyclerView.setAdapter(adapter);
        selectedIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addIngredientButton.setOnClickListener(v -> {
            String ingredient = ingredientEditText.getText().toString().trim();
            String quantity = quantityEditText.getText().toString().trim();
            if (!ingredient.isEmpty() && !quantity.isEmpty() && !ingredient.contains("-")) {
                String ingredientEntry = ingredient + " - " + quantity;
                ingredientEntries.add(ingredientEntry);
                adapter.notifyDataSetChanged();
                // Clear EditText fields after adding ingredient
                ingredientEditText.setText("");
                quantityEditText.setText("");
            }
        });

        submitRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientEntries.clear();
                adapter.notifyDataSetChanged();
                recipeNameEditText.setText("");
                ingredientEditText.setText("");
                quantityEditText.setText("");
                vModel.storeRecipe(vModel.getRecipeMap());
                Toast.makeText(getContext(), "submitted", Toast.LENGTH_SHORT).show();
                //still need to figure out how to store data into database
            }
        });

        return view;
    }
}
