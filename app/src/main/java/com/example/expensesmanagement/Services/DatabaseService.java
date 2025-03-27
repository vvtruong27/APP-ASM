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

    // ===================== CRUD cho User =====================

    // Create
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("role", user.getRole());
        long id = db.insert(TABLE_USER, null, values);
        db.close();
        return id;
    }

    // Read
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{"id", "username", "password", "email", "role"},
                "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setRole(cursor.getString(4));
            cursor.close();
            db.close();
            return user;
        }
        db.close();
        return null;
    }

    // Update
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("role", user.getRole());
        int rows = db.update(TABLE_USER, values, "id = ?", new String[]{String.valueOf(user.getId())});
        db.close();
        return rows;
    }

    // Delete
    public int deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_USER, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    // ===================== CRUD cho Budget =====================

    public long addBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", budget.getUserId());
        values.put("name", budget.getName());
        values.put("limit_amount", budget.getLimitAmount());
        values.put("remaining_amount", budget.getRemainingAmount());
        long id = db.insert(TABLE_BUDGET, null, values);
        db.close();
        return id;
    }

    public Budget getBudget(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BUDGET, new String[]{"id", "user_id", "name", "limit_amount", "remaining_amount"},
                "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Budget budget = new Budget();
            budget.setId(cursor.getInt(0));
            budget.setUserId(cursor.getInt(1));
            budget.setName(cursor.getString(2));
            budget.setLimitAmount(cursor.getDouble(3));
            budget.setRemainingAmount(cursor.getDouble(4));
            cursor.close();
            db.close();
            return budget;
        }
        db.close();
        return null;
    }

    public List<Budget> getBudgetsByUser(int userId) {
        List<Budget> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BUDGET, new String[]{"id", "user_id", "name", "limit_amount", "remaining_amount"},
                "user_id=?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Budget budget = new Budget();
                budget.setId(cursor.getInt(0));
                budget.setUserId(cursor.getInt(1));
                budget.setName(cursor.getString(2));
                budget.setLimitAmount(cursor.getDouble(3));
                budget.setRemainingAmount(cursor.getDouble(4));
                list.add(budget);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }

    public int updateBudget(Budget budget) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", budget.getUserId());
        values.put("name", budget.getName());
        values.put("limit_amount", budget.getLimitAmount());
        values.put("remaining_amount", budget.getRemainingAmount());
        int rows = db.update(TABLE_BUDGET, values, "id = ?", new String[]{String.valueOf(budget.getId())});
        db.close();
        return rows;
    }

    public int deleteBudget(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_BUDGET, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    // ===================== CRUD cho Expense =====================

    public long addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("budget_id", expense.getBudgetId());
        values.put("amount", expense.getAmount());
        values.put("description", expense.getDescription());
        // Lưu ngày dưới dạng chuỗi
        values.put("date", dateFormat.format(expense.getDate()));
        values.put("is_recurring", expense.isRecurring() ? 1 : 0);
        long id = db.insert(TABLE_EXPENSE, null, values);
        db.close();
        return id;
    }

    public Expense getExpense(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXPENSE, new String[]{"id", "budget_id", "amount", "description", "date", "is_recurring"},
                "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
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
            cursor.close();
            db.close();
            return expense;
        }
        db.close();
        return null;
    }

    public List<Expense> getExpensesByBudget(int budgetId) {
        List<Expense> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXPENSE, new String[]{"id", "budget_id", "amount", "description", "date", "is_recurring"},
                "budget_id=?", new String[]{String.valueOf(budgetId)}, null, null, "date DESC");
        if (cursor != null && cursor.moveToFirst()) {
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
                list.add(expense);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }

    public int updateExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("budget_id", expense.getBudgetId());
        values.put("amount", expense.getAmount());
        values.put("description", expense.getDescription());
        values.put("date", dateFormat.format(expense.getDate()));
        values.put("is_recurring", expense.isRecurring() ? 1 : 0);
        int rows = db.update(TABLE_EXPENSE, values, "id = ?", new String[]{String.valueOf(expense.getId())});
        db.close();
        return rows;
    }

    public int deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_EXPENSE, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }

    // ===================== CRUD cho Notification =====================

    public long addNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", notification.getUserId());
        values.put("message", notification.getMessage());
        values.put("date", dateFormat.format(notification.getDate()));
        long id = db.insert(TABLE_NOTIFICATION, null, values);
        db.close();
        return id;
    }

    public Notification getNotification(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTIFICATION, new String[]{"id", "user_id", "message", "date"},
                "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Notification notification = new Notification();
            notification.setId(cursor.getInt(0));
            notification.setUserId(cursor.getInt(1));
            notification.setMessage(cursor.getString(2));
            try {
                notification.setDate(dateFormat.parse(cursor.getString(3)));
            } catch (ParseException e) {
                notification.setDate(new Date());
            }
            cursor.close();
            db.close();
            return notification;
        }
        db.close();
        return null;
    }

    public List<Notification> getNotificationsByUser(int userId) {
        List<Notification> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTIFICATION, new String[]{"id", "user_id", "message", "date"},
                "user_id=?", new String[]{String.valueOf(userId)}, null, null, "date DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Notification notification = new Notification();
                notification.setId(cursor.getInt(0));
                notification.setUserId(cursor.getInt(1));
                notification.setMessage(cursor.getString(2));
                try {
                    notification.setDate(dateFormat.parse(cursor.getString(3)));
                } catch (ParseException e) {
                    notification.setDate(new Date());
                }
                list.add(notification);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return list;
    }

    public int updateNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", notification.getUserId());
        values.put("message", notification.getMessage());
        values.put("date", dateFormat.format(notification.getDate()));
        int rows = db.update(TABLE_NOTIFICATION, values, "id = ?", new String[]{String.valueOf(notification.getId())});
        db.close();
        return rows;
    }

    public int deleteNotification(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_NOTIFICATION, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows;
    }
}
