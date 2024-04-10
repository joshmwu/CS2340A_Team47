package com.example.myapplication.views;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.myapplication.models.FirebaseService;


import com.example.myapplication.R;
import com.example.myapplication.viewmodels.IngredientsViewModel;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.example.myapplication.viewmodels.ShoppingListViewModel;


import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddShoppingListScreenFrag extends Fragment {
    private Button addShoppingListButton;
    private Button addShoppingListBackButton;
    private EditText addShoppingListEditText;
    private EditText addShoppingListQuantityEditText;
    private TextView titleTV;
    private ShoppingListViewModel shoppingListViewModel = ShoppingListViewModel.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_to_shopping_list, container, false);

        addShoppingListButton = root.findViewById(R.id.addShoppingListButton);
        addShoppingListBackButton = root.findViewById(R.id.addShoppingListBackButton);
        addShoppingListEditText = root.findViewById(R.id.addShoppingListEditText);
        addShoppingListQuantityEditText = root.findViewById(R.id.addShoppingListQuantityEditText);

        titleTV = root.findViewById(R.id.addShoppingListScreenTitle);

        addShoppingListButton.setOnClickListener(v -> {

            if (AddShoppingListScreenFrag.checkValidity(addShoppingListEditText.getText().toString()) && checkValidity(addShoppingListQuantityEditText.getText().toString())) {
                String itemName = addShoppingListEditText.getText().toString();
                int itemQuantity = Integer.parseInt(addShoppingListQuantityEditText.getText().toString());
                shoppingListViewModel.addShoppingListItem(itemName, itemQuantity);
                addShoppingListEditText.setText("");
                addShoppingListQuantityEditText.setText("");
                hideKeyboard(v);
                Toast.makeText(getContext(),
                        "Added!",
                        Toast.LENGTH_SHORT).show();
            } else {
                hideKeyboard(v);
                Toast.makeText(getContext(),
                        "Please enter a valid name and quantity to add.",
                        Toast.LENGTH_SHORT).show();
            }
        });


        addShoppingListBackButton.setOnClickListener(v -> {
            replaceFragment(new ShoppingListScreenFrag());
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

    private static boolean checkValidity(String string) {
        if (string == null) {
            return false;
        } else if (string.isEmpty()) {
            return false;
        } else if (string.isBlank()) {
            return false;
        }
        return true;
    }
}