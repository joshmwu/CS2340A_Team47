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
import com.example.myapplication.viewmodels.IngredientsViewModel;
import com.example.myapplication.viewmodels.InputMealViewModel;
import com.google.firebase.database.*;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RecipeDetailsFrag extends Fragment {
    private TextView recipeDetailsTitle;
    private List<String> ingredientEntries;
    private RecyclerView recipeDetailsRecyclerView;
    private FirebaseService firebaseService;
    private IngredientAdapter adapter;
    private Button backButton;
    private Button cookButton;
    private IngredientsViewModel ingredientsViewModel = IngredientsViewModel.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        backButton = root.findViewById(R.id.backButtonRecipeDetails);
        cookButton = root.findViewById(R.id.cookRecipeButton);
        recipeDetailsTitle = root.findViewById(R.id.recipeDetailsScreenTitle);
        ingredientEntries = new ArrayList<>();
        adapter = new IngredientAdapter(ingredientEntries);
        recipeDetailsRecyclerView = root.findViewById(R.id.recipeDetailsRecyclerView);
        recipeDetailsRecyclerView.setAdapter(adapter);
        recipeDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Bundle bundle = getArguments();
        if (bundle != null) {
            String recipe = bundle.getString("key");
            recipeDetailsTitle.setText(recipe);
            firebaseService = FirebaseService.getInstance();
            DatabaseReference recipeRef = firebaseService.getFirebaseDatabase().getReference(
                    "Recipes").child(recipe);
            recipeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Check if the snapshot has children
                    if (dataSnapshot.hasChildren()) {
                        // Iterate over the children
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            // Get the key (child node name) and value
                            ingredientEntries.add(childSnapshot.getKey() + " - "
                                    + childSnapshot.getValue());
                            adapter.notifyDataSetChanged();

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                    System.err.println("Listener was cancelled");
                }
            });


        }

        cookButton.setOnClickListener(v -> {
            //update visualizations, meal database, calorie count, & indredients
            // get subtracted from pantry.
            for (String a : ingredientEntries) {
                String name = RecipeDetailsFrag.getItemName(a);
                int quantity = RecipeDetailsFrag.getItemQuantity(a);
                ingredientsViewModel.removeIngredient(name ,quantity);
            }
        });

        backButton.setOnClickListener(v -> {
            replaceFragment(new GlobalCookbookScreenFrag());
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