package com.example.expensesmanagement.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensesmanagement.Activities.Auth.Login;
import com.example.expensesmanagement.Activities.Auth.Register;
import com.example.expensesmanagement.R;
import com.example.expensesmanagement.Services.DatabaseService;

public class MainActivity extends AppCompatActivity {

    // Buttons for login and register actions
    private Button btnLogin, btnRegister;
    // Database service for initializing the database connection
    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the MainActivity
        setContentView(R.layout.activity_main);

        // Initialize the database service when the app opens
        databaseService = new DatabaseService(this);
        // Open the database to ensure the connection is established
        SQLiteDatabase db = databaseService.getReadableDatabase();

        // Bind UI views from the layout
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Set click event for the Login button to navigate to Login activity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        // Set click event for the Register button to navigate to Register activity
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
