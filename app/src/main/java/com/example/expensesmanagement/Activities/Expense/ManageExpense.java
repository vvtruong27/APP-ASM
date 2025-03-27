package com.example.expensesmanagement.Activities.Expense;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.expensesmanagement.Fragments.StudentFragment;
import com.example.expensesmanagement.R;

public class ManageExpense extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_expense);

        // Load StudentFragment vào container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new StudentFragment())
                .commit();
    }
}
