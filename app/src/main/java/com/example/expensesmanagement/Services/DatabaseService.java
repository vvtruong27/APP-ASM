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

    // Tạo database nếu chưa tồn tại
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_BUDGET);
        db.execSQL(CREATE_TABLE_EXPENSE);
        db.execSQL(CREATE_TABLE_NOTIFICATION);
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