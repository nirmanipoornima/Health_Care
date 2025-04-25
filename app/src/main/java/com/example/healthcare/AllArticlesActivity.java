package com.example.healthcare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllArticlesActivity extends AppCompatActivity {

    ListView listViewArticles;
    DatabaseHelper dbHelper;
    List<HashMap<String, String>> articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_articles);

        listViewArticles = findViewById(R.id.listViewArticles);
        dbHelper = new DatabaseHelper(this);

        articleList = dbHelper.getAllArticles();
        ArticleAdapter adapter = new ArticleAdapter(this, articleList);
        listViewArticles.setAdapter(adapter);
    }
}
