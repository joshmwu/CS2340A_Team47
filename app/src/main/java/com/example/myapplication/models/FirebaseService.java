package com.example.myapplication.models;

import static java.security.AccessController.getContext;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;

public class FirebaseService {
    private static FirebaseService instance;
    private FirebaseDatabase database;
    private LoginData firebaseLoginData = LoginData.getInstance();
    private UserData firebaseUserData = UserData.getInstance();
    private PantryData firebasePantryData = PantryData.getInstance();
    private ShoppingListData firebaseShoppingListData = ShoppingListData.getInstance();

    private FirebaseService() {
        database = FirebaseDatabase.getInstance();
    }

    public static FirebaseService getInstance() {
        if (instance == null) {
            instance = new FirebaseService();
        }
        return instance;
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return database;
    }
    public DatabaseReference getDBReference(String path) {
        return this.getFirebaseDatabase().getReference(path);
    }

    public void addUser(String username, String password) {
        DatabaseReference userRef = this.getDBReference("Users");
        userRef.child(username).child("username").setValue(username);
        userRef.child(username).child("password").setValue(password);
    }

    // NOTE: "set" in this context means grabbing data from database and assigning to local vars
    public void setAllData() {
        setPassword();
        setHeight();
        setWeight();
        setAge();
        setGender();
        setCalorieGoal();
        setPantry();
        setShoppingList();
    }

    // LoginData: username, password
    public void setUsername(String username) { // this one must be the first in order to retrieve all other data
        firebaseLoginData.setUsername(username);
    }

