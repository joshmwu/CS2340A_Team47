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

public class CookbookAdapter extends RecyclerView.Adapter<CookbookAdapter.ViewHolder> {

    private List<String> recipeEntries;
    private List<String> filteredRecipeEntries;

    public CookbookAdapter(List<String> ingredientEntries) {
        this.recipeEntries = ingredientEntries;
        this.filteredRecipeEntries = new ArrayList<>(ingredientEntries);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ingredientEntry = recipeEntries.get(position);
        // String ingredientEntry = ingredientEntries.get(position);
        // String ingredientEntry = filteredIngredientEntries.get(position);
        // holder.bind(ingredientEntry);
//        String ingredientEntry;
//        if (filteredIngredientEntries.isEmpty()) {
//            ingredientEntry = ingredientEntries.get(position);
//        } else {
//            ingredientEntry = filteredIngredientEntries.get(position);
//        }
        holder.bind(ingredientEntry);
    }

    @Override
    public int getItemCount() {
        return recipeEntries.size();
        // return ingredientEntries.size();
        // return filteredIngredientEntries.size();
    }

    public void setFilter(List<String> filteredList) {
        filteredRecipeEntries.clear(); // Clear current filtered list
        filteredRecipeEntries.addAll(filteredList); // Add all items from the new filtered list
        notifyDataSetChanged(); // Notify adapter about data change
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView recipeEntryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeEntryTextView = itemView.findViewById(R.id.recipeEntryTextView);
        }

        public void bind(String ingredientEntry) {
            recipeEntryTextView.setText(ingredientEntry);
        }
    }
}
