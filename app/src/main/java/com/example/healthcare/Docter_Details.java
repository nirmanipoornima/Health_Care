package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;

public class Docter_Details extends AppCompatActivity {

    TextView titleText;
    Button backButton;
    ListView listView;
    SearchView searchView;

    String[][] doctorData;
    ArrayList<HashMap<String, String>> doctorList;
    SimpleAdapter adapter;
    DatabaseHelper db;

    String[][] doctordetails1 = {
            {"Doctor Name : Kamal Perera", "Hospital address : Colombo", "Exp : 5Years", "Mobile No: 0771234567", "600"},
            {"Doctor Name : Nimal Perera", "Hospital address : Gampha", "Exp : 3Years", "Mobile No: 0771535567", "900"},
            {"Doctor Name : Udesh Wijewardhana", "Hospital address : Colombo", "Exp : 5Years", "Mobile No: 0774564567", "300"},
            {"Doctor Name : Amantha Gimhan", "Hospital address : Kandy", "Exp : 4Years", "Mobile No: 0771896567", "500"},
            {"Doctor Name : Chamika Dilshan", "Hospital address : Colombo", "Exp : 2Years", "Mobile No: 0774775567", "800"},
    };
    String[][] doctordetails2 = {
            {"Doctor Name : Pramodika Dilhara", "Hospital address : Baththaramulla", "Exp : 5Years", "Mobile No: 0771234567", "600"},
            {"Doctor Name : Kavindra Reshan", "Hospital address : Gampha", "Exp : 3Years", "Mobile No: 0771535567", "900"},
            {"Doctor Name : Malsha Prabhasara", "Hospital address : Colombo", "Exp : 5Years", "Mobile No: 0774564567", "300"},
            {"Doctor Name : Saduni Lanka", "Hospital address : Kandy", "Exp : 4Years", "Mobile No: 0771896567", "500"},
            {"Doctor Name : Charith Athapaththu", "Hospital address : Colombo", "Exp : 2Years", "Mobile No: 0774775567", "800"},
    };
    String[][] doctordetails3 = {
            {"Doctor Name : Savindya Chamikara", "Hospital address : Colombo", "Exp : 5Years", "Mobile No: 0771234567", "600"},
            {"Doctor Name : Nimal Perera", "Hospital address : Gampha", "Exp : 3Years", "Mobile No: 0771535567", "900"},
            {"Doctor Name : Udesh Imalka", "Hospital address : Colombo", "Exp : 5Years", "Mobile No: 0774564567", "300"},
            {"Doctor Name : Sanduni Nisansala", "Hospital address : Kandy", "Exp : 4Years", "Mobile No: 0771896567", "500"},
            {"Doctor Name : Chamikara Herath", "Hospital address : Colombo", "Exp : 2Years", "Mobile No: 0774775567", "800"},
    };
    String[][] doctordetails4 = {
            {"Doctor Name : Kamal Perera", "Hospital address : Kurunegala", "Exp : 5Years", "Mobile No: 0771234567", "600"},
            {"Doctor Name : Nimal Perera", "Hospital address : Gampha", "Exp : 3Years", "Mobile No: 0771535567", "900"},
            {"Doctor Name : Shehan Bhanuka", "Hospital address : Negambo", "Exp : 5Years", "Mobile No: 0774564567", "300"},
            {"Doctor Name : Amantha Gimhan", "Hospital address : Kandy", "Exp : 4Years", "Mobile No: 0771896567", "500"},
            {"Doctor Name : Amal Perera", "Hospital address : Colombo", "Exp : 2Years", "Mobile No: 0774775567", "800"},
    };
    String[][] doctordetails5 = {
            {"Doctor Name : Kamal Perera", "Hospital address : Colombo", "Exp : 5Years", "Mobile No: 0771234567", "600"},
            {"Doctor Name : Sandun Perera", "Hospital address : Gampha", "Exp : 3Years", "Mobile No: 0771535567", "900"},
            {"Doctor Name : Udesh Wijewardhana", "Hospital address : Colombo", "Exp : 5Years", "Mobile No: 0774564567", "300"},
            {"Doctor Name : Amantha Gimhan", "Hospital address : Kandy", "Exp : 4Years", "Mobile No: 0771896567", "500"},
            {"Doctor Name : Yasiru Vishan", "Hospital address : Colombo", "Exp : 2Years", "Mobile No: 0774775567", "800"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_details);

        db = new DatabaseHelper(this);
        titleText = findViewById(R.id.listViewHATitle);
        backButton = findViewById(R.id.buttonHABack);
        listView = findViewById(R.id.ScrollViewHA);
        searchView = findViewById(R.id.doctorSearchView);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        titleText.setText(title);

        if (title.equals("Family Physician")) doctorData = doctordetails1;
        else if (title.equals("Dietician")) doctorData = doctordetails2;
        else if (title.equals("Dentist")) doctorData = doctordetails3;
        else if (title.equals("Surgeon")) doctorData = doctordetails4;
        else doctorData = doctordetails5;

        loadDoctorList(doctorData);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                db.saveSearchQuery(query); // Save search query to DB
                filterList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        backButton.setOnClickListener(view -> {
            startActivity(new Intent(Docter_Details.this, FindDoctorActivity2.class));
        });
    }

    private void loadDoctorList(String[][] data) {
        doctorList = new ArrayList<>();
        for (String[] doctor : data) {
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", doctor[0]);
            item.put("line2", doctor[1]);
            item.put("line3", doctor[2]);
            item.put("line4", doctor[3]);
            item.put("line5", "Cons Fees: " + doctor[4] + "/-");
            doctorList.add(item);
        }
        adapter = new SimpleAdapter(this, doctorList, R.layout.multilines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.linea, R.id.lineb, R.id.linec, R.id.lined, R.id.linee});
        listView.setAdapter(adapter);
    }

    private void filterList(String query) {
        ArrayList<HashMap<String, String>> filteredList = new ArrayList<>();
        for (String[] doctor : doctorData) {
            if (doctor[0].toLowerCase().contains(query.toLowerCase()) ||
                    doctor[1].toLowerCase().contains(query.toLowerCase())) {
                HashMap<String, String> item = new HashMap<>();
                item.put("line1", doctor[0]);
                item.put("line2", doctor[1]);
                item.put("line3", doctor[2]);
                item.put("line4", doctor[3]);
                item.put("line5", "Cons Fees: " + doctor[4] + "/-");
                filteredList.add(item);
            }
        }
        adapter = new SimpleAdapter(this, filteredList, R.layout.multilines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.linea, R.id.lineb, R.id.linec, R.id.lined, R.id.linee});
        listView.setAdapter(adapter);
    }
}
