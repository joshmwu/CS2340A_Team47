package com.example.myapplication;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.renderscript.ScriptGroup;

import com.example.myapplication.models.LoginData;
import com.example.myapplication.viewmodels.InputMealViewModel;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;
import com.example.myapplication.views.LoginScreen;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void usernameWorks() {
        LoginScreenViewModel a = LoginScreenViewModel.getInstance();
        a.getLoginData().setUsername("josh");
        if (!a.getLoginData().getUsername().equals("josh")) {
            throw new RuntimeException("Doesnt work!");
        }
    }

    @Test
    public void passwordWorks() {
        LoginScreenViewModel a = LoginScreenViewModel.getInstance();
        a.getLoginData().setPassword("josh");
        if (!a.getLoginData().getPassword().equals("josh")) {
            throw new RuntimeException("Doesnt work!");
        }
    }

    @Test
    public void resetCalories() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.getMealData().resetMealCalories();
        if (a.getTotalDayCalories() != 0) {
            throw new RuntimeException("Doesnt work!");
        }
    }

    @Test
    public void getDay() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.setDay(3);
        if (a.getDay() != 3) {
            throw new RuntimeException("Doesnt work!");
        }
    }

    @Test
    public void setMealName() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.getMealData().setMealData("Breakfast", 500);
        if (!a.getMealData().getMealName().equals("Breakfast")) {
            throw new RuntimeException("Doesnt work!");
        }
    }

    public void setCalorieCount() {
        InputMealViewModel a = InputMealViewModel.getInstance();
        a.getMealData().setMealData("Breakfast", 500);
        if (a.getMealData().getMealCalories() != 500) {
            throw new RuntimeException("Doesnt work!");
        }
    }

    public void setLoggedIn() {
        LoginScreenViewModel a = LoginScreenViewModel.getInstance();
        a.getLoginData().setLoggedIn(true);
        if (a.getLoginData().getLoggedIn()) {
            throw new RuntimeException("Doesnt work!");
        }
    }

    public void getGender() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setGender("Male");
        if (!a.getUserData().getGender().equals("Male")) {
            throw new RuntimeException("Doesnt work!");
        }
    }

    public void getHeight() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setHeight(68);
        if (a.getUserData().getHeight() != 68) {
            throw new RuntimeException("Doesnt work!");
        }
    }

    public void getWeight() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setWeight(150);
        if (a.getUserData().getWeight() != 150) {
            throw new RuntimeException("Doesnt work!");
        }
    }

    public void getAge() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setAge(18);
        if (a.getUserData().getAge() != 18) {
            throw new RuntimeException("Doesnt work!");
        }
    }

    public void getCalorieGoal() {
        PersonalInfoViewModel a = PersonalInfoViewModel.getInstance();
        a.getUserData().setCalorieGoal(2500);
        if (a.getUserData().getAge() != 2500) {
            throw new RuntimeException("Doesnt work!");
        }
    }

}