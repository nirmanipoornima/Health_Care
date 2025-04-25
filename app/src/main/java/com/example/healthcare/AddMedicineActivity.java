package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMedicineActivity extends AppCompatActivity {

    EditText medName, medDetails, medPrice;
    Button addButton, buttonBack;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        db = new DatabaseHelper(this);

        medName = findViewById(R.id.editTextMedName);
        medDetails = findViewById(R.id.editTextMedDetails);
        medPrice = findViewById(R.id.editTextMedPrice);
        addButton = findViewById(R.id.buttonAddMedicine);
        buttonBack = findViewById(R.id.buttonHABack);

        addButton.setOnClickListener(view -> {
            String name = medName.getText().toString().trim();
            String details = medDetails.getText().toString().trim();
            String price = medPrice.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price)) {
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            db.insertMedicine(name, details, price);
            Toast.makeText(this, "Medicine added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Return to previous activity
        });

        buttonBack.setOnClickListener(v -> {
            startActivity(new Intent(AddMedicineActivity.this, HomeActivity.class));
        });
    }
}
