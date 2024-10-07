package com.example.grocerymanager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerymanager.R;
import com.example.grocerymanager.data.GroceryItem;

import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    private List<GroceryItem> groceryList;
    private OnItemClickListener onItemClickListener;

    public GroceryAdapter(List<GroceryItem> groceryList, OnItemClickListener onItemClickListener) {
        this.groceryList = groceryList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grocery, parent, false);
        return new GroceryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        GroceryItem groceryItem = groceryList.get(position);
        holder.bind(groceryItem);
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public void updateGroceries(List<GroceryItem> newGroceryList) {
        groceryList = newGroceryList;
        notifyDataSetChanged();
    }

    class GroceryViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewCategory;
        private View itemView;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
        }

        public void bind(GroceryItem groceryItem) {
            textViewName.setText(groceryItem.getName());
            textViewCategory.setText(groceryItem.getCategory());

            // Set the background color based on the category
            int colorResId;
            switch (groceryItem.getCategory().toLowerCase()) {
                case "fruits":
                    colorResId = R.color.fruits;
                    break;
                case "dairy":
                    colorResId = R.color.dairy;
                    break;
                case "vegetables":
                    colorResId = R.color.vegetables;
                    break;
                case "meat":
                    colorResId = R.color.meat;
                    break;
                case "beverages":
                    colorResId = R.color.beverages;
                    break;
                default:
                    colorResId = R.color.default_category;
                    break;
            }

            // Apply the background color
            itemView.setBackgroundColor(itemView.getContext().getResources().getColor(colorResId));

            // Set a click listener to select the item when clicked
            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(groceryItem);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(GroceryItem groceryItem);
    }
}
