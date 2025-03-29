package com.example.expensesmanagement.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.expensesmanagement.Activities.Budget.ManageBudget;
import com.example.expensesmanagement.Activities.Expense.ManageExpense;
import com.example.expensesmanagement.R;

public class StudentFragment extends Fragment {

    // Required empty public constructor
    public StudentFragment() {
    }

    /**
     * Inflates the layout for this fragment and sets up button click listeners
     * to navigate to ManageBudget and ManageExpense activities.
     *
     * @param inflater           LayoutInflater to inflate views in the fragment.
     * @param container          Parent view that the fragment's UI should be attached to.
     * @param savedInstanceState Previously saved state of the fragment.
     * @return The root view of the inflated layout.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);

        // Bind buttons from the layout
        Button btnManageBudget = view.findViewById(R.id.btnManageBudget);
        Button btnManageExpense = view.findViewById(R.id.btnManageExpense);

        // Set click listener to navigate to ManageBudget activity
        btnManageBudget.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ManageBudget.class);
            startActivity(intent);
        });

        // Set click listener to navigate to ManageExpense activity
        btnManageExpense.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ManageExpense.class);
            startActivity(intent);
        });

        // Return the root view for this fragment's UI
        return view;
    }
}
