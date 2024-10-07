package com.example.grocerymanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.grocerymanager.data.GroceryItem;

public class GroceryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "groceryManager.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_GROCERIES = "groceries";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_USER_ID = "user_id";

    public GroceryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERIES_TABLE = "CREATE TABLE " + TABLE_GROCERIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, "
                + COLUMN_USER_ID + " TEXT" +
                ")";
        db.execSQL(CREATE_GROCERIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERIES);
        onCreate(db);
    }

    public boolean addGrocery(String name, String category, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_USER_ID, userId);

        long result = db.insert(TABLE_GROCERIES, null, values);
        db.close();
        return result != -1;
    }

    public boolean updateGrocery(int id, String name, String category, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_USER_ID, userId);

        int result = db.update(TABLE_GROCERIES, values, COLUMN_ID + " = ? AND " + COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(id), userId});
        db.close();
        return result > 0;
    }

    public boolean deleteGrocery(int id, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_GROCERIES, COLUMN_ID + " = ? AND " + COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(id), userId});
        db.close();
        return result > 0;
    }

    // Retrieve all groceries for a specific user
    public List<GroceryItem> getAllGroceries(String userId) {
        List<GroceryItem> groceryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_GROCERIES + " WHERE " + COLUMN_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{userId});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));

                GroceryItem groceryItem = new GroceryItem(id, name, category);
                groceryList.add(groceryItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return groceryList;
    }
}
