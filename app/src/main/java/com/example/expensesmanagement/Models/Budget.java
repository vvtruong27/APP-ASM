package com.example.expensesmanagement.Models;

public class Budget {
    private int id;
    private int userId; // Liên kết đến User thông qua user id
    private String name;
    private double limitAmount;
    private double remainingAmount;

    public Budget() {
    }

    public Budget(int id, int userId, String name, double limitAmount, double remainingAmount) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.limitAmount = limitAmount;
        this.remainingAmount = remainingAmount;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}
