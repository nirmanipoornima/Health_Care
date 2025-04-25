package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthArticlesActivity extends AppCompatActivity {

    ListView lst;
    Button btnBack;
    ArrayList<HashMap<String, String>> articleList;
    SimpleAdapter sa;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_helth_article);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lst = findViewById(R.id.ScrollViewHA);
        btnBack = findViewById(R.id.buttonHABack);
        dbHelper = new DatabaseHelper(this);

        btnBack.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));

        // 🔽 Load articles from database
        articleList = dbHelper.getAllArticles();
        ArrayList<HashMap<String, String>> displayList = new ArrayList<>();

        for (HashMap<String, String> article : articleList) {
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", article.get("title"));
            item.put("line5", "Click More Details");
            displayList.add(item);
        }

        sa = new SimpleAdapter(this, displayList, R.layout.multilines,
                new String[]{"line1", "line5"},
                new int[]{R.id.linea, R.id.linee});
        lst.setAdapter(sa);

        lst.setOnItemClickListener((adapterView, view, i, l) -> {
            HashMap<String, String> selected = articleList.get(i);
            Intent intent = new Intent(this, HealthArticleDetailsActivity.class);
            intent.putExtra("title", selected.get("title"));
            intent.putExtra("image_path", selected.get("image_path"));
            startActivity(intent);
        });
    }
}
