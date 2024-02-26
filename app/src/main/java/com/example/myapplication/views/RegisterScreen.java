package com.example.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterScreen extends AppCompatActivity {
    private LoginScreenViewModel vModel;
    EditText editTextUsername, editTextPassword;
    Button buttonReg, buttonBack;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            //TODO:go to main activity
        }
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        editTextUsername = findViewById(R.id.RegUsernameEditText);
        editTextPassword = findViewById(R.id.RegPasswordEditText);
        buttonReg = findViewById(R.id.RegCreateButton);
        buttonBack = findViewById(R.id.backButton);
        mAuth = FirebaseAuth.getInstance();

        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
            startActivity(intent);
            finish();
        });
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = String.valueOf(editTextUsername.getText());
                password = String.valueOf(editTextPassword.getText());
                String usernameToken = username + "@fakemail.com"; //NOTE: workaround to use built in email/password functionality in firebase
                if(username.isEmpty()){
                    Toast.makeText(RegisterScreen.this, "enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(RegisterScreen.this, "enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                //NOTE: password needs to be 6 or more characters long
                mAuth.createUserWithEmailAndPassword(usernameToken, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterScreen.this, "Account created, redirecting to login page",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                            startActivity(intent);
                            finish();
                        } else  {
                            // If sign in fails, display a message to the user.
                            if(password.length()<6){
                                Toast.makeText(RegisterScreen.this, "Choose a longer password.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(RegisterScreen.this, "Error, account creation failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }
}
