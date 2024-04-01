package com.example.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterScreen extends AppCompatActivity {
    private LoginScreenViewModel vModel;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonReg;
    private Button buttonBack;
    private FirebaseAuth mAuth;

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
        setContentView(R.layout.register_screen);

        editTextUsername = findViewById(R.id.RegUsernameEditText);
        editTextPassword = findViewById(R.id.RegPasswordEditText);
        buttonReg = findViewById(R.id.RegCreateButton);
        buttonBack = findViewById(R.id.backButton);
        mAuth = FirebaseAuth.getInstance();
        vModel = LoginScreenViewModel.getInstance();

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
            startActivity(intent);
            finish();
        });
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username;
                String password;
                username = String.valueOf(editTextUsername.getText());
                password = String.valueOf(editTextPassword.getText());

                if (username.isEmpty()) {
                    Toast.makeText(RegisterScreen.this,
                            "enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(RegisterScreen.this,
                            "enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                vModel.addNewUser(username, password);
                Toast.makeText(RegisterScreen.this,
                        "Account created, redirecting to login page",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterScreen.this,
                        LoginScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
