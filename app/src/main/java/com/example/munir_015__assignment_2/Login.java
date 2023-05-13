package com.example.munir_015__assignment_2;

import static com.example.munir_015__assignment_2.UserRegistration.DatabaseHelper.*;


import android.app.Activity;
import com.example.munir_015__assignment_2.UserRegistration;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

    EditText etEmail, etPassword;
    Button btnLogin;
    private UserRegistration.DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        dbHelper = new UserRegistration.DatabaseHelper(this);

        // Initialize views
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        // Set click listener for login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Perform login process
                if (loginUser(email, password)) {
                    // Login successful
                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Quiz_Act1.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Login failed
                    Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean loginUser(String email, String password) {
        // Get an instance of the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies the columns from the table we care about
        String[] projection = {UserRegistration.DatabaseHelper.COLUMN_ID};

        // Filter results WHERE "email" = 'email' AND "password" = 'password'
        String selection = UserRegistration.DatabaseHelper.COLUMN_EMAIL + " = ?" + " AND " + UserRegistration.DatabaseHelper.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        // Query the database
        Cursor cursor = db.query(
                UserRegistration.DatabaseHelper.TABLE_USERS,  // The table to query
                projection,                 // The columns to return
                selection,                  // The columns for the WHERE clause
                selectionArgs,              // The values for the WHERE clause
                null,                       // Don't group the rows
                null,                       // Don't filter by row groups
                null                        // The sort order
        );

        // Check if the query returned a row
        boolean success = cursor.moveToFirst();

        // Close the cursor and database
        cursor.close();
        db.close();

        // Return true if login was successful, false otherwise
        return success;
    }



}
