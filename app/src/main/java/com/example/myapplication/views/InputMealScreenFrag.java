package com.example.myapplication.views;

import android.app.Person;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.InputMealViewModel;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.checkerframework.checker.units.qual.A;

import java.io.*;
import java.util.ArrayList;

public class InputMealScreenFrag extends Fragment {
    private EditText mealNameInputET;
    private EditText mealCaloriesInputET;
    private TextView userHeightTV;
    private TextView userWeightTV;
    private TextView userGenderTV;
    private Button submitMealInfoButton;
    private Button logMealsButton;
    private InputMealViewModel mealVM = InputMealViewModel.getInstance();
    private PersonalInfoViewModel userInfoVM = PersonalInfoViewModel.getInstance();

    private int calorieGoal = userInfoVM.getUserData().getCalorieGoal();
    private String mealName = mealVM.getMealName();
    private int mealCalories = mealVM.getMealCalories();
    private PieChart pieChart;
    private LineChart lineChart;
    public InputMealScreenFrag() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_input_meal_screen, container, false);

        userHeightTV = root.findViewById(R.id.userHeightTextView);
        userWeightTV = root.findViewById(R.id.userWeightTextView);
        userGenderTV = root.findViewById(R.id.userGenderTextView);

        userHeightTV.setText("Height: " + userInfoVM.getUserData().getHeight());
        userWeightTV.setText("Weight: " + userInfoVM.getUserData().getWeight());
        userGenderTV.setText("Gender: " + userInfoVM.getUserData().getGender());

        mealNameInputET = root.findViewById(R.id.mealNameEditText);
        mealCaloriesInputET = root.findViewById(R.id.mealCaloriesEditText);
        submitMealInfoButton = root.findViewById(R.id.submitMealInfoButton);
        logMealsButton = root.findViewById(R.id.logMealsButton);

        pieChart = (PieChart) root.findViewById(R.id.piechart);
        lineChart = (LineChart) root.findViewById(R.id.linechart);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(mealCalories, "Day's Caloric Intake"));
        pieEntries.add(new PieEntry(calorieGoal-mealCalories, "Daily Goal"));
        redrawPieChart(pieEntries, pieChart);

        ArrayList<Entry> lineEntries = mealVM.getMealData().getCaloriesByDay();
        redrawLineChart(lineEntries, lineChart);

        submitMealInfoButton.setOnClickListener(v -> {
            mealName=String.valueOf(mealNameInputET.getText());
            mealCalories+=Integer.parseInt(mealCaloriesInputET.getText().toString());
            mealVM.setMealData(mealName,mealCalories);
            mealName = mealVM.getMealName();
            mealCalories = mealVM.getMealCalories();
            pieEntries.clear();
            if (mealCalories<calorieGoal) {
                pieEntries.add(new PieEntry(mealCalories, "Day's Caloric Intake"));
                pieEntries.add(new PieEntry(calorieGoal - mealCalories, "Remaining Calories"));
            } else {
                pieEntries.add(new PieEntry((mealCalories-calorieGoal), "Excess Caloric Intake"));
                pieEntries.add(new PieEntry(calorieGoal, "Day's Calorie Goal"));
            }
            redrawPieChart(pieEntries, pieChart);
        });

        logMealsButton.setOnClickListener(v -> {
            lineEntries.add(new Entry(mealVM.getDay(), mealCalories));
            mealVM.setDay(mealVM.getDay() + 1);
            mealName = null;
            mealCalories = 0;
            if (lineEntries.size() >= 15) {
                redrawLineChart((ArrayList<Entry>) lineEntries.subList(lineEntries.size() - 15, lineEntries.size()), lineChart);
            } else if (lineEntries.size() >= 7) {
                redrawLineChart((ArrayList<Entry>) lineEntries.subList(lineEntries.size() - 7, lineEntries.size()), lineChart);
            } else {
                redrawLineChart(lineEntries, lineChart);
            }
            pieEntries.clear();
            if (mealCalories<calorieGoal) {
                pieEntries.add(new PieEntry(mealCalories, "Day's Caloric Intake"));
                pieEntries.add(new PieEntry(calorieGoal - mealCalories, "Remaining Calories"));
            } else {
                pieEntries.add(new PieEntry((mealCalories-calorieGoal), "Excess Caloric Intake"));
                pieEntries.add(new PieEntry(calorieGoal, "Day's Calorie Goal"));
            }
            redrawPieChart(pieEntries, pieChart);
        });
        return root;
    }
    private void redrawPieChart(ArrayList<PieEntry> enters, PieChart pieChart){
        PieDataSet pieDataSet = new PieDataSet(enters, "Label");
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.invalidate();

        //pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);

        pieDataSet.setSliceSpace(1f);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(20f);
        pieChart.setTransparentCircleRadius(60f);
    }
    private void redrawLineChart(ArrayList<Entry> lineEntries, LineChart lineChart) {
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Label");
        lineDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        lineChart.getDescription().setEnabled(false);
        lineChart.animateY(1400, Easing.EaseInOutQuad);
        lineChart.invalidate();
    }
}