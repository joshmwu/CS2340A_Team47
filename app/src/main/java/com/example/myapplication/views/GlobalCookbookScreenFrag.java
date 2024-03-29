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
import com.example.myapplication.models.FirebaseService;


import com.example.myapplication.R;
import com.google.firebase.database.*;


import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GlobalCookbookScreenFrag extends Fragment {
    private Button backButton;

    private Button filterButton;
    private FirebaseService firebaseService;
    private List<String> cookbookEntries;
    private RecyclerView cookbookRecyclerView;
    private CookbookAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cookbook, container, false);

        backButton = root.findViewById(R.id.backButtonCookbook);
        cookbookEntries = new ArrayList<>();

        cookbookRecyclerView = root.findViewById(R.id.cookbookRecyclerView);
        adapter = new CookbookAdapter(cookbookEntries);
        cookbookRecyclerView.setAdapter(adapter);
        cookbookRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        filterButton = root.findViewById(R.id.filterButton);

        firebaseService = FirebaseService.getInstance();
        DatabaseReference cookbookRef = firebaseService.getFirebaseDatabase().getReference("Recipes");
        //Log.d("MainActivity", "Activity created");
        cookbookRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if the snapshot has children
                if (dataSnapshot.hasChildren()) {
                    // Iterate over the children
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        cookbookRef.child(childSnapshot.getKey()).get().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                cookbookEntries.add(childSnapshot.getKey());
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


        filterButton.setOnClickListener(v -> {
            
        });


        backButton.setOnClickListener(v -> {
            replaceFragment(new RecipeScreenFrag());
        });

        return root;
    }

    private boolean checkValidity(String string) {
        if (string == null) {
            return false;
        } else if (string.isEmpty()) {
            return false;
        } else if (string.isBlank()) {
            return false;
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
}