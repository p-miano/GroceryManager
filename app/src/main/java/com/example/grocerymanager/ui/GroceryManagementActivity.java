package com.example.grocerymanager.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerymanager.R;
import com.example.grocerymanager.data.GroceryItem;
import com.example.grocerymanager.viewmodel.GroceryViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class GroceryManagementActivity extends AppCompatActivity {

    private GroceryViewModel groceryViewModel;
    private GroceryAdapter groceryAdapter;
    private EditText editTextGroceryName;
    private Spinner spinnerCategory;
    private Button buttonSave;
    private Button buttonDelete;
    private GroceryItem selectedGroceryItem = null; // To hold the selected grocery item for update/delete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_management);

        editTextGroceryName = findViewById(R.id.editTextGroceryName);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        buttonSave = findViewById(R.id.buttonSave);
        buttonDelete = findViewById(R.id.buttonDelete);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewGroceries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryAdapter = new GroceryAdapter(new ArrayList<>(), groceryItem -> {
            editTextGroceryName.setText(groceryItem.getName());

            // Set the spinner to the correct category based on the selected item
            int categoryPosition = ((ArrayAdapter<String>) spinnerCategory.getAdapter())
                    .getPosition(groceryItem.getCategory());
            spinnerCategory.setSelection(categoryPosition);

            selectedGroceryItem = groceryItem; // Set the selected grocery item
        });

        recyclerView.setAdapter(groceryAdapter);

        groceryViewModel = new ViewModelProvider(this).get(GroceryViewModel.class);

        // Get the current user's ID from FirebaseAuth
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Load groceries for the current user
        groceryViewModel.loadGroceries(userId);
        groceryViewModel.getGroceries().observe(this, groceryAdapter::updateGroceries);

        // Initialize Spinner data
        List<String> categories = new ArrayList<>();
        categories.add("Fruits");
        categories.add("Dairy");
        categories.add("Vegetables");
        categories.add("Meat");
        categories.add("Beverages");

        // Create an ArrayAdapter using the category list
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        spinnerCategory.setAdapter(spinnerAdapter);

        // Save button click listener
        buttonSave.setOnClickListener(view -> saveGrocery(userId));

        // Delete button click listener
        buttonDelete.setOnClickListener(view -> deleteGrocery(userId));
    }

    // Method to save or update grocery item
    private void saveGrocery(String userId) {
        String name = editTextGroceryName.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a grocery name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedGroceryItem == null) {
            // Add new grocery item
            boolean result = groceryViewModel.addGrocery(name, category, userId);
            if (result) {
                Toast.makeText(this, "Grocery item added", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "Failed to add grocery item", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Update existing grocery item
            boolean result = groceryViewModel.updateGrocery(selectedGroceryItem.getId(), name, category, userId);
            if (result) {
                Toast.makeText(this, "Grocery item updated", Toast.LENGTH_SHORT).show();
                clearFields();
                selectedGroceryItem = null; // Reset the selected item
            } else {
                Toast.makeText(this, "Failed to update grocery item", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to delete grocery item
    private void deleteGrocery(String userId) {
        if (selectedGroceryItem == null) {
            Toast.makeText(this, "No grocery item selected", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = groceryViewModel.deleteGrocery(selectedGroceryItem.getId(), userId);
        if (result) {
            Toast.makeText(this, "Grocery item deleted", Toast.LENGTH_SHORT).show();
            clearFields();
            selectedGroceryItem = null; // Reset the selected item
        } else {
            Toast.makeText(this, "Failed to delete grocery item", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to clear input fields
    private void clearFields() {
        editTextGroceryName.setText("");
        spinnerCategory.setSelection(0);
    }
}
