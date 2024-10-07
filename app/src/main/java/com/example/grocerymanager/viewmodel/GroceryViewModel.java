package com.example.grocerymanager.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.grocerymanager.data.GroceryItem;
import com.example.grocerymanager.repository.GroceryRepository;

import java.util.List;

public class GroceryViewModel extends AndroidViewModel {

    private GroceryRepository repository;
    private MutableLiveData<List<GroceryItem>> groceryListLiveData;

    public GroceryViewModel(Application application) {
        super(application);
        repository = new GroceryRepository(application);
        groceryListLiveData = new MutableLiveData<>();
    }

    // Method to load groceries for the current user
    public void loadGroceries(String userId) {
        List<GroceryItem> groceryList = repository.getAllGroceries(userId);
        groceryListLiveData.setValue(groceryList);
    }

    // Expose the grocery list LiveData
    public LiveData<List<GroceryItem>> getGroceries() {
        return groceryListLiveData;
    }

    // Method to add a grocery item
    public boolean addGrocery(String name, String category, String userId) {
        boolean result = repository.addGrocery(name, category, userId);
        if (result) {
            loadGroceries(userId); // Reload groceries after addition
        }
        return result;
    }

    // Method to update a grocery item
    public boolean updateGrocery(int id, String name, String category, String userId) {
        boolean result = repository.updateGrocery(id, name, category, userId);
        if (result) {
            loadGroceries(userId); // Reload groceries after update
        }
        return result;
    }

    // Method to delete a grocery item
    public boolean deleteGrocery(int id, String userId) {
        boolean result = repository.deleteGrocery(id, userId);
        if (result) {
            loadGroceries(userId); // Reload groceries after deletion
        }
        return result;
    }
}
