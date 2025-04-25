package com.example.healthcare;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class Buy_Medicine extends AppCompatActivity {

    DatabaseHelper db;
    MedicineAdapter adapter;
    ArrayList<MedicineModel> medicineList;

    Button buttonBack;
    ListView listView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        db = new DatabaseHelper(this);
        medicineList = new ArrayList<>();

        // UI Elements
        buttonBack = findViewById(R.id.buttonHABack);
        listView = findViewById(R.id.listViewMedicine);
        searchView = findViewById(R.id.searchView);

        // Load medicines
        loadMedicines();

        // Back button
        buttonBack.setOnClickListener(v -> {
            startActivity(new Intent(Buy_Medicine.this, HomeActivity.class));
        });

        // Search filter
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

        // Delete on long click
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            MedicineModel model = medicineList.get(i);
            db.deleteMedicineById(model.getId());
            Toast.makeText(Buy_Medicine.this, "Deleted: " + model.getName(), Toast.LENGTH_SHORT).show();
            loadMedicines();
            return true;
        });
    }

    private void loadMedicines() {
        medicineList.clear();
        Cursor cursor = db.getAllMedicines();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String details = cursor.getString(cursor.getColumnIndexOrThrow("details"));
                String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));

                medicineList.add(new MedicineModel(id, name, details, price));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new MedicineAdapter(Buy_Medicine.this, medicineList);
        listView.setAdapter(adapter);
    }
}
