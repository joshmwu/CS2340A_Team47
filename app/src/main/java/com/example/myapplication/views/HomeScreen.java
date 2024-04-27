package com.example.myapplication.views;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.myapplication.R;
import com.example.myapplication.databinding.HomeScreenBinding;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;


public class HomeScreen extends AppCompatActivity {
    private HomeScreenBinding binding;
    private LoginScreenViewModel loginVM = LoginScreenViewModel.getInstance();
    private PersonalInfoViewModel userInfoVM = PersonalInfoViewModel.getInstance();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent2 = getIntent();
        if (intent2 != null && intent2.hasExtra("username")) {
            String usernameHome = intent2.getStringExtra("username");
            loginVM.setInitialData(usernameHome);
        }
        replaceFragment(new HomeFragment());
        findViewById(R.id.Home).setOnClickListener(v -> replaceFragment(new HomeFragment()));
        findViewById(R.id.Meal).setOnClickListener(v -> replaceFragment(new InputMealScreenFrag()));
        findViewById(R.id.Recipe).setOnClickListener(v -> replaceFragment(new RecipeScreenFrag()));
        findViewById(R.id.Ingredients)
                .setOnClickListener(v -> replaceFragment(new IngredientsScreenFrag()));
        findViewById(R.id.Shopping)
                .setOnClickListener(v -> replaceFragment(new ShoppingListScreenFrag()));
        findViewById(R.id.PersonalInfo)
                .setOnClickListener(v -> replaceFragment(new PersonalInfoFragment()));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
}
