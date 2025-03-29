package com.example.expensesmanagement.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensesmanagement.Activities.Budget.EditBudget;
import com.example.expensesmanagement.Models.Budget;
import com.example.expensesmanagement.R;
import com.example.expensesmanagement.Services.DatabaseService;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {

    // Context of the calling activity
    private Context context;
    // List of budgets to be displayed
    private List<Budget> budgetList;
    // Database service for database operations
    private DatabaseService databaseService;

    /**
     * Constructor for BudgetAdapter.
     *
     * @param context         The context in which the adapter is used.
     * @param budgetList      The list of Budget objects.
     * @param databaseService The database service instance.
     */
    public BudgetAdapter(Context context, List<Budget> budgetList, DatabaseService databaseService) {
        this.context = context;
        this.budgetList = budgetList;
        this.databaseService = databaseService;
    }

    /**
     * Creates a new BudgetViewHolder by inflating the item_budget layout.
     *
     * @param parent   The parent view group.
     * @param viewType The type of the view.
     * @return A new BudgetViewHolder instance.
     */
    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_budget, parent, false);
        return new BudgetViewHolder(view);
    }

    /**
     * Binds data to the view holder for each budget item.
     *
     * @param holder   The view holder.
     * @param position The position of the item in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        Budget budget = budgetList.get(position);

        // Set budget name, limit, and remaining amount
        holder.tvBudgetName.setText(budget.getName());
        holder.tvBudgetLimit.setText("Limit: $" + budget.getLimitAmount());
        holder.tvBudgetRemaining.setText("Remaining: $" + budget.getRemainingAmount());

        // Handle the Edit button click event to navigate to EditBudget activity
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditBudget.class);
            intent.putExtra("budgetId", budget.getId());
            context.startActivity(intent);
        });

        // Handle the Delete button click event to delete the budget
        holder.btnDelete.setOnClickListener(v -> {
            // Delete budget from database
            databaseService.deleteBudget(budget.getId());
            // Remove the budget from the list and notify the adapter
            budgetList.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "Budget deleted", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Returns the total number of budget items.
     *
     * @return The size of the budget list.
     */
    @Override
    public int getItemCount() {
        return budgetList.size();
    }

    /**
     * ViewHolder class for Budget items.
     */
    public static class BudgetViewHolder extends RecyclerView.ViewHolder {
        // UI components for displaying budget details
        TextView tvBudgetName, tvBudgetLimit, tvBudgetRemaining;
        // Buttons for editing and deleting a budget
        Button btnEdit, btnDelete;

        /**
         * Constructor for the BudgetViewHolder.
         *
         * @param itemView The view of the individual item.
         */
        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBudgetName = itemView.findViewById(R.id.tvBudgetName);
            tvBudgetLimit = itemView.findViewById(R.id.tvBudgetLimit);
            tvBudgetRemaining = itemView.findViewById(R.id.tvBudgetRemaining);
            btnEdit = itemView.findViewById(R.id.btnEditBudget);
            btnDelete = itemView.findViewById(R.id.btnDeleteBudget);
        }
    }
}
