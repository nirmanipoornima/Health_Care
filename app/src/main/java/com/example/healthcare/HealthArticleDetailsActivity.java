package com.example.healthcare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class HealthArticleDetailsActivity extends AppCompatActivity {

    TextView tvTitle;
    ImageView imgArticle;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_health_article_details);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTitle = findViewById(R.id.textViewHADtitle);
        imgArticle = findViewById(R.id.imageViewHAD);
        btnBack = findViewById(R.id.buttonHADBack);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String imagePath = intent.getStringExtra("image_path");

        tvTitle.setText(title);

        if (imagePath != null) {
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgArticle.setImageBitmap(bitmap);
            }
        }

        btnBack.setOnClickListener(view ->
                startActivity(new Intent(this, HealthArticlesActivity.class)));
    }
}
