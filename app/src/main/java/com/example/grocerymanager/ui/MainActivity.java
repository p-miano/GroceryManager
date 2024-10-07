package com.example.grocerymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grocerymanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonSignUp;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(view -> loginUser());
        buttonSignUp.setOnClickListener(view -> signUpUser());
    }

    // Method to log in the user
    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful, move to Grocery Management Activity
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            openGroceryManagementActivity();
                        }
                    } else {
                        // Login failed
                        Toast.makeText(MainActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                        Log.e("LoginError", task.getException().getMessage());
                    }
                });
    }

    // Method to sign up a new user
    private void signUpUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-up successful, move to Grocery Management Activity
                        Toast.makeText(MainActivity.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            openGroceryManagementActivity();
                        }
                    } else {
                        // Sign-up failed
                        Toast.makeText(MainActivity.this, "Sign Up failed. Please try again.", Toast.LENGTH_SHORT).show();
                        Log.e("SignUpError", task.getException().getMessage());
                    }
                });
    }

    // Method to transition to GroceryManagementActivity
    private void openGroceryManagementActivity() {
        Intent intent = new Intent(MainActivity.this, GroceryManagementActivity.class);
        startActivity(intent);
        finish(); // Finish MainActivity so that the user can't navigate back to it
    }
}
