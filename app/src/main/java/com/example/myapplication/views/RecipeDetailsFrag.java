package com.example.myapplication.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.models.Ingredient;
import com.example.myapplication.models.PantryData;
import com.example.myapplication.viewmodels.IngredientsViewModel;
import com.example.myapplication.viewmodels.InputMealViewModel;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;
import com.example.myapplication.viewmodels.ShoppingListViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.*;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class RecipeDetailsFrag extends Fragment {
    private TextView recipeDetailsTitle;
    private List<String> ingredientEntries;
    private RecyclerView recipeDetailsRecyclerView;
    private FirebaseService firebaseService;
    private IngredientAdapter adapter;
    private Button backButton;
    private Button cookButton;
    private Button addSListButton;
    private IngredientsViewModel ingredientsViewModel = IngredientsViewModel.getInstance();
    private InputMealViewModel inputMealViewModel = InputMealViewModel.getInstance();
    private ArrayList<PieEntry> pieEntries = new ArrayList<>();
    private PersonalInfoViewModel userInfoVM = PersonalInfoViewModel.getInstance();
    private ShoppingListViewModel shoppingListVM = ShoppingListViewModel.getInstance();
    private int calorieGoal = userInfoVM.getUserData().getCalorieGoal();
    private InputMealViewModel mealVM = InputMealViewModel.getInstance();
    private int mealCalories;
    private PieChart pieChart;
    private LoginScreenViewModel loginVM = LoginScreenViewModel.getInstance();
    private String mealName = mealVM.getMealName();
    private PantryData pantryData = PantryData.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        backButton = root.findViewById(R.id.backButtonRecipeDetails);
        cookButton = root.findViewById(R.id.cookRecipeButton);
        addSListButton = root.findViewById(R.id.addRecipeToShoppingListButton);
        recipeDetailsTitle = root.findViewById(R.id.recipeDetailsScreenTitle);
        ingredientEntries = new ArrayList<>();
        adapter = new IngredientAdapter(ingredientEntries);
        recipeDetailsRecyclerView = root.findViewById(R.id.recipeDetailsRecyclerView);
        recipeDetailsRecyclerView.setAdapter(adapter);
        recipeDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(mealCalories, "Day's Caloric Intake"));
        pieEntries.add(new PieEntry(calorieGoal-mealCalories, "Daily Goal"));

        Bundle bundle = getArguments();
        if (bundle != null) {
            String recipe = bundle.getString("key");
            if(recipe.substring(recipe.length()-1,recipe.length()).equals("*")){
                recipe = recipe.substring(0, recipe.length()-1);
            }
            recipeDetailsTitle.setText(recipe);
            firebaseService = FirebaseService.getInstance();
            DatabaseReference recipeRef = firebaseService.getFirebaseDatabase().getReference(
                    "Recipes").child(recipe);
            recipeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Check if the snapshot has children
                    if (dataSnapshot.hasChildren()) {
                        // Iterate over the children
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            // Get the key (child node name) and value
                            ingredientEntries.add(childSnapshot.getKey() + " - "
                                    + childSnapshot.getValue());
                            adapter.notifyDataSetChanged();

                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                    System.err.println("Listener was cancelled");
                }
            });


        }

        cookButton.setOnClickListener(v -> {
            //update visualizations, meal database, calorie count, & indredients
            // get subtracted from pantry.
            String recipe = "";
            if (bundle != null) {
                recipe = bundle.getString("key");
            }
            if (!recipe.contains("*")) {
                Log.d("ingredientEntries", ingredientEntries.toString());
                for (String a : ingredientEntries) {
                    String name = RecipeDetailsFrag.getItemName(a);
                    int quantity = RecipeDetailsFrag.getItemQuantity(a);
                    // query pantry for ingredient calories
                    mealCalories += (pantryData.getCaloriesFromName(name) * quantity);
                    ingredientsViewModel.removeIngredient(name ,quantity);
                }

                mealVM.setMealData(loginVM.getLoginData().getUsername(), recipe, mealCalories);
                pieEntries.clear();
                int totCals = mealVM.getTotalDayCalories();
                if (mealCalories < calorieGoal) {
                    pieEntries.add(new PieEntry(mealCalories, "Day's Caloric Intake"));
                    pieEntries.add(new PieEntry(calorieGoal - mealCalories, "Remaining Calories"));
                } else {
                    pieEntries.add(new PieEntry((totCals - mealCalories), "Excess Caloric Intake"));
                    pieEntries.add(new PieEntry(calorieGoal, "Day's Calorie Goal"));
                }
                mealCalories = 0;

                CircleVisual circleVisualFragment = (CircleVisual) getParentFragmentManager().findFragmentById(R.id.goToPieChart);
                if (circleVisualFragment != null) {
                    // Call the method and pass the required parameters
                    int calorieLeft = totCals - mealCalories;
                    ArrayList<PieEntry> updatedPieEntries = generateUpdatedPieEntries(calorieLeft);
                    circleVisualFragment.updatePieChart(updatedPieEntries);
                }
                Toast.makeText(getContext(),
                        "Meal Cooked!",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getContext(),
                        "Insufficient ingredients in pantry to cook this recipe.",
                        Toast.LENGTH_SHORT).show();
            }


        });
        
        //DONT DELETE WILL FINISH IMPLEMENTATION LATER
        /*addSListButton.setOnClickListener(v -> {
            String recipe = bundle.getString("key");
            if(recipe.substring(recipe.length()-1,recipe.length()).equals("*")){
                recipe = recipe.substring(0, recipe.length()-1);
            }
            firebaseService = FirebaseService.getInstance();
            DatabaseReference recipeRef = firebaseService.getFirebaseDatabase().getReference(
                    "Recipes").child(recipe);
            recipeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Check if the snapshot has children
                    if (dataSnapshot.hasChildren()) {
                        // Iterate over the children
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            // Get the key (child node name) and value
                            //ingredientEntries.add(childSnapshot.getKey() + " - "
                            //        + childSnapshot.getValue());
                            shoppingListVM.addShoppingListItem(childSnapshot.getKey(), (Integer) childSnapshot.getValue(), 100);
                            adapter.notifyDataSetChanged();
                        }
                        Toast.makeText(getContext(),
                                "Successfully added items to shopping list.",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors
                    System.err.println("Listener was cancelled");
                }
            });
        });*/

        backButton.setOnClickListener(v -> {
            replaceFragment(new GlobalCookbookScreenFrag());
        });

        return root;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }

    private static String getItemName(String item) {
        String name = "";
        for (int i = 0; i < item.length() && !item.substring(i, i+1).equals(" "); i++) {
            name = name + item.substring(i, i+1);
        }
        return name;
    }

    public static int getItemQuantity(String item) {
        String quantity = "";
        for (int i = item.length() - 1; i < item.length() && !item.substring(i, i+1).equals(" "); i--) {
            quantity = item.substring(i, i+1) + quantity;
        }
        return Integer.valueOf(quantity);
    }

    private ArrayList<PieEntry> generateUpdatedPieEntries(int calorieLeft) {
        // Generate pie chart entries with the new total calorie count
        ArrayList<PieEntry> updatedPieEntries = new ArrayList<>();
        // Add your logic to generate pie chart entries based on the new data
        updatedPieEntries.add(new PieEntry(calorieLeft, "Total Calories"));
        // Add other entries as needed
        return updatedPieEntries;
    }
}