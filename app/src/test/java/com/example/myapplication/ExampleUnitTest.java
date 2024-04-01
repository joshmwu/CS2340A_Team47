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

import com.example.myapplication.models.LoginData;
import com.example.myapplication.viewmodels.InputMealViewModel;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;
import com.example.myapplication.views.IngredientAdapter;
import com.example.myapplication.views.LoginScreen;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {

    private LoginScreenViewModel loginScreenViewModel;
    private Context mockContext;
    private DatabaseReference databaseReference;

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
//        Context Context;
        //FirebaseApp.initializeApp(InstrumentationRegistry.getInstrumentation().targetContext);
        //LoginScreenViewModel a = LoginScreenViewModel.getInstance();
        //a.getLoginData().setUsername("josh");
        when(databaseReference.child("Users")).thenReturn(databaseReference);
        when(databaseReference.getValue()).thenReturn("")
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


}