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
import com.google.firebase.database.*;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GlobalCookbookScreenFrag extends Fragment {
    private Button backButton;

    private Button filterButton;
    private EditText containsFilterET;
    private FirebaseService firebaseService;
    private List<String> cookbookEntries;
    private RecyclerView cookbookRecyclerView;
    private CookbookAdapter adapter;
    private TextView tv;


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
        tv = root.findViewById(R.id.globalCookbookScreenTitle);

        containsFilterET = root.findViewById(R.id.contains);

        Spinner filterSpinner = root.findViewById(R.id.filterSpinner);
        ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(requireContext(),
                R.array.filter_options, android.R.layout.simple_spinner_item);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapt);

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
            cookbookRef.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cookbookEntries.clear();
                    // Check if the snapshot has children
                    if (dataSnapshot.hasChildren()) {
                        // Iterate over the children
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            cookbookRef.child(childSnapshot.getKey()).get().addOnCompleteListener(task -> {
                                //tv.setText(childSnapshot.getKey() + filterSpinner.getSelectedItem().toString() + " + " + ((Long) dataSnapshot.getChildrenCount()).toString());
                                if (task.isSuccessful() && childSnapshot.getKey().contains(containsFilterET.getText().toString()) && filterSpinner.getSelectedItem().toString().equals("None")) {
                                    cookbookEntries.add(childSnapshot.getKey());
                                    adapter.notifyDataSetChanged();
                                } else if (task.isSuccessful() && childSnapshot.getKey().contains(containsFilterET.getText().toString()) && filterSpinner.getSelectedItem().toString().equals(((Long) childSnapshot.getChildrenCount()).toString())) {
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
        });


        backButton.setOnClickListener(v -> {
            replaceFragment(new RecipeScreenFrag());
        });

        return root;
    }

//    private void filter(String query) {
//        List<String> filteredList = new ArrayList<>();
//        for (String item : originalList) {
//            if (item.toLowerCase().contains(query.toLowerCase())) {
//                filteredList.add(item);
//            }
//        }
//        adapter.setFilter(filteredList); // Apply the filter to the adapter
//    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
}