package com.example.munir_015__assignment_2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class UserRegistration extends Activity {


    EditText etName, etEmail, etPassword;
    Button btnRegister;

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);

        // Initialize views
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);

        // Initialize DatabaseHelper
        databaseHelper = DatabaseHelper.getInstance(this);

        // Set click listener for register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // Perform registration process
                long userId = registerUser(name, email, password);
                if (userId != -1) {
                    // Registration successful
                    Toast.makeText(UserRegistration.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    // Start the Login activity
                    Intent intent = new Intent(UserRegistration.this, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Registration failed
                    Toast.makeText(UserRegistration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private long registerUser(String name, String email, String password) {
        // Get an instance of the database
        SQLiteDatabase db = DatabaseHelper.databaseHelper.getWritableDatabase();

        // Create a ContentValues object to hold the user data
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);

        // Insert the user data into the database
        long result = db.insert(DatabaseHelper.TABLE_USERS, null, values);

        // Close the database
        db.close();

        // Return the ID of the newly inserted user
        return result;
    }


    /**
     * Database helper class to manage the creation and upgrading of the database.
     */
    public static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "users.db";
        private static final int DATABASE_VERSION = 1;

        public static final String TABLE_USERS = "users";
        public static final String COLUMN_ID = "_id";
        private static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";

        public static DatabaseHelper databaseHelper;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public static synchronized DatabaseHelper getInstance(Context context) {
            if (databaseHelper == null) {
                databaseHelper = new DatabaseHelper(context.getApplicationContext());
            }
            return databaseHelper;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Create the users table
            String createTable = "CREATE TABLE " + TABLE_USERS + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT NOT NULL, "
                    + COLUMN_EMAIL + " TEXT NOT NULL, "
                    + COLUMN_PASSWORD + " TEXT NOT NULL);";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop the users table if it exists
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

            // Create the users table
            onCreate(db);
        }
    }

}
