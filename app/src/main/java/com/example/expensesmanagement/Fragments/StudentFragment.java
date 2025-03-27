package com.example.expensesmanagement.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.expensesmanagement.Activities.Budget.ManageBudget;
import com.example.expensesmanagement.Activities.Expense.ManageExpense;
import com.example.expensesmanagement.R;

public class StudentFragment extends Fragment {

    public StudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho fragment
        return inflater.inflate(R.layout.fragment_student, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button btnManageBudget = view.findViewById(R.id.btnManageBudget);
        Button btnManageExpense = view.findViewById(R.id.btnManageExpense);

        btnManageBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điều hướng sang activity ManageBudget (chi tiết quản lý ngân sách)
                Intent intent = new Intent(getActivity(), ManageBudget.class);
                startActivity(intent);
            }
        });

        btnManageExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Điều hướng sang activity ManageExpense (quản lý chi tiêu)
                Intent intent = new Intent(getActivity(), ManageExpense.class);
                startActivity(intent);
            }
        });
    }
}
