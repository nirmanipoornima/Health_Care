
package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
public class RegisterActivity extends AppCompatActivity {
    EditText edName, edEmail, edPassword, edConfirmPassword;
    Button btnRegister;
    TextView tvLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        edName = findViewById(R.id.editTextBMBFullName);
        edEmail = findViewById(R.id.editTextBMBAddress);
        edPassword = findViewById(R.id.editTextAppContactNumber);
        edConfirmPassword = findViewById(R.id.editTextRegConfirmPassword);
        btnRegister = findViewById(R.id.buttonBMBBooking);
        tvLogin = findViewById(R.id.textViewExitingUser);
        db = new DatabaseHelper(this);

        btnRegister.setOnClickListener(view -> {
            String name = edName.getText().toString();
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            String confirm = edConfirmPassword.getText().toString();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirm)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                db.addUser(name, email, password);
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        tvLogin.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }
}
