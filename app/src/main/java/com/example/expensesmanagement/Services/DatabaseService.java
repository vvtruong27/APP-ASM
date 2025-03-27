package com.example.expensesmanagement.Services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            + "date TEXT, "  // Lưu dưới dạng TEXT hoặc có thể dùng kiểu INTEGER (UNIX timestamp)
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

    public DatabaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Phương thức này được gọi khi database được tạo lần đầu
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_BUDGET);
        db.execSQL(CREATE_TABLE_EXPENSE);
        db.execSQL(CREATE_TABLE_NOTIFICATION);
    }

    // Phương thức này được gọi khi có sự thay đổi về cấu trúc database (phiên bản mới)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Tạo lại bảng
        onCreate(db);
    }
}
