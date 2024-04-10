package com.example.myapplication.views;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;
import java.util.ArrayList;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private List<String> shoppingListEntries;
    private SparseBooleanArray itemStateArray = new SparseBooleanArray(); // to store state of each checkbox

    public ShoppingListAdapter(List<String> shoppingListEntries) {
        this.shoppingListEntries = shoppingListEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopping_list_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String shoppingListEntry = shoppingListEntries.get(position);
        holder.bind(shoppingListEntry, position);
    }

    @Override
    public int getItemCount() {
        return shoppingListEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkbox;
        private TextView shoppingListItemEntryTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.shoppingListItemCheckbox);
            shoppingListItemEntryTextView = itemView.findViewById(R.id.shoppingListItemEntryTextView);

            // Set OnClickListener for checkbox
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (!itemStateArray.get(adapterPosition, false)) {
                        checkbox.setChecked(true);
                        itemStateArray.put(adapterPosition, true);
                    } else {
                        checkbox.setChecked(false);
                        itemStateArray.put(adapterPosition, false);
                    }
                }
            });
        }

        public void bind(String ingredientEntry, int position) {
            shoppingListItemEntryTextView.setText(ingredientEntry);
            checkbox.setChecked(itemStateArray.get(position, false)); // Set checkbox state based on itemStateArray
        }
    }
    public List<String> getCheckedItems() {
        List<String> checkedItems = new ArrayList<>();
        for (int i = 0; i < shoppingListEntries.size(); i++) {
            if (itemStateArray.get(i, false)) {
                checkedItems.add(shoppingListEntries.get(i));
            }
        }
        return checkedItems;
    }
}
