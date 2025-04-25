package com.example.healthcare;

public class MedicineModel {
    private int id;
    private String name;
    private String details;
    private String price;

    public MedicineModel(int id, String name, String details, String price) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getPrice() {
        return price;
    }
}
