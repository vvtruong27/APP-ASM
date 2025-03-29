package com.example.expensesmanagement.Activities.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensesmanagement.R;
import com.example.expensesmanagement.Services.DatabaseService;

public class Register extends AppCompatActivity {

    // UI elements for username, email, password, register button, and login text view
    private EditText etUsername, etEmail, etPassword;
    private Button btnRegister;
    private TextView tvLogin;

    // Service for handling database operations
    private DatabaseService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the registration activity
        setContentView(R.layout.activity_register);

        // Map UI elements to their corresponding views
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        // Initialize the database service
        dbService = new DatabaseService(this);

        // Set click event for the Register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegister();
            }
        });

        // Set click event for the "Login" text view to navigate to the Login activity
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Handles the registration process.
     * Retrieves the input data, validates them, checks for duplicate usernames,
     * and inserts the new user into the database with a default role of "student".
     * Displays appropriate messages based on the operation's outcome.
     */
    private void handleRegister() {
        // Retrieve and trim input from username, email, and password fields
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate that all fields are filled
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the username already exists in the database
        if (dbService.isUserExists(username)) {
            Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert the new user into the database with the default role "student"
        boolean isInserted = dbService.insertUser(username, email, password, "student");
        if (isInserted) {
            // Show success message and navigate to Login Activity if registration is successful
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            // Show error message if registration fails
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}
