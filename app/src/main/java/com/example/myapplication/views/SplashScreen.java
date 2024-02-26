package com.example.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000; // 1 minute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, WelcomeScreen.class);
                startActivity(intent);
                finish(); // Finish this activity so the user can't navigate back to it
            }
        }, SPLASH_TIME_OUT);
    }
}
