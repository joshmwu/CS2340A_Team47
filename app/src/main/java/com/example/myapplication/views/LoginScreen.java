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
import android.widget.Toast;
import com.example.myapplication.models.LoginData;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.LoginScreenViewModel;

public class LoginScreen extends AppCompatActivity {
    private LoginScreenViewModel loginVM;
    private EditText usernameET;
    private EditText passwordET;
    private TextView warningMessage;
    FirebaseAuth mAuth;
    Button loginButton, registerButton;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            //TODO:go to main activity
            //Intent intent= new Intent(LoginScreen.this, MainActivity.class);
            //startActivity(intent);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        loginVM = LoginScreenViewModel.getInstance();
        usernameET = findViewById(R.id.LogUsernameEditText);
        passwordET = findViewById(R.id.LogPasswordEditText);
        warningMessage = findViewById(R.id.loginMessage);
        mAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.LogCreateButton);
        LoginData data = loginVM.getLoginData();
        Button loginQuitButton = findViewById(R.id.loginExitButton);

        loginQuitButton.setOnClickListener(v -> {
            Intent goHome = new Intent(Intent.ACTION_MAIN);
            goHome.addCategory(Intent.CATEGORY_HOME);
            goHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(goHome);
        });
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
            startActivity(intent);
            finish();
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            if (loginVM.checkNoInput(passwordET.getText()) || loginVM.checkNoInput(passwordET.getText())) {
                if (loginVM.checkNoInput(usernameET.getText()) && loginVM.checkNoInput(passwordET.getText())) {
                    warningMessage.setText("Please enter a valid username and password");
                } else if (loginVM.checkNoInput(usernameET.getText()) && !loginVM.checkNoInput(passwordET.getText())) {
                    warningMessage.setText("Please enter a valid username");
                } else if (!loginVM.checkNoInput(usernameET.getText()) && loginVM.checkNoInput(passwordET.getText())) {
                    warningMessage.setText("Please enter a valid password");
                } else if (loginVM.checkWhitespace(usernameET.getText()) || loginVM.checkWhitespace(passwordET.getText())) {
                    warningMessage.setText("Please check to ensure you have no whitespace");
                }
            }
            else {
                String username, password;
                username = String.valueOf(usernameET.getText());
                password = String.valueOf(passwordET.getText());
                String usernameToken = username + "@fakemail.com";
                mAuth.signInWithEmailAndPassword(usernameToken, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            warningMessage.setText("Authentification successful");

                            data.setLoggedIn(true);
                            data.setUsername(username);
                            data.setLoginToken(usernameToken);

                            //TODO:move to main activity screen
                            Intent intent2= new Intent(LoginScreen.this, HomeScreen.class);
                            startActivity(intent2);
                            //finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            warningMessage.setText("Authentification failed");
                            return;
                        }
                    }
                });
            }
        });
    }
}