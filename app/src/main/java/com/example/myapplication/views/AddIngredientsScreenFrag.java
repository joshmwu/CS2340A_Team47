package com.example.myapplication.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.models.FirebaseService;


import com.example.myapplication.R;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;
import com.google.firebase.database.*;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import androidx.annotation.NonNull;




import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private FirebaseService firebaseService;
    private PersonalInfoViewModel personalInfoViewModel;

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

        personalInfoViewModel = PersonalInfoViewModel.getInstance();
        firebaseService = FirebaseService.getInstance();
        titleTV = root.findViewById(R.id.addIngredientsScreenTitle);

        addIngredientButton.setOnClickListener(v -> {
            String ingName = addIngredientNameET.getText().toString();
            int number = Integer.parseInt(addIngredientQuantityET.getText().toString());
            int calories = Integer.parseInt(addIngredientCaloriesET.getText().toString());


            DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
            DatabaseReference pantryRef = userRef.child(personalInfoViewModel.getUserData().getUsername()).child("Pantry");
            DatabaseReference ingredientRef = pantryRef.child(ingName);


            //checks if it exists in pantry already
            ingredientRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        ingredientRef.child("quantity").get().addOnCompleteListener(task -> {
                            Object quantityObj = task.getResult().getValue();

                            // height value exists and is an integer
                            if (quantityObj instanceof Long) {
                                // set the height value to new value
                                int newQuantity = ((Long) quantityObj).intValue() + number;
                                pantryRef.child(ingName).child("quantity").setValue(newQuantity);
                            }
                        });
                        ingredientRef.child("calories").get().addOnCompleteListener(task -> {
                            Object caloriesObj = task.getResult().getValue();

                            // height value exists and is an integer
                            if (caloriesObj instanceof Long) {
                                // set the height value to new value
                                int newCalories = ((Long) caloriesObj).intValue() + calories;
                                pantryRef.child(ingName).child("calories").setValue(newCalories);
                            }
                        });
                    } else {
                        pantryRef.child(ingName).child("quantity").setValue(number);
                        pantryRef.child(ingName).child("calories").setValue(calories);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            addIngredientNameET.setText("");
            addIngredientQuantityET.setText("");
            addIngredientCaloriesET.setText("");
        });

        removeIngredientButton.setOnClickListener(v -> {
            String ingName = removeIngredientNameET.getText().toString();
            int number = Integer.parseInt(removeIngredientQuantityET.getText().toString());


            DatabaseReference userRef = firebaseService.getFirebaseDatabase().getReference("Users");
            DatabaseReference pantryRef = userRef.child(personalInfoViewModel.getUserData().getUsername()).child("Pantry");
            DatabaseReference ingredientRef = pantryRef.child(ingName);


            //checks if it exists in pantry already
            ingredientRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        ingredientRef.child("quantity").get().addOnCompleteListener(task -> {
                            Object quantityObj = task.getResult().getValue();

                            if (quantityObj instanceof Long) {
                                int newQuantity = ((Long) quantityObj).intValue() - number;
                                pantryRef.child(ingName).child("quantity").setValue(newQuantity);
                                if (newQuantity <= 0) {
                                    ingredientRef.removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    System.out.println("Node removed successfully.");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    System.err.println("Failed to remove node: " + e.getMessage());
                                                }

                                            });
                                }
                            }

                        });

                    } else {
                        pantryRef.child(ingName).child("quantity").setValue(number);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            removeIngredientNameET.setText("");
            removeIngredientQuantityET.setText("");
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