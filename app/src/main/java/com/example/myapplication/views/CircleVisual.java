package com.example.myapplication.views;

import android.graphics.Color;
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
import com.example.myapplication.databinding.HomeScreenBinding;
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

import java.util.ArrayList;

public class CircleVisual extends Fragment {
    private EditText mealNameInputET;
    private EditText mealCaloriesInputET;
    private Button submitMealInfoButton;
    private Button logMealsButton;

    private Button pieChartButton;
    private InputMealViewModel mealVM = InputMealViewModel.getInstance();
    private PersonalInfoViewModel userInfoVM = PersonalInfoViewModel.getInstance();

    private int calorieGoal = userInfoVM.getUserData().getCalorieGoal();
    private String mealName = mealVM.getMealName();
    private int mealCalories = mealVM.getMealCalories();
    private PieChart pieChart;

    public CircleVisual() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_input_meal_screen, container, false);

        pieChartButton = root.findViewById(R.id.goToPieChart);

        pieChart = (PieChart) root.findViewById(R.id.piechart);

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(mealCalories, "Day's Caloric Intake"));
        pieEntries.add(new PieEntry(calorieGoal-mealCalories, "Daily Goal"));
        redrawPieChart(pieEntries, pieChart);

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
            mealVM.setDay(mealVM.getDay() + 1);
            mealName = null;
            mealCalories = 0;

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
}