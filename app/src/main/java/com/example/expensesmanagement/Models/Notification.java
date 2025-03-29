package com.example.expensesmanagement.Models;

import java.util.Date;

public class Notification {
    // Unique identifier for the notification
    private int id;
    // User ID associated with this notification (link to User via user id)
    private int userId;
    // Notification message content
    private String message;
    // Date when the notification was created
    private Date date;

    /**
     * Default constructor.
     */
    public Notification() {
    }

    /**
     * Parameterized constructor to create a Notification object.
     *
     * @param id      Unique identifier for the notification.
     * @param userId  ID of the user associated with this notification.
     * @param message Content of the notification.
     * @param date    Date when the notification was created.
     */
    public Notification(int id, int userId, String message, Date date) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.date = date;
    }

    // Getters & Setters

    /**
     * Returns the notification's unique ID.
     *
     * @return the notification ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the notification's unique ID.
     *
     * @param id the notification ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the user ID associated with this notification.
     *
     * @return the user ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with this notification.
     *
     * @param userId the user ID to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Returns the message content of the notification.
     *
     * @return the notification message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message content of the notification.
     *
     * @param message the notification message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the date when the notification was created.
     *
     * @return the notification date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date when the notification was created.
     *
     * @param date the notification date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
