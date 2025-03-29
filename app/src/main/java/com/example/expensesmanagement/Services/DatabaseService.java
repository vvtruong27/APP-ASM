package com.example.expensesmanagement.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensesmanagement.Models.Budget;
import com.example.expensesmanagement.Models.Expense;
import com.example.expensesmanagement.Models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Locale;


public class DatabaseService extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "expense_management.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_USER = "User";
    public static final String TABLE_BUDGET = "Budget";
    public static final String TABLE_EXPENSE = "Expense";
    public static final String TABLE_NOTIFICATION = "Notification";

    // SQL statement to create the User table
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "username TEXT, "
            + "password TEXT, "
            + "email TEXT, "
            + "role TEXT"
            + ")";

    // SQL statement to create the Budget table
    private static final String CREATE_TABLE_BUDGET = "CREATE TABLE IF NOT EXISTS " + TABLE_BUDGET + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "user_id INTEGER, "
            + "name TEXT, "
            + "limit_amount REAL, "
            + "remaining_amount REAL, "
            + "FOREIGN KEY(user_id) REFERENCES " + TABLE_USER + "(id)"
            + ")";

    // SQL statement to create the Expense table
    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE IF NOT EXISTS " + TABLE_EXPENSE + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "budget_id INTEGER, "
            + "amount REAL, "
            + "description TEXT, "
            + "date TEXT, "  // Stored as TEXT
            + "is_recurring INTEGER, " // 0: non-recurring, 1: recurring
            + "FOREIGN KEY(budget_id) REFERENCES " + TABLE_BUDGET + "(id)"
            + ")";

    // SQL statement to create the Notification table
    private static final String CREATE_TABLE_NOTIFICATION = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATION + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "user_id INTEGER, "
            + "message TEXT, "
            + "date TEXT, "
            + "FOREIGN KEY(user_id) REFERENCES " + TABLE_USER + "(id)"
            + ")";

    // Date format used for storing and parsing dates
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DatabaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Inserts mock data into the database.
     *
     * @param db The SQLiteDatabase instance.
     */
    private void insertMockData(SQLiteDatabase db) {
        // Insert data into User table
        db.execSQL("INSERT INTO User (username, password, email, role) VALUES " +
                "('admin', 'admin123', 'admin@example.com', 'admin'), " +
                "('student1', 'pass123', 'student1@example.com', 'student'), " +
                "('student2', 'pass456', 'student2@example.com', 'student'), " +
                "('student3', 'pass789', 'student3@example.com', 'student'), " +
                "('student4', 'pass000', 'student4@example.com', 'student');");

        // Insert data into Budget table
        db.execSQL("INSERT INTO Budget (user_id, name, limit_amount, remaining_amount) VALUES " +
                "(2, 'Monthly Budget', 5000, 3000), " +
                "(2, 'Food Budget', 2000, 1200), " +
                "(2, 'Transport', 1500, 700), " +
                "(3, 'Entertainment', 3000, 1500), " +
                "(3, 'Shopping', 2500, 1000), " +
                "(4, 'Emergency Fund', 7000, 6500), " +
                "(4, 'Healthcare', 2000, 1800), " +
                "(5, 'Travel Savings', 10000, 8000), " +
                "(5, 'Subscriptions', 1200, 800);");

        // Insert many rows into Expense table (40 rows)
        db.execSQL("INSERT INTO Expense (budget_id, amount, description, date, is_recurring) VALUES " +
                "(1, 100, 'Grocery Shopping', '2025-03-01 10:00:00', 0), " +
                "(1, 200, 'Electricity Bill', '2025-03-05 15:00:00', 0), " +
                "(1, 50, 'Water Bill', '2025-03-07 08:00:00', 0), " +
                "(2, 50, 'Lunch', '2025-03-02 12:30:00', 1), " +
                "(2, 120, 'Dinner at restaurant', '2025-03-04 19:30:00', 0), " +
                "(3, 200, 'Concert Ticket', '2025-03-03 19:00:00', 0), " +
                "(3, 80, 'Movie Night', '2025-03-06 20:00:00', 0), " +
                "(4, 300, 'Taxi Fare', '2025-03-07 08:30:00', 0), " +
                "(4, 50, 'Bus Ticket', '2025-03-08 09:00:00', 1), " +
                "(5, 500, 'New Clothes', '2025-03-09 14:00:00', 0), " +
                "(5, 200, 'Shoes', '2025-03-11 16:00:00', 0), " +
                "(6, 1000, 'Emergency Fund', '2025-03-10 10:00:00', 0), " +
                "(7, 400, 'Doctor Visit', '2025-03-12 11:00:00', 0), " +
                "(8, 3000, 'Flight Ticket', '2025-03-15 08:00:00', 0), " +
                "(8, 2000, 'Hotel Booking', '2025-03-16 14:00:00', 0), " +
                "(9, 150, 'Netflix Subscription', '2025-03-17 18:00:00', 1), " +
                "(9, 100, 'Spotify Premium', '2025-03-18 19:00:00', 1), " +
                "(2, 300, 'Weekend Groceries', '2025-03-19 09:00:00', 0), " +
                "(3, 250, 'Theme Park Ticket', '2025-03-20 13:00:00', 0), " +
                "(3, 90, 'Streaming Subscription', '2025-03-21 20:00:00', 1), " +
                "(4, 150, 'Gasoline Refill', '2025-03-22 07:30:00', 0), " +
                "(5, 4000, 'International Flight', '2025-03-23 06:00:00', 0), " +
                "(5, 600, 'Rental Car', '2025-03-24 12:00:00', 0), " +
                "(2, 50, 'Coffee at Starbucks', '2025-03-25 08:00:00', 0), " +
                "(2, 30, 'Snacks', '2025-03-26 10:00:00', 0), " +
                "(3, 500, 'Concert Merchandise', '2025-03-27 18:00:00', 0), " +
                "(4, 100, 'Medication', '2025-03-28 14:00:00', 0), " +
                "(5, 1000, 'Visa Application Fee', '2025-03-29 11:00:00', 0), " +
                "(2, 250, 'Birthday Dinner', '2025-03-30 20:00:00', 0), " +
                "(3, 200, 'Gaming Subscription', '2025-03-31 23:00:00', 1), " +
                "(3, 300, 'New Headphones', '2025-04-01 09:00:00', 0), " +
                "(4, 700, 'Health Insurance', '2025-04-02 10:00:00', 0), " +
                "(5, 500, 'Museum Visit', '2025-04-03 15:00:00', 0), " +
                "(5, 1200, 'Luxury Resort Stay', '2025-04-04 16:00:00', 0), " +
                "(2, 80, 'Ice Cream Treat', '2025-04-05 13:00:00', 0), " +
                "(3, 900, 'Premium Gym Membership', '2025-04-06 06:00:00', 1), " +
                "(4, 300, 'Eye Checkup', '2025-04-07 10:00:00', 0), " +
                "(5, 2500, 'Cruise Trip Deposit', '2025-04-08 17:00:00', 0);");

        // Insert data into Notification table
        db.execSQL("INSERT INTO Notification (user_id, message, date) VALUES " +
                "(2, 'Your food budget is running low!', '2025-03-05 08:00:00'), " +
                "(2, 'New expense added: Dinner at restaurant', '2025-03-06 14:30:00'), " +
                "(3, 'Reminder: Check your budget!', '2025-03-07 09:00:00'), " +
                "(3, 'Entertainment budget is almost depleted!', '2025-03-08 10:00:00'), " +
                "(4, 'Your emergency fund is growing!', '2025-03-09 11:00:00'), " +
                "(4, 'Healthcare budget used: Doctor Visit', '2025-03-12 12:00:00'), " +
                "(5, 'Your travel budget has been updated!', '2025-03-15 13:00:00'), " +
                "(5, 'Subscription alert: Netflix charged', '2025-03-17 14:00:00'), " +
                "(5, 'Subscription alert: Spotify charged', '2025-03-18 15:00:00'), " +
                "(2, 'Grocery Shopping alert: $300 spent!', '2025-03-19 16:00:00'), " +
                "(3, 'Theme Park expense added!', '2025-03-20 17:00:00'), " +
                "(3, 'New Streaming subscription detected!', '2025-03-21 18:00:00'), " +
                "(4, 'Gasoline refill alert!', '2025-03-22 19:00:00'), " +
                "(5, 'International Flight booked successfully!', '2025-03-23 20:00:00'), " +
                "(5, 'Rental car expense recorded!', '2025-03-24 21:00:00');");
    }

    /**
     * Creates the database tables if they do not already exist.
     *
     * @param db The SQLiteDatabase instance.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_BUDGET);
        db.execSQL(CREATE_TABLE_EXPENSE);
        db.execSQL(CREATE_TABLE_NOTIFICATION);

        insertMockData(db);
    }

    /**
     * Upgrades the database by dropping existing tables and recreating them.
     *
     * @param db         The SQLiteDatabase instance.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    /**
     * Retrieves a user by username and password.
     *
     * @param username The username.
     * @param password The password.
     * @return A Cursor pointing to the user record.
     */
    public Cursor getUserByUsernameAndPassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";
        return db.rawQuery(query, new String[]{username, password});
    }

    /**
     * Checks if a username already exists.
     *
     * @param username The username to check.
     * @return True if the user exists, false otherwise.
     */
    public boolean isUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM User WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    /**
     * Inserts a new user into the database.
     *
     * @param username The username.
     * @param email    The email address.
     * @param password The password.
     * @param role     The user's role.
     * @return True if insertion is successful, false otherwise.
     */
    public boolean insertUser(String username, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);
        values.put("role", role);

        long result = db.insert("User", null, values);
        return result != -1;
    }

    /**
     * Retrieves a list of budgets for a given user.
     *
     * @param userId The user ID.
     * @return A List of Budget objects.
     */
    public List<Budget> getBudgetsByUserId(int userId) {
        List<Budget> budgets = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Budget WHERE user_id = ?", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                budgets.add(new Budget(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getDouble(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return budgets;
    }

    /**
     * Deletes a budget from the database.
     *
     * @param budgetId The budget ID to delete.
     */
    public void deleteBudget(int budgetId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Budget", "id = ?", new String[]{String.valueOf(budgetId)});
    }

    /**
     * Inserts a new budget into the database.
     *
     * @param userId     The ID of the user associated with the budget.
     * @param name       The name of the budget.
     * @param limitAmount The budget limit.
     * @return True if insertion is successful, false otherwise.
     */
    public boolean insertBudget(int userId, String name, double limitAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("name", name);
        values.put("limit_amount", limitAmount);
        values.put("remaining_amount", limitAmount);  // Initially, remaining equals the limit

        long result = db.insert(TABLE_BUDGET, null, values);
        return result != -1;
    }

    /**
     * Retrieves a Budget object by its ID.
     *
     * @param budgetId The budget ID.
     * @return The corresponding Budget object, or null if not found.
     */
    public Budget getBudgetById(int budgetId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BUDGET + " WHERE id = ?", new String[]{String.valueOf(budgetId)});
        if (cursor != null && cursor.moveToFirst()) {
            Budget budget = new Budget(
                    cursor.getInt(0),  // id
                    cursor.getInt(1),  // user_id
                    cursor.getString(2),  // name
                    cursor.getDouble(3),  // limit_amount
                    cursor.getDouble(4)   // remaining_amount
            );
            cursor.close();
            return budget;
        }
        return null;
    }

    /**
     * Updates a budget's name and limit amount, recalculating the remaining amount.
     *
     * @param budgetId The budget ID.
     * @param name     The new name.
     * @param newLimit The new budget limit.
     * @return True if update is successful, false otherwise.
     */
    public boolean updateBudget(int budgetId, String name, double newLimit) {
        // Retrieve current budget
        Budget currentBudget = getBudgetById(budgetId);
        if (currentBudget == null) {
            return false;
        }

        // Calculate total spent: totalSpent = oldLimit - oldRemaining
        double totalSpent = currentBudget.getLimitAmount() - currentBudget.getRemainingAmount();

        // Calculate new remaining: newRemaining = newLimit - totalSpent
        double newRemaining = newLimit - totalSpent;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("limit_amount", newLimit);
        values.put("remaining_amount", newRemaining);

        int rows = db.update(TABLE_BUDGET, values, "id = ?", new String[]{String.valueOf(budgetId)});
        return rows > 0;
    }

    /**
     * Retrieves all users except those with the 'admin' role.
     *
     * @return A List of User objects.
     */
    public List<User> getAllUsersExceptAdmin() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE role != 'admin'", null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("username")),
                        cursor.getString(cursor.getColumnIndexOrThrow("password")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("role"))
                );
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId The user ID to delete.
     */
    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("User", "id = ?", new String[]{String.valueOf(userId)});
    }

    /**
     * Retrieves a user by ID.
     *
     * @param userId The user ID.
     * @return The corresponding User object, or null if not found.
     */
    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE id = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password")), // required
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("role"))
            );
            cursor.close();
            return user;
        }
        return null;
    }

    /**
     * Updates a user's information.
     *
     * @param userId   The user ID.
     * @param username The new username.
     * @param email    The new email.
     * @param role     The new role.
     * @return True if update is successful, false otherwise.
     */
    public boolean updateUser(int userId, String username, String email, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("role", role);

        int rows = db.update("User", values, "id = ?", new String[]{String.valueOf(userId)});
        return rows > 0;
    }

    /**
     * Retrieves all expenses for a given user by joining Expense and Budget tables.
     *
     * @param userId The user ID.
     * @return A List of Expense objects.
     */
    public List<Expense> getExpensesByUserId(int userId) {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Query expenses through the Budget table to get the expenses of the current user
        String query = "SELECT E.id, E.budget_id, E.amount, E.description, E.date, E.is_recurring " +
                "FROM " + TABLE_EXPENSE + " E INNER JOIN " + TABLE_BUDGET + " B ON E.budget_id = B.id " +
                "WHERE B.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setId(cursor.getInt(0));
                expense.setBudgetId(cursor.getInt(1));
                expense.setAmount(cursor.getDouble(2));
                expense.setDescription(cursor.getString(3));
                try {
                    expense.setDate(dateFormat.parse(cursor.getString(4)));
                } catch (ParseException e) {
                    expense.setDate(new Date());
                }
                expense.setRecurring(cursor.getInt(5) == 1);
                expenses.add(expense);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return expenses;
    }

    /**
     * Deletes an expense and updates the corresponding budget's remaining amount.
     *
     * @param expenseId The expense ID to delete.
     */
    public void deleteExpense(int expenseId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // 1. Get the budget_id of the expense to be deleted
        Cursor cursor = db.rawQuery("SELECT budget_id FROM " + TABLE_EXPENSE + " WHERE id = ?", new String[]{String.valueOf(expenseId)});
        int budgetId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            budgetId = cursor.getInt(0);
            cursor.close();
        }

        // 2. Delete the expense
        db.delete(TABLE_EXPENSE, "id = ?", new String[]{String.valueOf(expenseId)});

        // 3. If budget_id is found, recalculate the remaining_amount
        if (budgetId != -1) {
            // Calculate total spent for this budget (after deletion)
            Cursor cursorSum = db.rawQuery("SELECT SUM(amount) FROM " + TABLE_EXPENSE + " WHERE budget_id = ?", new String[]{String.valueOf(budgetId)});
            double totalSpent = 0;
            if (cursorSum != null && cursorSum.moveToFirst()) {
                totalSpent = cursorSum.getDouble(0);
                cursorSum.close();
            }

            // Get the limit_amount of that budget
            Cursor cursorBudget = db.rawQuery("SELECT limit_amount FROM " + TABLE_BUDGET + " WHERE id = ?", new String[]{String.valueOf(budgetId)});
            double limitAmount = 0;
            if (cursorBudget != null && cursorBudget.moveToFirst()) {
                limitAmount = cursorBudget.getDouble(0);
                cursorBudget.close();
            }

            // Calculate new remaining_amount: newRemaining = limit_amount - totalSpent
            double newRemaining = limitAmount - totalSpent;

            // Update remaining_amount in the Budget table
            ContentValues values = new ContentValues();
            values.put("remaining_amount", newRemaining);
            db.update(TABLE_BUDGET, values, "id = ?", new String[]{String.valueOf(budgetId)});
        }
    }

    /**
     * Inserts a new expense into the database and updates the corresponding budget's remaining amount.
     *
     * @param budgetId     The budget ID to which the expense belongs.
     * @param amount       The expense amount.
     * @param description  The expense description.
     * @param date         The expense date as a String.
     * @param isRecurring  True if the expense is recurring, false otherwise.
     * @return True if insertion is successful, false otherwise.
     */
    public boolean insertExpense(int budgetId, double amount, String description, String date, boolean isRecurring) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("budget_id", budgetId);
        values.put("amount", amount);
        values.put("description", description);
        values.put("date", date);
        values.put("is_recurring", isRecurring ? 1 : 0);
        long result = db.insert(TABLE_EXPENSE, null, values);

        if (result != -1) {
            // Update remaining_amount for the budget after adding the expense
            Cursor cursor = db.rawQuery("SELECT limit_amount FROM " + TABLE_BUDGET + " WHERE id = ?", new String[]{String.valueOf(budgetId)});
            double limitAmount = 0;
            if (cursor != null && cursor.moveToFirst()) {
                limitAmount = cursor.getDouble(0);
                cursor.close();
            }
            Cursor cursorSum = db.rawQuery("SELECT SUM(amount) FROM " + TABLE_EXPENSE + " WHERE budget_id = ?", new String[]{String.valueOf(budgetId)});
            double totalSpent = 0;
            if (cursorSum != null && cursorSum.moveToFirst()) {
                totalSpent = cursorSum.getDouble(0);
                cursorSum.close();
            }
            double newRemaining = limitAmount - totalSpent;
            ContentValues updateValues = new ContentValues();
            updateValues.put("remaining_amount", newRemaining);
            db.update(TABLE_BUDGET, updateValues, "id = ?", new String[]{String.valueOf(budgetId)});

            // If total spent exceeds the limit, create a notification
            if (newRemaining < 0) {
                // Get user_id from the Budget table
                Cursor cursorBudget = db.rawQuery("SELECT user_id FROM " + TABLE_BUDGET + " WHERE id = ?", new String[]{String.valueOf(budgetId)});
                int userId = -1;
                if (cursorBudget != null && cursorBudget.moveToFirst()) {
                    userId = cursorBudget.getInt(0);
                    cursorBudget.close();
                }
                // Create the notification message
                String message = "Budget exceeded! You have spent $" + totalSpent + " which exceeds your limit of $" + limitAmount;
                // Get current time in the specified format
                String currentDate = dateFormat.format(new Date());
                ContentValues notifValues = new ContentValues();
                notifValues.put("user_id", userId);
                notifValues.put("message", message);
                notifValues.put("date", currentDate);
                db.insert(TABLE_NOTIFICATION, null, notifValues);
            }
            return true;
        }
        return false;
    }

    /**
     * Retrieves the latest notification message for a given user.
     *
     * @param userId The user ID.
     * @return The latest notification message, or null if none exists.
     */
    public String getLatestNotificationForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT message FROM " + TABLE_NOTIFICATION + " WHERE user_id = ? ORDER BY id DESC LIMIT 1", new String[]{String.valueOf(userId)});
        String message = null;
        if (cursor != null && cursor.moveToFirst()) {
            message = cursor.getString(0);
            cursor.close();
        }
        return message;
    }

    /**
     * Retrieves an Expense object by its ID.
     *
     * @param expenseId The expense ID.
     * @return The corresponding Expense object, or null if not found.
     */
    public Expense getExpenseById(int expenseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE + " WHERE id = ?", new String[]{String.valueOf(expenseId)});
        Expense expense = null;
        if (cursor != null && cursor.moveToFirst()) {
            expense = new Expense();
            expense.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            expense.setBudgetId(cursor.getInt(cursor.getColumnIndexOrThrow("budget_id")));
            expense.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("amount")));
            expense.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
            try {
                expense.setDate(dateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow("date"))));
            } catch (ParseException e) {
                expense.setDate(new Date());
            }
            expense.setRecurring(cursor.getInt(cursor.getColumnIndexOrThrow("is_recurring")) == 1);
        }
        if (cursor != null) {
            cursor.close();
        }
        return expense;
    }

    /**
     * Updates an expense in the database and recalculates remaining amounts for affected budgets.
     *
     * @param expenseId    The expense ID.
     * @param newBudgetId  The new budget ID.
     * @param amount       The updated expense amount.
     * @param description  The updated expense description.
     * @param date         The updated expense date as a String.
     * @param isRecurring  True if the expense is recurring, false otherwise.
     * @return True if update is successful, false otherwise.
     */
    public boolean updateExpense(int expenseId, int newBudgetId, double amount, String description, String date, boolean isRecurring) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Retrieve the old expense details
        Expense oldExpense = getExpenseById(expenseId);
        if (oldExpense == null) {
            return false;
        }
        int oldBudgetId = oldExpense.getBudgetId();

        ContentValues values = new ContentValues();
        values.put("budget_id", newBudgetId);
        values.put("amount", amount);
        values.put("description", description);
        values.put("date", date);
        values.put("is_recurring", isRecurring ? 1 : 0);

        int rows = db.update(TABLE_EXPENSE, values, "id = ?", new String[]{String.valueOf(expenseId)});
        if (rows > 0) {
            // Update remaining_amount for the old budget
            updateBudgetRemaining(oldBudgetId);
            // If the budget has changed, also update the new budget
            if (oldBudgetId != newBudgetId) {
                updateBudgetRemaining(newBudgetId);
            }
            return true;
        }
        return false;
    }

    /**
     * Helper method to update the remaining amount for a budget based on its expenses.
     *
     * @param budgetId The budget ID.
     */
    private void updateBudgetRemaining(int budgetId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Retrieve the limit_amount of the budget
        Cursor cursorLimit = db.rawQuery("SELECT limit_amount FROM " + TABLE_BUDGET + " WHERE id = ?", new String[]{String.valueOf(budgetId)});
        double limitAmount = 0;
        if (cursorLimit != null && cursorLimit.moveToFirst()) {
            limitAmount = cursorLimit.getDouble(0);
            cursorLimit.close();
        }
        // Calculate total spent for the budget
        Cursor cursorSum = db.rawQuery("SELECT SUM(amount) FROM " + TABLE_EXPENSE + " WHERE budget_id = ?", new String[]{String.valueOf(budgetId)});
        double totalSpent = 0;
        if (cursorSum != null && cursorSum.moveToFirst()) {
            totalSpent = cursorSum.getDouble(0);
            cursorSum.close();
        }
        double newRemaining = limitAmount - totalSpent;
        ContentValues updateValues = new ContentValues();
        updateValues.put("remaining_amount", newRemaining);
        db.update(TABLE_BUDGET, updateValues, "id = ?", new String[]{String.valueOf(budgetId)});
    }
    // Lấy tổng chi tiêu theo ngày trong khoảng start -> end
    public List<Float> getDailyExpenseTotals(Date start, Date end) {
        List<Float> dailyTotals = new ArrayList<>();
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        SQLiteDatabase db = this.getReadableDatabase();

        while (!calendar.getTime().after(end)) {
            String day = dbFormat.format(calendar.getTime());
            String likePattern = day + "%"; // date LIKE '2025-03-01%'
            Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM Expense WHERE date LIKE ?", new String[]{likePattern});
            float total = 0;
            if (cursor.moveToFirst()) {
                total = cursor.isNull(0) ? 0 : cursor.getFloat(0);
            }
            dailyTotals.add(total);
            cursor.close();
            calendar.add(Calendar.DATE, 1);
        }

        return dailyTotals;
    }

    // Đếm số lần chi tiêu vượt quá ngân sách trong khoảng thời gian
    public int countExpensesOverBudget(Date start, Date end) {
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String startDate = format.format(start);
        String endDate = format.format(end);

        String query = "SELECT COUNT(*) " +
                "FROM Expense e JOIN Budget b ON e.budget_id = b.id " +
                "WHERE e.amount > b.limit_amount " +
                "AND e.date BETWEEN ? AND ?";

        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
    // Đếm số expense trong khoảng thời gian
    public int getExpenseCount(Date start, Date end) {
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        String startStr = format.format(getStartOfDay(start));
        String endStr = format.format(getEndOfDay(end));

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Expense WHERE date BETWEEN ? AND ?", new String[]{startStr, endStr});
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public float getTotalSpent(Date start, Date end) {
        SQLiteDatabase db = this.getReadableDatabase();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        String startStr = format.format(getStartOfDay(start));
        String endStr = format.format(getEndOfDay(end));

        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM Expense WHERE date BETWEEN ? AND ?", new String[]{startStr, endStr});
        float total = 0;
        if (cursor.moveToFirst()) total = cursor.isNull(0) ? 0f : cursor.getFloat(0);
        cursor.close();
        return total;
    }


    // Helpers để lấy đầu ngày và cuối ngày
    private Date getStartOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date getEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public List<Integer> getOverBudgetCountsPerDay(Date start, Date end) {
        List<Integer> overBudgetCounts = new ArrayList<>();
        SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);

        SQLiteDatabase db = this.getReadableDatabase();

        while (!calendar.getTime().after(end)) {
            String day = dbFormat.format(calendar.getTime());
            String likePattern = day + "%"; // date LIKE '2025-03-01%'

            // Đếm số lần chi tiêu vượt quá ngân sách trong ngày
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM Expense e JOIN Budget b ON e.budget_id = b.id " +
                            "WHERE e.amount > b.limit_amount AND e.date LIKE ?", new String[]{likePattern}
            );

            int count = 0;
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0); // Lấy số lần chi tiêu vượt ngân sách trong ngày
            }
            overBudgetCounts.add(count);
            cursor.close();

            // Tiến đến ngày tiếp theo
            calendar.add(Calendar.DATE, 1);
        }

        return overBudgetCounts;
    }
}