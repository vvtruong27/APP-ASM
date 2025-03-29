package com.example.expensesmanagement.Models;

public class Budget {
    // Unique identifier for the budget
    private int id;
    // User ID associated with this budget (link to the User via user ID)
    private int userId;
    // Name of the budget
    private String name;
    // Maximum allowed amount for the budget
    private double limitAmount;
    // Amount remaining in the budget
    private double remainingAmount;

    /**
     * Default constructor.
     */
    public Budget() {
    }

    /**
     * Parameterized constructor to create a Budget object.
     *
     * @param id              Unique identifier for the budget.
     * @param userId          ID of the user associated with this budget.
     * @param name            Name of the budget.
     * @param limitAmount     Maximum allowed amount for the budget.
     * @param remainingAmount Remaining amount in the budget.
     */
    public Budget(int id, int userId, String name, double limitAmount, double remainingAmount) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.limitAmount = limitAmount;
        this.remainingAmount = remainingAmount;
    }

    // Getters and Setters

    /**
     * Returns the budget's unique ID.
     *
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the budget's unique ID.
     *
     * @param id the id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user ID associated with this budget.
     *
     * @return the userId.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with this budget.
     *
     * @param userId the userId to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the name of the budget.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the budget.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the budget's limit amount.
     *
     * @return the limitAmount.
     */
    public double getLimitAmount() {
        return limitAmount;
    }

    /**
     * Sets the budget's limit amount.
     *
     * @param limitAmount the limitAmount to set.
     */
    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }

    /**
     * Returns the remaining amount in the budget.
     *
     * @return the remainingAmount.
     */
    public double getRemainingAmount() {
        return remainingAmount;
    }

    /**
     * Sets the remaining amount in the budget.
     *
     * @param remainingAmount the remainingAmount to set.
     */
    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}
