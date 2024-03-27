package com.example.myapplication.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.myapplication.R;
import android.widget.Button;
import android.widget.EditText;

public class AddIngredientsScreenFrag extends Fragment {
    private Button addIngredient;
    private Button backButton;
    private EditText ingredientNameET;
    private EditText ingredientQuantityET;
    private EditText ingredientCaloriesET;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_to_pantry, container, false);

        addIngredient = root.findViewById(R.id.addIngredientButton);
        backButton = root.findViewById(R.id.addIngredientsBackButton);
        ingredientNameET = root.findViewById(R.id.ingredientEditText);
        ingredientQuantityET = root.findViewById(R.id.ingredientQuantityEditText);
        ingredientCaloriesET = root.findViewById(R.id.ingredientCaloriesEditText);


        addIngredient.setOnClickListener(v -> {

            //TODO add info to database when clicked

            ingredientNameET.setText("");
            ingredientQuantityET.setText("");
            ingredientCaloriesET.setText("");
        });
        backButton.setOnClickListener(v -> {
            replaceFragment(new IngredientsScreenFrag());
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