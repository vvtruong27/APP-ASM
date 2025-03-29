package com.example.expensesmanagement.Activities.Auth;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensesmanagement.Activities.Budget.ManageBudget;
import com.example.expensesmanagement.Activities.User.ManageUser;
import com.example.expensesmanagement.R;
import com.example.expensesmanagement.Services.DatabaseService;

public class Login extends AppCompatActivity {

    // UI elements for username, password, login button, and register text view
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    // Service for database operations
    private DatabaseService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the login activity
        setContentView(R.layout.activity_login);

        // Map UI elements to their corresponding views
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Initialize the database service
        dbService = new DatabaseService(this);

        // Set click event for the Login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
            }
        });

        // Set click event for the "Register" text view to navigate to the Register activity
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Handles the login process.
     * Retrieves the username and password input, validates them, and checks the credentials in the database.
     * If login is successful, navigates to the appropriate activity based on the user's role.
     * Displays error messages for invalid input or failed login attempts.
     */
    private void handleLogin() {
        // Retrieve and trim the username and password input
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate that both fields are filled
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check login credentials from SQLite database
        Cursor cursor = dbService.getUserByUsernameAndPassword(username, password);
        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve the user ID and role of the logged-in user
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

            // Navigate to different activities based on the user's role
            Intent intent;
            if (role.equals("student")) {
                intent = new Intent(Login.this, ManageBudget.class);
            } else {
                intent = new Intent(Login.this, ManageUser.class);
            }
            intent.putExtra("user_id", userId);  // Pass the user ID to ViewProfile
            startActivity(intent);

            finish();
        } else {
            // Show error message if login credentials are invalid
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }

        // Close the cursor to free up resources
        if (cursor != null) {
            cursor.close();
        }
    }

}