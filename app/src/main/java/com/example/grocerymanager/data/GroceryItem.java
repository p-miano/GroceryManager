package com.example.grocerymanager.data;

public class GroceryItem {
    private int id;
    private String name;
    private String category;

    // No-argument constructor
    public GroceryItem() {
    }

    // Constructor with parameters
    public GroceryItem(int id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    // Setters (if needed)
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
