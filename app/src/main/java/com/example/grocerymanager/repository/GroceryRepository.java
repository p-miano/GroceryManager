package com.example.grocerymanager.repository;

import android.content.Context;

import com.example.grocerymanager.data.GroceryDatabaseHelper;
import com.example.grocerymanager.data.GroceryItem;

import java.util.List;

public class GroceryRepository {

    private GroceryDatabaseHelper dbHelper;

    public GroceryRepository(Context context) {
        dbHelper = new GroceryDatabaseHelper(context);
    }

    // Method to add a grocery item with userId
    public boolean addGrocery(String name, String category, String userId) {
        return dbHelper.addGrocery(name, category, userId);
    }

    // Method to update a grocery item with userId
    public boolean updateGrocery(int id, String name, String category, String userId) {
        return dbHelper.updateGrocery(id, name, category, userId);
    }

    // Method to delete a grocery item with userId
    public boolean deleteGrocery(int id, String userId) {
        return dbHelper.deleteGrocery(id, userId);
    }

    // Method to get all groceries for a user
    public List<GroceryItem> getAllGroceries(String userId) {
        return dbHelper.getAllGroceries(userId);
    }
}
