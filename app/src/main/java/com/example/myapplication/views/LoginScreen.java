package com.example.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

public class LoginScreen extends AppCompatActivity {
    private LoginScreenViewModel loginVM;
    private EditText usernameET;
    private EditText passwordET;
    private TextView warningMessage;
    private FirebaseAuth mAuth;
    private Button loginButton;
    private Button registerButton;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            int i = 0;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        // set buttons and text
        loginVM = LoginScreenViewModel.getInstance();
        usernameET = findViewById(R.id.LogUsernameEditText);
        passwordET = findViewById(R.id.LogPasswordEditText);
        warningMessage = findViewById(R.id.loginMessage);
        mAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.LogCreateButton);
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
            if (loginVM.checkNoInput(passwordET.getText())
                    || loginVM.checkNoInput(passwordET.getText())) {
                if (loginVM.checkNoInput(usernameET.getText())
                        && loginVM.checkNoInput(passwordET.getText())) {
                    warningMessage.setText("Please enter a valid username and password");
                } else if (loginVM.checkNoInput(usernameET.getText())
                        && !loginVM.checkNoInput(passwordET.getText())) {
                    warningMessage.setText("Please enter a valid username");
                } else if (!loginVM.checkNoInput(usernameET.getText())
                        && loginVM.checkNoInput(passwordET.getText())) {
                    warningMessage.setText("Please enter a valid password");
                } else if (loginVM.checkWhitespace(usernameET.getText())
                        || loginVM.checkWhitespace(passwordET.getText())) {
                    warningMessage.setText("Please check to ensure you have no whitespace");
                }
            } else {
                String usernameLocal;
                String passwordLocal;
                usernameLocal = String.valueOf(usernameET.getText());
                passwordLocal = String.valueOf(passwordET.getText());

                loginVM.checkUserValidity(usernameLocal, passwordLocal);
                if (loginVM.getLoginData().getLoggedIn()) {
                    warningMessage.setText("Authentification successful");
                    Intent intent2 = new Intent(LoginScreen.this, HomeScreen.class);
                    intent2.putExtra("username", usernameLocal);
                    startActivity(intent2);
                }
                else {
                    warningMessage.setText("Authentication failed");
                }
            }
        });
    }
}