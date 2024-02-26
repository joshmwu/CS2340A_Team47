package com.example.myapplication.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.databinding.HomeScreenBinding;

public class HomeScreen extends AppCompatActivity {
    HomeScreenBinding binding;
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navigationbar.setOnItemSelectedListener(v -> {
            int itemId = v.getItemId();
            if (itemId == R.id.Meal) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.Shopping) {
                replaceFragment(new HomeFragment());
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
