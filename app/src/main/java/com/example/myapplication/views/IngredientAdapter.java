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

    public IngredientAdapter(List<String> ingredientEntries) {
        this.ingredientEntries = ingredientEntries;
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
