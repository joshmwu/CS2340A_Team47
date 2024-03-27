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

public class AddIngredientsScreenFrag extends Fragment {
    private Button addIngredient;
    private Button backButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_to_pantry, container, false);
        addIngredient = root.findViewById(R.id.addIngredientButton);

        backButton = root.findViewById(R.id.addIngredientsBackButton);
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