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
import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;
import com.google.firebase.database.*;

import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class IngredientsScreenFrag extends Fragment {
    private Button addToPantryButton;
    private RecyclerView ingRecyclerView;
    private FirebaseService firebaseService;
    private PersonalInfoViewModel personalInfoViewModel;
    private IngredientAdapter adapter;
    private List<String> ingredientEntries = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_ingredients_screen, container, false);

        addToPantryButton = root.findViewById(R.id.addToPantryButton);

        ingRecyclerView = root.findViewById(R.id.ingredientsRecyclerView);
        adapter = new IngredientAdapter(ingredientEntries);
        ingRecyclerView.setAdapter(adapter);
        ingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        personalInfoViewModel = PersonalInfoViewModel.getInstance();

        firebaseService = FirebaseService.getInstance();
        DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
        DatabaseReference pantryRef = userRef.child(personalInfoViewModel.getUserData().getUsername()).child("Pantry");

        pantryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if the snapshot has children
                if (dataSnapshot.hasChildren()) {
                    // Iterate over the children
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        // Get the key (child node name) and value

                        pantryRef.child(childSnapshot.getKey()).child("quantity").get().addOnCompleteListener(task -> {
                            Object quantityObj = task.getResult().getValue();
                            if (quantityObj instanceof Long) {
                                ingredientEntries.add(childSnapshot.getKey() + " - " + ((Long) quantityObj).intValue());
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
                System.err.println("Listener was cancelled");
            }
        });

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