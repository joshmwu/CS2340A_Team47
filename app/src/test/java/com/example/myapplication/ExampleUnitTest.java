package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import android.content.Context;
import android.renderscript.ScriptGroup;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.myapplication.models.Ingredient;
import com.example.myapplication.models.IngredientType;
import com.example.myapplication.models.LoginData;
import com.example.myapplication.models.MediumCalorieIngredientFactory;
import com.example.myapplication.models.MediumCalorieIngredientType;
import com.example.myapplication.models.PantryData;
import com.example.myapplication.models.ShoppingListData;
import com.example.myapplication.models.MealData;
import com.example.myapplication.models.Ingredient;
import com.example.myapplication.viewmodels.IngredientsViewModel;
import com.example.myapplication.viewmodels.InputMealViewModel;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;
import com.example.myapplication.viewmodels.ShoppingListViewModel;
import com.example.myapplication.views.IngredientAdapter;
import com.example.myapplication.views.LoginScreen;
import com.example.myapplication.views.RecipeDetailsFrag;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.cert.X509Certificate;
import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {

    private LoginScreenViewModel loginScreenViewModel;
    private InputMealViewModel inputMealViewModel;
    private Context mockContext;
    private DatabaseReference databaseReference;

    private MealData mealData;
    private Ingredient ingredient;

    public void setup() {
        // Mock the context
        FirebaseDatabase firebaseDatabase = mock(FirebaseDatabase.class);
        databaseReference = mock(DatabaseReference.class);
        when(firebaseDatabase.getReference()).thenReturn(databaseReference);
        // Assuming FirebaseApp needs to be initialized
        //FirebaseApp.initializeApp(mockContext);
        // Initialize your ViewModel
        //loginScreenViewModel = LoginScreenViewModel.getInstance();
    }
    @Test
    public void usernameWorks() {
        LoginScreenViewModel a = LoginScreenViewModel.getInstance();
        a.getLoginData().setPassword("josh");
        assertEquals("josh", a.getLoginData().getUsername());
    }

    @Test
    public void passwordWorks() {
        LoginScreenViewModel a = LoginScreenViewModel.getInstance();
        a.getLoginData().setPassword("josh");
        assertEquals("josh", a.getLoginData().getPassword().equals("josh"));
    }

    @Test
    public void resetCalories() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.getMealData().resetMealCalories();
        assertEquals(0, a.getTotalDayCalories() == 0);
    }

    @Test
    public void getDay() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.setDay(3);
        assertEquals(3, a.getDay() == 3);
    }

    @Test
    public void setMealName() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.getMealData().setMealData("Breakfast", 500);
        assertEquals("Breakfast", a.getMealData().getMealName().equals("Breakfast"));
    }

    @Test
    public void setCalorieCount() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.getMealData().setMealData("Breakfast", 500);
        assertEquals(500, a.getMealData().getMealCalories() == 500);
    }

    @Test
    public void setLoggedIn() {
        LoginScreenViewModel a = LoginScreenViewModel.getInstance();
        a.getLoginData().setLoggedIn(true);
        assertEquals(true, a.getLoginData().getLoggedIn());
    }

    @Test
    public void getGender() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setGender("Male");
        assertEquals("Male", a.getUserData().getGender().equals("Male"));
    }

    @Test
    public void getHeight() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setHeight(68);
        assertEquals(68, a.getUserData().getHeight() == 68);
    }

    @Test
    public void getWeight() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setWeight(150);
        assertEquals(150, a.getUserData().getWeight() != 150);
    }

    @Test
    public void getAge() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setAge(18);
        assertEquals(18, a.getUserData().getAge() == 18);
    }

    @Test
    public void getCalorieGoal() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setCalorieGoal(2500);
        assertEquals(2500, a.getUserData().getAge() == 2500);
    }

    @Test
    public void getMealName() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.getMealData().setMealData("burrito", 500);
        assertEquals("burrito", a.getMealData().getMealName().equals("burrito"));
    }

    @Test
    public void getMealCalories() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.getMealData().setMealData("burrito", 500);
        assertEquals(500, a.getMealData().getMealCalories() == 500);
    }

    @Test
    public void resetTotalDayCalories() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.getMealData().setMealData("burrito", 500);
        a.resetTotalDayCalories();
        assertEquals(0, a.getTotalDayCalories());
    }

    @Test
    public void getTotalDayCalories() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.getMealData().setMealData("burrito", 500);
        a.addCaloriesToTotal(300);
        a.addCaloriesToTotal(500);
        assertEquals(800, a.getTotalDayCalories() == 800);
    }

    @Test
    public void addNewUser() {
        LoginScreenViewModel a = LoginScreenViewModel.getInstance();
        a.getLoginData().setUsername("christina");
        assertEquals("christina", a.getLoginData().getUsername().equals("christina"));
    }

    @Test
    public void setMealData() {
        MealData mealData = new MealData();
        mealData.setMealData("pasta", 150);
        assertEquals("pasta", mealData.getMealName());
        assertEquals(150, mealData.getMealCalories());
        assertTrue(mealData.getMeals().contains("pasta"));
    }

    @Test
    public void getMeals() {
        MealData mealData = new MealData();
        mealData.setMealData("teriyaki chicken", 300);
        mealData.setMealData("kung pao chicken", 400);
        assertEquals(2, mealData.getMeals().size());
        assertTrue(mealData.getMeals().contains("teriyaki chicken"));
        assertTrue(mealData.getMeals().contains("kung pao chicken"));
    }

    @Test
    public void resetMealCalories() {
        MealData mealData = new MealData();
        mealData.setMealData("peking duck", 400);
        mealData.resetMealCalories();
        assertEquals(0, mealData.getMealCalories());
    }



    ////  SPRINT FOUR JUNITS!!!!!! ////
    @Test
    public void getPantryCalories() {
        PantryData pantryData = PantryData.getInstance();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("spam", 100, 2));
        ingredients.add(new Ingredient("eggs", 50, 3));
        pantryData.setIngredientList(ingredients);
        int actualCal = pantryData.getCaloriesFromName("spam");
        assertEquals(100,actualCal);
    }

    @Test
    public void addToShoppingList() {
        ShoppingListData shoppingListData = ShoppingListData.getInstance();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("horseradish", 30, 5));
        ingredients.add(new Ingredient("pickled ginger", 40, 2));
        shoppingListData.setShoppingList(ingredients);
        assertEquals(ingredients, shoppingListData.getShoppingList());
    }

    @Test
    public void shoppingIngredientValidityCheck(){
        String ingredientName = " Hoisin Sauce_";
        String ingredientName2 = "  ";
        assertEquals(false, IngredientsViewModel.checkValidity(ingredientName2));
        assertEquals(true, IngredientsViewModel.checkValidity(ingredientName));
    }

    @Test
    public void getItemQuantityCheck() {
        String item = "Chicken 10";
        int expectedQuantity = 10;
        int actualQuantity = RecipeDetailsFrag.getItemQuantity(item);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void createFactoryIngredient() {
        MediumCalorieIngredientFactory factory = new MediumCalorieIngredientFactory();
        IngredientType ingredient = factory.createIngredient();
        assertNotNull(ingredient);
        assertTrue(ingredient instanceof MediumCalorieIngredientType);
    }

    @Test
    public void getShoppingListCalories() {
        ShoppingListData shoppingList = ShoppingListData.getInstance();
        Ingredient sugar = new Ingredient("sugar", 50, 5);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(sugar);
        shoppingList.setShoppingList(ingredients);
        assertEquals(50, shoppingList.getCaloriesFromName("sugar"));
        assertEquals(0, shoppingList.getCaloriesFromName("pepper"));
    }

    @Test
    public void getIngredientCalories() {
        Ingredient ingredient = new Ingredient("sugar", 50, 5);
        assertEquals(50, ingredient.getCalories());
    }

    @Test
    public void setIngredientCalories() {
        Ingredient ingredient = new Ingredient("sugar", 50, 5);
        ingredient.setCalories(70);
        assertEquals(70, ingredient.getCalories());
    }

    @Test
    public void getIngredientName() {
        Ingredient ingredient = new Ingredient("sugar", 50, 5);
        assertEquals("sugar", ingredient.getName());
    }

    @Test
    public void setIngredientName() {
        Ingredient ingredient = new Ingredient("sugar", 50, 5);
        ingredient.setName("salt");
        assertEquals("salt", ingredient.getName());
    }

    @Test
    public void getIngredientQuantity() {
        Ingredient ingredient = new Ingredient("sugar", 50, 5);
        assertEquals(5, ingredient.getQuantity());
    }

    @Test
    public void setIngredientQuantity() {
        Ingredient ingredient = new Ingredient("sugar", 50, 5);
        ingredient.setQuantity(200);
        assertEquals(200, ingredient.getQuantity());
    }

}