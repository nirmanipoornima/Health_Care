package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout layoutAddMedicine = findViewById(R.id.layoutAddMedicine);
        layoutAddMedicine.setOnClickListener(v -> startActivity(new Intent(this, AddMedicineActivity.class)));

        LinearLayout layoutBuyMedicine = findViewById(R.id.layoutBuyMedicine);
        layoutBuyMedicine.setOnClickListener(v -> startActivity(new Intent(this, Buy_Medicine.class)));

        LinearLayout layoutFindDoctor = findViewById(R.id.layoutFindDoctor);
        layoutFindDoctor.setOnClickListener(v -> startActivity(new Intent(this, FindDoctorActivity2.class)));

        LinearLayout layoutSearchHistory = findViewById(R.id.layoutSearchHistory);
        layoutSearchHistory.setOnClickListener(v -> startActivity(new Intent(this, SearchHistoryActivity.class)));

        LinearLayout layoutHealthArticles = findViewById(R.id.layoutHealthArticles);
        layoutHealthArticles.setOnClickListener(v -> startActivity(new Intent(this, HealthArticlesActivity.class)));

        LinearLayout layoutUploadArticle = findViewById(R.id.layoutUploadArticle);
        layoutUploadArticle.setOnClickListener(v -> startActivity(new Intent(this, UploadArticleActivity.class)));

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));

    }
}
