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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.models.LoginData;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    Button buttonLog, buttonReg;
    EditText editTextUsername, editTextPassword;
    FirebaseAuth mAuth;
    private LoginScreenViewModel vModel;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

           //TODO:go to main activity
            //Intent intent= new Intent(LoginScreen.this, MainActivity.class);
            //startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        mAuth = FirebaseAuth.getInstance();
        editTextUsername = findViewById(R.id.LogUsernameEditText);
        editTextPassword = findViewById(R.id.LogPasswordEditText);
        buttonLog = findViewById(R.id.loginButton);
        buttonReg = findViewById(R.id.LogCreateButton);

        vModel = LoginScreenViewModel.getInstance();
        LoginData data = vModel.getLoginData();

        buttonReg.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
            startActivity(intent);
            finish();
        });

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = String.valueOf(editTextUsername.getText());
                password = String.valueOf(editTextPassword.getText());
                String usernameToken = username+"@fakemail.com";
                if(username.isEmpty()){
                    Toast.makeText(LoginScreen.this, "enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(LoginScreen.this, "enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(usernameToken, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(LoginScreen.this, "Authentication Successful.",
                                            Toast.LENGTH_SHORT).show();

                                    data.setLoggedIn(true);
                                    data.setUsername(username);
                                    data.setLoginToken(usernameToken);

                                    //TODO:move to main activity screen
                                    //Intent intent2= new Intent(LoginScreen.this, MainActivity.class);
                                    //startActivity(intent2);
                                    //finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginScreen.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        });
            }
        });
        //TODO: add logout option to main activity screen
    }
}
