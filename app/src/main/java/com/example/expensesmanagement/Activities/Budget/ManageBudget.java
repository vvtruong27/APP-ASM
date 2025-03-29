package com.example.expensesmanagement.Activities.Budget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensesmanagement.Activities.Auth.ViewProfile;
import com.example.expensesmanagement.Adapters.BudgetAdapter;
import com.example.expensesmanagement.Fragments.StudentFragment;
import com.example.expensesmanagement.Models.Budget;
import com.example.expensesmanagement.R;
import com.example.expensesmanagement.Services.DatabaseService;

import java.util.List;

public class ManageBudget extends AppCompatActivity {

    // RecyclerView to display the list of budgets
    private RecyclerView recyclerViewBudgets;
    // Adapter for binding budget data to the RecyclerView
    private BudgetAdapter budgetAdapter;
    // Service for database operations
    private DatabaseService databaseService;

    // Buttons for creating a new budget and viewing the profile
    private Button btnCreateBudget, btnViewProfile;

    // Example user ID (should be replaced with the actual logged-in user's ID)
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userId = getIntent().getIntExtra("user_id", -1);
        super.onCreate(savedInstanceState);
        // Set the layout for the Manage Budget activity
        setContentView(R.layout.activity_manage_budget);

        // Add StudentFragment to the Activity's container
        StudentFragment studentFragment = new StudentFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, studentFragment);
        transaction.commit();

        // Bind UI components to their corresponding views
        recyclerViewBudgets = findViewById(R.id.recyclerViewBudgets);
        btnCreateBudget = findViewById(R.id.btnCreateBudget);
        btnViewProfile = findViewById(R.id.btnViewProfile);

        // Initialize the database service
        databaseService = new DatabaseService(this);

        // Set the layout manager for the RecyclerView
        recyclerViewBudgets.setLayoutManager(new LinearLayoutManager(this));

        // Load the list of budgets from the database
        loadBudgets();

        // Handle click event for creating a new budget: navigate to CreateBudget activity
        btnCreateBudget.setOnClickListener(v -> {
            Intent intent = new Intent(ManageBudget.this, CreateBudget.class);
            startActivity(intent);
        });

        // Handle click event for viewing the profile: navigate to ViewProfile activity
        btnViewProfile.setOnClickListener(v -> {
            Intent intent = new Intent(ManageBudget.this, ViewProfile.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });
    }

    /**
     * Loads the list of budgets for the current user from the database.
     * Binds the data to the RecyclerView using the BudgetAdapter.
     */
    private void loadBudgets() {
        Log.d("ManageBudget", "user_id received: " + userId);


        List<Budget> budgetList = databaseService.getBudgetsByUserId(userId);
        Log.d("ManageBudget", "Budget list size: " + budgetList.size());
        budgetAdapter = new BudgetAdapter(this, budgetList, databaseService);
        recyclerViewBudgets.setAdapter(budgetAdapter);
    }

    /**
     * When the activity resumes, refresh the list of budgets.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadBudgets(); // Refresh the budget list when returning to this screen
    }
}
