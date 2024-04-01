package com.example.myapplication.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;
import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<String> ingredientEntries;
    private List<String> filteredIngredientEntries;

    public IngredientAdapter(List<String> ingredientEntries) {
        this.ingredientEntries = ingredientEntries;
        this.filteredIngredientEntries = new ArrayList<>(ingredientEntries);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ingredientEntry = ingredientEntries.get(position);
        holder.bind(ingredientEntry);
    }

    @Override
    public int getItemCount() {
        return ingredientEntries.size();
    }

    public void setFilter(List<String> filteredList) {
        filteredIngredientEntries.clear(); // Clear current filtered list
        filteredIngredientEntries.addAll(filteredList); // Add all items from the new filtered list
        notifyDataSetChanged(); // Notify adapter about data change
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ingredientEntryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientEntryTextView = itemView.findViewById(R.id.ingredientEntryTextView);
        }

        public void bind(String ingredientEntry) {
            ingredientEntryTextView.setText(ingredientEntry);
        }
    }
}
