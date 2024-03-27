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

public class IngredientsScreenFrag extends Fragment {
    private Button addToPantryButton;
    private View recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ingredients_screen, container, false);

        addToPantryButton = root.findViewById(R.id.addToPantryButton);
        addToPantryButton.setOnClickListener(v -> {
            replaceFragment(new AddIngredientsScreenFrag());
        });
        recyclerView = root.findViewById(R.id.ingredientsRecyclerView);
        recyclerView.setNestedScrollingEnabled(false);


        //TODO write items in the pantry from the database to the recycler view


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