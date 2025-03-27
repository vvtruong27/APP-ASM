package com.example.expensesmanagement.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.expensesmanagement.Models.Budget;
import com.example.expensesmanagement.Models.Expense;
import com.example.expensesmanagement.Models.Notification;
import com.example.expensesmanagement.Models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseService extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense_management.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng
    public static final String TABLE_USER = "User";
    public static final String TABLE_BUDGET = "Budget";
    public static final String TABLE_EXPENSE = "Expense";
    public static final String TABLE_NOTIFICATION = "Notification";

    // Câu lệnh tạo bảng User
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "username TEXT, "
            + "password TEXT, "
            + "email TEXT, "
            + "role TEXT"
            + ")";

    // Câu lệnh tạo bảng Budget
    private static final String CREATE_TABLE_BUDGET = "CREATE TABLE IF NOT EXISTS " + TABLE_BUDGET + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "user_id INTEGER, "
            + "name TEXT, "
            + "limit_amount REAL, "
            + "remaining_amount REAL, "
            + "FOREIGN KEY(user_id) REFERENCES " + TABLE_USER + "(id)"
            + ")";

    // Câu lệnh tạo bảng Expense
    private static final String CREATE_TABLE_EXPENSE = "CREATE TABLE IF NOT EXISTS " + TABLE_EXPENSE + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "budget_id INTEGER, "
            + "amount REAL, "
            + "description TEXT, "
            + "date TEXT, "  // Lưu dưới dạng TEXT
            + "is_recurring INTEGER, " // 0: không lặp lại, 1: lặp lại
            + "FOREIGN KEY(budget_id) REFERENCES " + TABLE_BUDGET + "(id)"
            + ")";

    // Câu lệnh tạo bảng Notification
    private static final String CREATE_TABLE_NOTIFICATION = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATION + "("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "user_id INTEGER, "
            + "message TEXT, "
            + "date TEXT, "
            + "FOREIGN KEY(user_id) REFERENCES " + TABLE_USER + "(id)"
            + ")";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DatabaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void insertMockData(SQLiteDatabase db) {
        // Giữ nguyên dữ liệu bảng User
        db.execSQL("INSERT INTO User (username, password, email, role) VALUES " +
                "('admin', 'admin123', 'admin@example.com', 'admin'), " +
                "('student1', 'pass123', 'student1@example.com', 'student'), " +
                "('student2', 'pass456', 'student2@example.com', 'student'), " +
                "('student3', 'pass789', 'student3@example.com', 'student'), " +
                "('student4', 'pass000', 'student4@example.com', 'student');");

        // Giữ nguyên dữ liệu bảng Budget
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

        // **Thêm rất nhiều dữ liệu vào bảng Expense (40 dòng)**
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

    // Tạo database nếu chưa tồn tại
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_BUDGET);
        db.execSQL(CREATE_TABLE_EXPENSE);
        db.execSQL(CREATE_TABLE_NOTIFICATION);

        insertMockData(db);
    }

    // Cập nhật database khi phiên bản thay đổi
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
    public Cursor getUserByUsernameAndPassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";
        return db.rawQuery(query, new String[]{username, password});
    }

    // Kiểm tra xem username đã tồn tại hay chưa
    public boolean isUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM User WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Thêm user mới vào database
    public boolean insertUser(String username, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);
        values.put("role", role);

        long result = db.insert("User", null, values);
        return result != -1; // Trả về true nếu chèn thành công, false nếu thất bại
    }

}