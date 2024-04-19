package com.example.myapplication.views;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.myapplication.R;
import com.example.myapplication.viewmodels.IngredientsViewModel;


import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddIngredientsScreenFrag extends Fragment {
    private Button addIngredientButton;
    private Button removeIngredientButton;
    private Button backButton;
    private EditText addIngredientNameET;
    private EditText addIngredientQuantityET;
    private EditText addIngredientCaloriesET;
    private EditText removeIngredientNameET;
    private EditText removeIngredientQuantityET;
    private TextView titleTV;
    private IngredientsViewModel ingredientsViewModel = IngredientsViewModel.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_to_pantry, container, false);

        addIngredientButton = root.findViewById(R.id.addIngredientButton);
        removeIngredientButton = root.findViewById(R.id.removeIngredientButton);
        backButton = root.findViewById(R.id.addIngredientsBackButton);
        addIngredientNameET = root.findViewById(R.id.addIngredientEditText);
        addIngredientQuantityET = root.findViewById(R.id.addIngredientQuantityEditText);
        addIngredientCaloriesET = root.findViewById(R.id.addIngredientCaloriesEditText);

        removeIngredientNameET = root.findViewById(R.id.removeIngredientEditText);
        removeIngredientQuantityET = root.findViewById(R.id.removeIngredientQuantityEditText);

        titleTV = root.findViewById(R.id.addIngredientsScreenTitle);

        addIngredientButton.setOnClickListener(v -> {

            if (IngredientsViewModel.checkValidity(addIngredientNameET.getText()
                    .toString()) && IngredientsViewModel.checkValidity(addIngredientQuantityET
                    .getText().toString()) && IngredientsViewModel
                    .checkValidity(addIngredientCaloriesET.getText().toString())) {
                String ingName = addIngredientNameET.getText().toString();
                int number = Integer.parseInt(addIngredientQuantityET.getText().toString());
                int calories = Integer.parseInt(addIngredientCaloriesET.getText().toString());

                ingredientsViewModel.addIngredient(ingName, number, calories);

                addIngredientNameET.setText("");
                addIngredientQuantityET.setText("");
                addIngredientCaloriesET.setText("");
                hideKeyboard(v);
                Toast.makeText(getContext(),
                        "Added!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(),
                        "Please enter a valid name, quantity, and calories to add.",
                        Toast.LENGTH_SHORT).show();
            }

        });

        removeIngredientButton.setOnClickListener(v -> {
            if (IngredientsViewModel.checkValidity(removeIngredientNameET
                    .getText().toString()) && IngredientsViewModel
                    .checkValidity(removeIngredientQuantityET.getText().toString())) {

                String ingName = removeIngredientNameET.getText().toString();
                int number = Integer.parseInt(removeIngredientQuantityET.getText().toString());

                ingredientsViewModel.removeIngredient(ingName, number);

                hideKeyboard(v);
                Toast.makeText(getContext(),
                        "Removed!",
                        Toast.LENGTH_SHORT).show();
                removeIngredientNameET.setText("");
                removeIngredientQuantityET.setText("");
            } else {
                Toast.makeText(getContext(),
                        "Please enter a valid name and quantity to remove", Toast.LENGTH_SHORT)
                        .show();
            }
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