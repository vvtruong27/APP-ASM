package com.example.expensesmanagement.Activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensesmanagement.Activities.Auth.Login;
import com.example.expensesmanagement.Activities.Auth.Register;
import com.example.expensesmanagement.R;
import com.example.expensesmanagement.Services.DatabaseService;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin, btnRegister;
    private DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Khởi tạo database ngay khi mở ứng dụng
        databaseService = new DatabaseService(this);
        SQLiteDatabase db = databaseService.getReadableDatabase();  // Mở database để đảm bảo có kết nối
        // Ánh xạ view
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Sự kiện click nút Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        // Sự kiện click nút Register
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
