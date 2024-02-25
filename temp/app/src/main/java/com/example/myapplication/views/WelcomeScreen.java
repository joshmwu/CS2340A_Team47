package com.example.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class WelcomeScreen extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        //WelcomeScreenViewModel viewModel = new ViewModelProvider(this).get(WelcomeScreenViewModel.class);
        Button startButton = findViewById(R.id.startButton);
        Button welcomeQuitButton = findViewById(R.id.quitButton);

        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeScreen.this, LoginScreen.class);
            startActivity(intent);
        });

        welcomeQuitButton.setOnClickListener(v -> {
            Intent goHome = new Intent(Intent.ACTION_MAIN);
            goHome.addCategory(Intent.CATEGORY_HOME);
            goHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(goHome);
        });
    }
}
