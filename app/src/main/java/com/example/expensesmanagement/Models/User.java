package com.example.expensesmanagement.Models;

public class User {
    // Unique identifier for the user
    private int id;
    // Username for the user
    private String username;
    // Password for the user
    private String password;
    // Email address of the user
    private String email;
    // Role of the user, e.g., "student" or "admin"
    private String role;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Parameterized constructor to create a User object.
     *
     * @param id       Unique identifier for the user.
     * @param username Username of the user.
     * @param password Password of the user.
     * @param email    Email address of the user.
     * @param role     Role of the user (e.g., "student", "admin").
     */
    public User(int id, String username, String password, String email, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters

    /**
     * Returns the user's unique identifier.
     *
     * @return the user id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the user's unique identifier.
     *
     * @param id the user id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the user's password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's role.
     *
     * @return the role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     *
     * @param role the role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }
}
