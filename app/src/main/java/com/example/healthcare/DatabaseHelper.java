package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "healthcare_db";
    private static final int DATABASE_VERSION = 4; // 🔁 BUMPED to ensure articles table creation

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_MEDICINE = "medicine";
    public static final String TABLE_SEARCH_HISTORY = "search_history";
    public static final String TABLE_ARTICLES = "articles";

    // USER columns
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    // MEDICINE columns
    public static final String MED_ID = "id";
    public static final String MED_NAME = "name";
    public static final String MED_DETAILS = "details";
    public static final String MED_PRICE = "price";

    // ARTICLE columns
    public static final String COLUMN_ARTICLE_ID = "id";
    public static final String COLUMN_ARTICLE_TITLE = "title";
    public static final String COLUMN_ARTICLE_IMAGE_PATH = "image_path";

    // CREATE TABLES
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_PASSWORD + " TEXT);";

    private static final String CREATE_TABLE_MEDICINE = "CREATE TABLE " + TABLE_MEDICINE + " (" +
            MED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MED_NAME + " TEXT, " +
            MED_DETAILS + " TEXT, " +
            MED_PRICE + " TEXT);";

    private static final String CREATE_TABLE_SEARCH_HISTORY = "CREATE TABLE " + TABLE_SEARCH_HISTORY + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "search_term TEXT);";

    private static final String CREATE_TABLE_ARTICLES = "CREATE TABLE " + TABLE_ARTICLES + " (" +
            COLUMN_ARTICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ARTICLE_TITLE + " TEXT, " +
            COLUMN_ARTICLE_IMAGE_PATH + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_MEDICINE);
        db.execSQL(CREATE_TABLE_SEARCH_HISTORY);
        db.execSQL(CREATE_TABLE_ARTICLES); // ✅ Now created on DB creation
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        onCreate(db);
    }

    // ✅ USER CRUD
    public void addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public boolean checkUserLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null,
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        boolean exists = (cursor != null && cursor.moveToFirst());
        if (cursor != null) cursor.close();
        return exists;
    }

    // ✅ MEDICINE CRUD
    public void insertMedicine(String name, String details, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MED_NAME, name);
        values.put(MED_DETAILS, details);
        values.put(MED_PRICE, price);
        db.insert(TABLE_MEDICINE, null, values);
        db.close();
    }

    public Cursor getAllMedicines() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_MEDICINE, null);
    }

    public void deleteMedicineById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEDICINE, MED_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ✅ SEARCH HISTORY
    public void saveSearchQuery(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("search_term", query);
        db.insert(TABLE_SEARCH_HISTORY, null, values);
        db.close();
    }

    public Cursor getSearchHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SEARCH_HISTORY + " ORDER BY id DESC", null);
    }

    // ✅ ARTICLE INSERT
    public void insertArticle(String title, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ARTICLE_TITLE, title);
        values.put(COLUMN_ARTICLE_IMAGE_PATH, imagePath);
        db.insert(TABLE_ARTICLES, null, values);
        db.close();
    }

    // ✅ GET ALL ARTICLES
    public ArrayList<HashMap<String, String>> getAllArticles() {
        ArrayList<HashMap<String, String>> articleList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ARTICLES, new String[]{
                COLUMN_ARTICLE_ID,
                COLUMN_ARTICLE_TITLE,
                COLUMN_ARTICLE_IMAGE_PATH
        }, null, null, null, null, COLUMN_ARTICLE_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> article = new HashMap<>();
                article.put("id", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_ID)));
                article.put("title", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_TITLE)));
                article.put("image_path", cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTICLE_IMAGE_PATH)));
                articleList.add(article);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return articleList;
    }
}
