package com.example.expensesmanagement.Models;

import java.util.Date;

public class Expense {
    // Unique identifier for the expense
    private int id;
    // Budget ID associated with this expense (link to Budget via budget id)
    private int budgetId;
    // Amount of the expense
    private double amount;
    // Description of the expense
    private String description;
    // Date when the expense was incurred
    private Date date;
    // Flag indicating whether the expense is recurring
    private boolean isRecurring;

    /**
     * Default constructor.
     */
    public Expense() {
    }

    /**
     * Parameterized constructor to create an Expense object.
     *
     * @param id          Unique identifier for the expense.
     * @param budgetId    ID of the associated budget.
     * @param amount      Amount of the expense.
     * @param description Description of the expense.
     * @param date        Date when the expense occurred.
     * @param isRecurring True if the expense is recurring, false otherwise.
     */
    public Expense(int id, int budgetId, double amount, String description, Date date, boolean isRecurring) {
        this.id = id;
        this.budgetId = budgetId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.isRecurring = isRecurring;
    }

    // Getters & Setters

    /**
     * Returns the unique expense ID.
     *
     * @return the expense ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique expense ID.
     *
     * @param id the expense ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the budget ID associated with this expense.
     *
     * @return the budget ID.
     */
    public int getBudgetId() {
        return budgetId;
    }

    /**
     * Sets the budget ID associated with this expense.
     *
     * @param budgetId the budget ID to set.
     */
    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    /**
     * Returns the amount of the expense.
     *
     * @return the expense amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the expense.
     *
     * @param amount the expense amount to set.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns the description of the expense.
     *
     * @return the expense description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the expense.
     *
     * @param description the expense description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the date of the expense.
     *
     * @return the expense date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the expense.
     *
     * @param date the expense date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns whether the expense is recurring.
     *
     * @return true if recurring; false otherwise.
     */
    public boolean isRecurring() {
        return isRecurring;
    }

    /**
     * Sets whether the expense is recurring.
     *
     * @param recurring true to mark as recurring; false otherwise.
     */
    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }
}
