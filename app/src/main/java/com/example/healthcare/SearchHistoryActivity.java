package com.example.healthcare;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchHistoryActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listViewHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);

        listViewHistory = findViewById(R.id.listViewSearchHistory);
        db = new DatabaseHelper(this);

        loadSearchHistory();
    }

    private void loadSearchHistory() {
        Cursor cursor = db.getSearchHistory();
        ArrayList<String> historyList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String term = cursor.getString(cursor.getColumnIndexOrThrow("search_term"));
                historyList.add(term);
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "No search history found", Toast.LENGTH_SHORT).show();
        }

        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                historyList
        );

        listViewHistory.setAdapter(adapter);
    }
}