    public void setPassword() {
        DatabaseReference userRef = this.getDBReference("Users");
        userRef.child(firebaseLoginData.getUsername()).child("password")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error finding password", task.getException());
                        } else {
                            // Retrieve password and set it to firebaseLoginData's password
                            String password = String.valueOf(task.getResult().getValue());
                            firebaseLoginData.setPassword(password);
                        }
                    }
                });
    }

    public void checkUserValidity(String username, String password) {
        DatabaseReference userRef = this.getDBReference("Users");
        userRef.child(username).child("username")
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error finding username", task.getException());
                        } else {
                            DataSnapshot usernameSnapshot = task.getResult();
                            if (usernameSnapshot.exists() && String.valueOf(usernameSnapshot.getValue()).equals(username)) {
                                userRef.child(username).child("password")
                                        .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                if (!task.isSuccessful()) {
                                                    Log.e("firebase", "Error finding password", task.getException());
                                                } else {
                                                    DataSnapshot passwordSnapshot = task.getResult();
                                                    if (passwordSnapshot.exists() && String.valueOf(passwordSnapshot.getValue()).equals(password)) {
                                                        Log.e("verified", "verified");
                                                        firebaseLoginData.setLoggedIn(true);
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }


    // UserData: height, weight, age, gender, calorieGoal

    public void setHeight() {
        DatabaseReference userRef = this.getDBReference("Users");
        userRef.child(firebaseLoginData.getUsername()).child("height")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Object heightObj = task.getResult().getValue();

                        // Check if the height value exists and is an integer
                        if (heightObj != null && heightObj instanceof Long) {
                            // Convert the height value to an integer
                            int height = ((Long) heightObj).intValue();
                            firebaseUserData.setHeight(height);
                        }
                    }
                });
    }

    public void setWeight() {
        DatabaseReference userRef = this.getDBReference("Users");
        userRef.child(firebaseLoginData.getUsername()).child("weight")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Object weightObj = task.getResult().getValue();

                        // Check if the height value exists and is an integer
                        if (weightObj != null && weightObj instanceof Long) {
                            // Convert the height value to an integer
                            int weight = ((Long) weightObj).intValue();
                            firebaseUserData.setWeight(weight);
                        }
                    }
                });
    }

    public void setAge() {
        DatabaseReference userRef = this.getDBReference("Users");
        userRef.child(firebaseLoginData.getUsername()).child("age")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Object ageObj = task.getResult().getValue();

                        // Check if the height value exists and is an integer
                        if (ageObj != null && ageObj instanceof Long) {
                            // Convert the height value to an integer
                            int age = ((Long) ageObj).intValue();
                            firebaseUserData.setAge(age);
                        }
                    }
                });
    }

    public void setGender() {
        DatabaseReference userRef = this.getDBReference("Users");
        userRef.child(firebaseLoginData.getUsername()).child("gender")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error finding gender", task.getException());
                        } else {
                            // Retrieve password and set it to firebaseLoginData's password
                            String gender = String.valueOf(task.getResult().getValue());
                            firebaseUserData.setGender(gender);
                        }
                    }
                });
    }

    public void setCalorieGoal() {
        DatabaseReference userRef = this.getDBReference("Users");
        userRef.child(firebaseLoginData.getUsername()).child("calorieGoal")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        Object ageObj = task.getResult().getValue();
                        if (ageObj != null && ageObj instanceof Long) {
                            int calorieGoal = ((Long) ageObj).intValue();
                            firebaseUserData.setCalorieGoal(calorieGoal);
                        }
                    }
                });
    }
    public void updatePersonalData(String username, int height, int weight, int age, String gender, int calorieGoal) {
        DatabaseReference userRef = this.getFirebaseDatabase().getReference("Users");
        userRef.child(username).child("height").setValue(height);
        userRef.child(username).child("weight").setValue(weight);
        userRef.child(username).child("age").setValue(age);
        userRef.child(username).child("gender").setValue(gender);
        userRef.child(username).child("calorieGoal").setValue(calorieGoal);
    }

    // PantryData: pantry
    public void setPantry() {
        ArrayList<Ingredient> listOfIngredients = new ArrayList<>();
        DatabaseReference userRef = this.getFirebaseDatabase().getReference("Users");
        DatabaseReference pantryRef = userRef.child(firebaseLoginData.getUsername()).child("Pantry");
        pantryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ingredientSnapshot : dataSnapshot.getChildren()) {
                    String ingredientName = ingredientSnapshot.getKey();
                    int quantity = 0; // Default value
                    int calories = 0; // Default value

                    // Check if quantity and calories exist for the ingredient
                    if (ingredientSnapshot.hasChild("quantity")) {
                        quantity = ingredientSnapshot.child("quantity").getValue(Integer.class);
                    }
                    if (ingredientSnapshot.hasChild("calories")) {
                        calories = ingredientSnapshot.child("calories").getValue(Integer.class);
                    }

                    // Create Ingredient object
                    Ingredient ingredient = new Ingredient(ingredientName, calories, quantity);
                    // Do something with the Ingredient object, such as adding it to a list
                    listOfIngredients.add(ingredient);
                }
                firebasePantryData.setIngredientList(listOfIngredients);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors
                System.err.println("Error reading pantry: " + databaseError.getMessage());
            }
        });
    }

    public void addIngredient(String name, int quantity, int calories) {
        DatabaseReference userRef = this.getFirebaseDatabase().getReference("Users");
        DatabaseReference pantryRef = userRef.child(firebaseLoginData.getUsername()).child("Pantry");
        DatabaseReference ingredientRef = pantryRef.child(name);
        ingredientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //addToPantry(ingredientRef, pantryRef, dataSnapshot);
                if (dataSnapshot.exists()) {
                    ingredientRef.child("quantity").get().addOnCompleteListener(task -> {
                        Object quantityObj = task.getResult().getValue();
                        // quantity value exists and is an integer
                        if (quantityObj instanceof Long) {
                            // set the quantity value to new value
                            int newQuantity = ((Long) quantityObj).intValue() + quantity;
                            pantryRef.child(name).child("quantity").setValue(newQuantity)
                                    .addOnCompleteListener(task1 -> {
                                        // After quantity update, refresh the pantry
                                        setPantry();
                                    });
                        }
                    });
                    ingredientRef.child("calories").get().addOnCompleteListener(task -> {
                        Object caloriesObj = task.getResult().getValue();

                        // calories value exists and is an integer
                        if (caloriesObj instanceof Long) {
                            // set the calories value to new value
                            int newCalories = calories;
                            pantryRef.child(name).child("calories").setValue(newCalories)
                                    .addOnCompleteListener(task1 -> {
                                        // After quantity update, refresh the pantry
                                        setPantry();
                                    });
                        }
                    });
                } else {
                    pantryRef.child(name).child("quantity").setValue(quantity);
                    pantryRef.child(name).child("calories").setValue(calories)
                            .addOnCompleteListener(task -> {
                                // After adding new ingredient, update the entire pantry
                                setPantry();
                            });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void removeIngredient(String name, int quantity) {
        DatabaseReference userRef = this.getFirebaseDatabase().getReference("Users");
        DatabaseReference pantryRef = userRef.child(firebaseLoginData.getUsername()).child("Pantry");
        DatabaseReference ingredientRef = pantryRef.child(name);

        // Check if the ingredient exists in the pantry
        ingredientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ingredientRef.child("quantity").get().addOnCompleteListener(task -> {
                        Object quantityObj = task.getResult().getValue();

                        if (quantityObj instanceof Long) {
                            int currentQuantity = ((Long) quantityObj).intValue();
                            int newQuantity = currentQuantity - quantity;
                            if (newQuantity <= 0) {
                                // If the new quantity is less than or equal to 0, remove the ingredient from the pantry
                                ingredientRef.removeValue().addOnCompleteListener(removeTask -> {
                                    if (removeTask.isSuccessful()) {
                                        // After removing the ingredient, refresh the pantry
                                        setPantry();
                                    } else {
                                        // Handle the case where removing the ingredient failed
                                    }
                                });
                            } else {
                                // If the new quantity is greater than 0, update the quantity in the pantry
                                pantryRef.child(name).child("quantity").setValue(newQuantity)
                                        .addOnCompleteListener(updateTask -> {
                                            if (updateTask.isSuccessful()) {
                                                // After updating the quantity, refresh the pantry
                                                setPantry();
                                            } else {
                                                // Handle the case where updating the quantity failed
                                            }
                                        });
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle onCancelled if needed
            }
        });
    }
    // Recipe Database - does not need to be stored locally
    public void addNewRecipe(String name, HashMap<String, Integer> ingredientMap) {
        DatabaseReference userRef = this.getDBReference("Recipes");
        for (Map.Entry<String, Integer> entry : ingredientMap.entrySet()) {
            String ingredient = entry.getKey();
            Integer quantity = entry.getValue();
            userRef.child(name).child(ingredient).setValue(quantity);
        }
    }

    // ShoppingList Database - should be stored locally!

    public void setShoppingList() {
        ArrayList<Ingredient> listOfItems = new ArrayList<>();
        DatabaseReference userRef = this.getFirebaseDatabase().getReference("Users");
        DatabaseReference shoppingListRef = userRef.child(firebaseLoginData.getUsername()).child("ShoppingList");
        shoppingListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String itemName = itemSnapshot.getKey();
                    int quantity = 0; // Default value

                    // Check if quantity and calories exist for the ingredient
                    if (itemSnapshot.hasChild("quantity")) {
                        quantity = itemSnapshot.child("quantity").getValue(Integer.class);
                    }

                    // Create Ingredient object
                    Ingredient ingredient = new Ingredient(itemName, 0, quantity);
                    // Do something with the Ingredient object, such as adding it to a list
                    listOfItems.add(ingredient);
                }
                firebaseShoppingListData.setShoppingList(listOfItems);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors
                System.err.println("Error reading pantry: " + databaseError.getMessage());
            }
        });
    }

    public void addShoppingListItem(String name, int quantity) {
        DatabaseReference userRef = this.getFirebaseDatabase().getReference("Users");
        DatabaseReference shoppingListRef = userRef.child(firebaseLoginData.getUsername()).child("ShoppingList");
        DatabaseReference itemRef = shoppingListRef.child(name);
        itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    itemRef.child("quantity").get().addOnCompleteListener(task -> {
                        Object quantityObj = task.getResult().getValue();
                        // quantity value exists and is an integer
                        if (quantityObj instanceof Long) {
                            // set the quantity value to new value
                            int newQuantity = ((Long) quantityObj).intValue() + quantity;
                            shoppingListRef.child(name).child("quantity").setValue(newQuantity)
                                    .addOnCompleteListener(task1 -> {
                                        // After quantity update, refresh the pantry
                                        setShoppingList();
                                    });
                        }
                    });
                } else {
                    shoppingListRef.child(name).child("quantity").setValue(quantity)
                            .addOnCompleteListener(task -> {
                                // After adding new ingredient, update the entire pantry
                                setShoppingList();
                            });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    public void removeShoppingListItem(String name, int quantity) {
        DatabaseReference userRef = this.getFirebaseDatabase().getReference("Users");
        DatabaseReference shoppingListRef = userRef.child(firebaseLoginData.getUsername()).child("Pantry");
        DatabaseReference ingredientRef = shoppingListRef.child(name);

        // must implement this!
    }
}