package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import android.content.Context;
import android.renderscript.ScriptGroup;

import com.example.myapplication.models.LoginData;
import com.example.myapplication.viewmodels.InputMealViewModel;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;
import com.example.myapplication.views.LoginScreen;
import com.google.firebase.FirebaseApp;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
public class ExampleUnitTest {

    @Test
    public void usernameWorks() {
        LoginScreenViewModel a = LoginScreenViewModel.getInstance();
        a.getLoginData().setUsername("josh");
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

    public void setCalorieCount() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.getMealData().setMealData("Breakfast", 500);
        assertEquals(500, a.getMealData().getMealCalories() == 500);
    }

    public void setLoggedIn() {
        LoginScreenViewModel a = LoginScreenViewModel.getInstance();
        a.getLoginData().setLoggedIn(true);
        assertEquals(true, a.getLoginData().getLoggedIn());
    }

    public void getGender() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setGender("Male");
        assertEquals("Male", a.getUserData().getGender().equals("Male"));
    }

    public void getHeight() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setHeight(68);
        assertEquals(68, a.getUserData().getHeight() == 68);
    }

    public void getWeight() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setWeight(150);
        assertEquals(150, a.getUserData().getWeight() != 150);
    }

    public void getAge() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setAge(18);
        assertEquals(18, a.getUserData().getAge() == 18);
    }

    public void getCalorieGoal() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setCalorieGoal(2500);
        assertEquals(2500, a.getUserData().getAge() == 2500);
    }
}