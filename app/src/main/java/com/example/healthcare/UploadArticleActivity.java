package com.example.healthcare;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadArticleActivity extends AppCompatActivity {

    private static final int IMAGE_PICK_CODE = 1000;
    private ImageView imageViewPreview;
    private EditText editTextArticleTitle;
    private Uri selectedImageUri;
    private String savedImagePath = null;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload_article);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        imageViewPreview = findViewById(R.id.imageViewPreview);
        editTextArticleTitle = findViewById(R.id.editTextArticleTitle);
        Button buttonUploadImage = findViewById(R.id.buttonUploadImage);
        Button buttonSaveArticle = findViewById(R.id.buttonSaveArticle);

        buttonUploadImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, IMAGE_PICK_CODE);
        });

        buttonSaveArticle.setOnClickListener(v -> {
            String title = editTextArticleTitle.getText().toString().trim();

            if (title.isEmpty() || savedImagePath == null) {
                Toast.makeText(this, "Please enter title and select an image", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.insertArticle(title, savedImagePath);
                Toast.makeText(this, "Article saved successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imageViewPreview.setImageURI(selectedImageUri);
            savedImagePath = saveImageToInternalStorage(selectedImageUri);
        }
    }

    private String saveImageToInternalStorage(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            File directory = new File(getFilesDir(), "articles");
            if (!directory.exists()) directory.mkdirs();

            String filename = "article_" + System.currentTimeMillis() + ".png";
            File file = new File(directory, filename);
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
