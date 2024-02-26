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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.LoginScreenViewModel;

public class LoginScreen extends AppCompatActivity {
    private LoginScreenViewModel loginVM;
    private EditText usernameET;
    private EditText passwordET;
    private TextView warningMessage;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        loginVM = LoginScreenViewModel.getInstance();
        usernameET = findViewById(R.id.usernameEditText);
        passwordET = findViewById(R.id.passwordEditText);
        warningMessage = findViewById(R.id.loginMessage);

        Button loginQuitButton = findViewById(R.id.loginExitButton);
        loginQuitButton.setOnClickListener(v -> {
            Intent goHome = new Intent(Intent.ACTION_MAIN);
            goHome.addCategory(Intent.CATEGORY_HOME);
            goHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(goHome);
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            if (loginVM.checkNoInput(usernameET.getText()) && loginVM.checkNoInput(passwordET.getText())) {
                warningMessage.setText("Please enter a valid username and password");
            } else if (loginVM.checkNoInput(usernameET.getText()) && !loginVM.checkNoInput(passwordET.getText())) {
                warningMessage.setText("Please enter a valid username");
            } else if (!loginVM.checkNoInput(usernameET.getText()) && loginVM.checkNoInput(passwordET.getText())) {
                warningMessage.setText("Please enter a valid password");
            } else if (loginVM.checkWhitespace(usernameET.getText()) || loginVM.checkWhitespace(passwordET.getText())) {
                warningMessage.setText("Please check to ensure you have no whitespace");
            } else if (false/*invalid login, needs firebase functionality*/){
                warningMessage.setText("Invalid credentials, please try again");
            } else {
                loginVM.updateLoginData(usernameET.getText().toString(), passwordET.getText().toString());
                Intent toAppHomeScreen = new Intent(LoginScreen.this, HomeScreen.class);
                startActivity(toAppHomeScreen);
            }
        });
    }
}
