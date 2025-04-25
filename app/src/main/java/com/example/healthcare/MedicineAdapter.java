package com.example.healthcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MedicineAdapter extends BaseAdapter {
    private Context context;
    private List<MedicineModel> originalList;
    private List<MedicineModel> filteredList;
    private LayoutInflater inflater;

    public MedicineAdapter(Context context, List<MedicineModel> medicineList) {
        this.context = context;
        this.originalList = new ArrayList<>(medicineList);
        this.filteredList = medicineList;
        this.inflater = LayoutInflater.from(context);
    }

    public void filter(String text) {
        text = text.toLowerCase(Locale.getDefault());
        filteredList.clear();
        if (text.length() == 0) {
            filteredList.addAll(originalList);
        } else {
            for (MedicineModel model : originalList) {
                if (model.getName().toLowerCase(Locale.getDefault()).contains(text)) {
                    filteredList.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return filteredList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.medicine_list_item, parent, false);

        TextView medName = view.findViewById(R.id.medName);
        TextView medDetails = view.findViewById(R.id.medDetails);
        TextView medPrice = view.findViewById(R.id.medPrice);
        ImageView deleteIcon = view.findViewById(R.id.deleteIcon);

        MedicineModel model = filteredList.get(position);
        medName.setText(model.getName());
        medDetails.setText(model.getDetails());
        medPrice.setText("Rs. " + model.getPrice());

        deleteIcon.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(context);
            db.deleteMedicineById(model.getId());
            filteredList.remove(position);
            originalList.remove(model);
            notifyDataSetChanged();
        });

        return view;
    }
}
