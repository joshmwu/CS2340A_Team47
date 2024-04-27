package com.example.myapplication.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.EditText;
import android.widget.Spinner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.models.FirebaseService;


import com.example.myapplication.R;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;
import com.google.firebase.database.*;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class GlobalCookbookScreenFrag extends Fragment implements OnItemClickListener {
    private Button backButton;

    private Button filterButton;
    private EditText containsFilterET;
    private FirebaseService firebaseService = FirebaseService.getInstance();
    private List<String> cookbookEntries;
    private RecyclerView cookbookRecyclerView;
    private CookbookAdapter adapter;
    private TextView tv;
    private Spinner filterSpinner;
    private PersonalInfoViewModel personalInfoViewModel = PersonalInfoViewModel.getInstance();
    private LoginScreenViewModel loginViewModel = LoginScreenViewModel.getInstance();

    @Override
    public void onItemClick(int position) {
        String recipe = cookbookEntries.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("key", recipe);
        Fragment recipeDetails = new RecipeDetailsFrag();
        recipeDetails.setArguments(bundle);
        replaceFragment(recipeDetails);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cookbook, container, false);

        backButton = root.findViewById(R.id.backButtonCookbook);
        cookbookEntries = new ArrayList<>();

        cookbookRecyclerView = root.findViewById(R.id.cookbookRecyclerView);
        adapter = new CookbookAdapter(cookbookEntries, this);
        cookbookRecyclerView.setAdapter(adapter);
        cookbookRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        filterButton = root.findViewById(R.id.filterButton);
        tv = root.findViewById(R.id.globalCookbookScreenTitle);

        containsFilterET = root.findViewById(R.id.contains);

        filterSpinner = root.findViewById(R.id.filterSpinner);
        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(requireContext(),
                R.array.filter_options, android.R.layout.simple_spinner_item);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapt);

        DatabaseReference cookbookRef = firebaseService.getFirebaseDatabase()
                .getReference("Recipes");
        //populates the initial list of recipes
        cookbookRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot cookbookSnapshot) {
                // Check if the snapshot has children
                if (cookbookSnapshot.hasChildren()) {
                    // Iterate over the children of recipes
                    for (DataSnapshot recipesOfCookbookSnapshot : cookbookSnapshot.getChildren()) {
                        checkRecipeAvailability(recipesOfCookbookSnapshot);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
                System.err.println("Listener was cancelled");
            }
        });

        filterButton.setOnClickListener(v -> {
            //changes the list of recipes based on the filter
            cookbookRef.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cookbookEntries.clear();
                    // Check if the snapshot has children
                    if (dataSnapshot.hasChildren()) {
                        // Iterate over the children of recipes
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            cookbookRef.child(childSnapshot.getKey()).get()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            checkRecipeAvailability(childSnapshot);
                                        }
                                    });
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                    System.err.println("Listener was cancelled");
                }
            });
        });


        backButton.setOnClickListener(v -> replaceFragment(new RecipeScreenFrag()));

        return root;
    }

    private void checkRecipeAvailability(DataSnapshot recipesOfCookbookSnapshot) {
        DatabaseReference userRef = firebaseService
                .getFirebaseDatabase().getReference("Users");
        DatabaseReference pantryRef = userRef.child(loginViewModel.getLoginData().getUsername())
                .child("Pantry");
        Log.d("mytag", "made it here 1");
        pantryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot pantryDataSnapshot) {
                boolean canMake = false;
                for (DataSnapshot ingredientsOfRecipeSnapshot
                        : recipesOfCookbookSnapshot.getChildren()) {
                    if (pantryDataSnapshot.hasChild(ingredientsOfRecipeSnapshot.getKey())) {
                        long pantryQuantity = (Long) pantryDataSnapshot
                                .child(ingredientsOfRecipeSnapshot.getKey())
                                .child("quantity").getValue();
                        Log.d("mytag", ingredientsOfRecipeSnapshot.toString());
                        Log.d("mytag", ingredientsOfRecipeSnapshot
                                .child("quantity").toString());
                        long recipeQuantity = (Long) ingredientsOfRecipeSnapshot
                                .child("quantity").getValue();
                        if (pantryQuantity >= recipeQuantity) {
                            canMake = true;
                        } else {
                            canMake = false;
                            break;
                        }
                    } else {
                        canMake = false;
                        break;
                    }
                }
                if (canMake) {
                    cookbookEntries.add(recipesOfCookbookSnapshot.getKey());
                } else {
                    cookbookEntries.add(recipesOfCookbookSnapshot.getKey() + "*");

                }
                Filter filter = new Filter();
                filter.setFilteringStrategy(new ContainsFiltering(containsFilterET
                        .getText().toString()));
                filter.filter(cookbookEntries);
                filter.setFilteringStrategy(new CanMakeFiltering(filterSpinner
                        .getSelectedItem().toString()));
                filter.filter(cookbookEntries);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });


    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
}