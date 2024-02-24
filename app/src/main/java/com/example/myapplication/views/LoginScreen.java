package com.example.myapplication.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.LoginScreenViewModel;

public class LoginScreen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        Button loginQuitButton = findViewById(R.id.loginExitButton);
        loginQuitButton.setOnClickListener(v -> {
            Intent goHome = new Intent(Intent.ACTION_MAIN);
            goHome.addCategory(Intent.CATEGORY_HOME);
            goHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(goHome);
        });
    }
}
