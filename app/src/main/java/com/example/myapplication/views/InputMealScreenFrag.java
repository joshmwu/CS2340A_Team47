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
import com.github.mikephil.charting.utils.ColorTemplate;

import org.checkerframework.checker.units.qual.A;

import java.io.*;
import java.util.ArrayList;

public class InputMealScreenFrag extends Fragment {
    private EditText mealNameInputET;
    private EditText mealCaloriesInputET;
    private Button submitMealInfoButton;
    private InputMealViewModel mealVM=InputMealViewModel.getInstance();
    private PersonalInfoViewModel userInfoVM = PersonalInfoViewModel.getInstance();

    private int calorieGoal = userInfoVM.getUserData().getCalorieGoal();
    private String mealName = mealVM.getMealName();
    private int mealCalories = mealVM.getMealCalories();
    public InputMealScreenFrag() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_input_meal_screen, container, false);

        PieChart pieChart = (PieChart) root.findViewById(R.id.piechart);
        mealNameInputET = root.findViewById(R.id.mealNameEditText);
        mealCaloriesInputET = root.findViewById(R.id.mealCaloriesEditText);
        submitMealInfoButton = root.findViewById(R.id.submitMealInfoButton);

        //pieChart.setVisibility(View.INVISIBLE);
        ArrayList<PieEntry> enters = new ArrayList<>();
        enters.add(new PieEntry(mealCalories, "Day's Caloric Intake"));
        enters.add(new PieEntry(calorieGoal-mealCalories, "Daily Goal"));
        redrawChart(enters, pieChart);

        submitMealInfoButton.setOnClickListener(v -> {
            mealName=String.valueOf(mealNameInputET.getText());
            mealCalories+=Integer.parseInt(mealCaloriesInputET.getText().toString());
            mealVM.setMealData(mealName,mealCalories);
            mealName = mealVM.getMealName();
            mealCalories = mealVM.getMealCalories();
            enters.clear();
            if(mealCalories<calorieGoal) {
                enters.add(new PieEntry(mealCalories, "Day's Caloric Intake"));
                enters.add(new PieEntry(calorieGoal - mealCalories, "Remaining Calories"));
            }
            else{
                enters.add(new PieEntry((mealCalories-calorieGoal), "Excess Caloric Intake"));
                enters.add(new PieEntry(calorieGoal, "Day's Calorie Goal"));
            }
            redrawChart(enters, pieChart);
        });

        return root;
    }
    private void redrawChart(ArrayList<PieEntry> enters, PieChart pieChart){
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
}