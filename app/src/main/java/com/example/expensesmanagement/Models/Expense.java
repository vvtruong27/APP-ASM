package com.example.expensesmanagement.Models;

import java.util.Date;

public class Expense {
    private int id;
    private int budgetId; // Liên kết đến Budget thông qua budget id
    private double amount;
    private String description;
    private Date date;
    private boolean isRecurring;

    public Expense() {
    }

    public Expense(int id, int budgetId, double amount, String description, Date date, boolean isRecurring) {
        this.id = id;
        this.budgetId = budgetId;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.isRecurring = isRecurring;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }
}
