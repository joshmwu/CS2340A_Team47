package com.example.myapplication.views;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.InputMealViewModel;
import com.example.myapplication.viewmodels.LoginScreenViewModel;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class InputMealScreenFrag extends Fragment implements Subject {
    private EditText mealNameInputET;
    private EditText mealCaloriesInputET;
    private TextView welcomeUser;
    private TextView userHeightTV;
    private TextView userWeightTV;
    private TextView userGenderTV;
    private TextView userGoalTV;
    private TextViewMessage textViewMessage;
    private TextViewColorMessage textViewMeal;
    private Button submitMealInfoButton;
    private Button logMealsButton;
    private Button goToPieChart;
    private Button goToLineChart;
    private InputMealViewModel mealVM = InputMealViewModel.getInstance();
    private PersonalInfoViewModel userInfoVM = PersonalInfoViewModel.getInstance();
    private LoginScreenViewModel loginVM = LoginScreenViewModel.getInstance();

    private int calorieGoal = userInfoVM.getUserData().getCalorieGoal();
    private String mealName = mealVM.getMealName();
    private int mealCalories = mealVM.getMealCalories();
    private PieChart pieChart;
    private LineChart lineChart;
    private List<Observer> listOfObservers = new ArrayList<>();
    public InputMealScreenFrag() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater
                .inflate(R.layout.fragment_input_meal_screen, container, false);

        welcomeUser = root.findViewById(R.id.welcomeUser);
        userHeightTV = root.findViewById(R.id.userHeightTextView);
        userWeightTV = root.findViewById(R.id.userWeightTextView);
        userGenderTV = root.findViewById(R.id.userGenderTextView);
        userGoalTV = root.findViewById(R.id.userGoalTextView);
        textViewMeal = new TextViewColorMessage(root.findViewById(R.id.inputMealScreenMessageTV));
        textViewMessage = new TextViewMessage(root.findViewById(R.id.inputMealMealTV));

        addObserver(textViewMeal);
        addObserver(textViewMessage);

        welcomeUser.setText("Welcome, " + loginVM.getLoginData().getUsername() + "!");
        userHeightTV.setText("Height: " + userInfoVM.getUserData().getHeight());
        userWeightTV.setText("Weight: " + userInfoVM.getUserData().getWeight());
        userGenderTV.setText("Gender: " + userInfoVM.getUserData().getGender());
        userGoalTV.setText("Calorie Goal: " + userInfoVM.getUserData().getCalorieGoal());

        mealNameInputET = root.findViewById(R.id.mealNameEditText);
        mealCaloriesInputET = root.findViewById(R.id.mealCaloriesEditText);
        submitMealInfoButton = root.findViewById(R.id.submitMealInfoButton);
        logMealsButton = root.findViewById(R.id.logMealsButton);

        pieChart = (PieChart) root.findViewById(R.id.piechart);
        lineChart = (LineChart) root.findViewById(R.id.linechart);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        if (mealVM.getTotalDayCalories() < calorieGoal) {
            pieEntries.add(new PieEntry(mealVM.getTotalDayCalories(), "Day's Caloric Intake"));
            pieEntries.add(new PieEntry(calorieGoal - mealVM.getTotalDayCalories(),
                    "Remaining Calories"));
        } else {
            pieEntries.add(new PieEntry((mealVM.getTotalDayCalories() - calorieGoal),
                    "Excess Caloric Intake"));
            pieEntries.add(new PieEntry(calorieGoal, "Day's Calorie Goal"));
        }

        ArrayList<Entry> lineEntries = mealVM.getMealData().getCaloriesByDay();

        submitMealInfoButton.setOnClickListener(v -> {
            if (mealNameInputET.getText().toString().isBlank()
                    || mealNameInputET.getText().toString().isBlank()) {
                notifyObserver("");
                mealNameInputET.setText("");
                mealCaloriesInputET.setText("");
            } else {
                mealName = String.valueOf(mealNameInputET.getText());
                mealCalories = Integer.parseInt(mealCaloriesInputET.getText().toString());
                mealVM.setMealData(loginVM.getLoginData().getUsername(), mealName, mealCalories);
                pieEntries.clear();
                int totCals = mealVM.getTotalDayCalories();
                if (totCals < calorieGoal) {
                    pieEntries.add(new PieEntry(totCals, "Day's Caloric Intake"));
                    pieEntries.add(new PieEntry(calorieGoal - totCals,
                            "Remaining Calories"));
                } else {
                    pieEntries.add(new PieEntry((totCals - calorieGoal),
                            "Excess Caloric Intake"));
                    pieEntries.add(new PieEntry(calorieGoal, "Day's Calorie Goal"));
                }
                mealCalories = 0;
                notifyObserver(mealName);
                mealNameInputET.setText("");
                mealCaloriesInputET.setText("");
            }

        });

        logMealsButton.setOnClickListener(v -> {
            lineEntries.add(new Entry(mealVM.getDay(), mealVM.getTotalDayCalories()));
            pieEntries.clear();
            mealVM.resetTotalDayCalories();
            mealVM.setDay(mealVM.getDay() + 1);
            mealName = null;
            mealCalories = 0;
        });

        root.findViewById(R.id.goToPieChart)
                .setOnClickListener(v -> replaceFragment(new CircleVisual(pieEntries)));
        root.findViewById(R.id.goToLineChart)
                .setOnClickListener(v -> replaceFragment(new LineVisual(lineEntries)));

        return root;
    }
    public void addObserver(Observer observer) {
        listOfObservers.add(observer);
    }
    public void removeObserver(Observer observer) {
        listOfObservers.remove(observer);
    }
    public void notifyObserver(String meal) {
        for (Observer observer : listOfObservers) {
            observer.update(meal);
        }
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
}