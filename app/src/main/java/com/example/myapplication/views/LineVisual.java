package com.example.myapplication.views;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.InputMealViewModel;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;


public class LineVisual extends Fragment {
    private EditText mealNameInputET;
    private EditText mealCaloriesInputET;
    private Button submitMealInfoButton;
    private Button logMealsButton;
    private Button lineChartButton;
    private InputMealViewModel mealVM = InputMealViewModel.getInstance();
    private PersonalInfoViewModel userInfoVM = PersonalInfoViewModel.getInstance();

    private int calorieGoal = userInfoVM.getUserData().getCalorieGoal();
    private String mealName = mealVM.getMealName();
    private int mealCalories = mealVM.getMealCalories();
    private LineChart lineChart;


    public LineVisual() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_line_visual, container, false);

        lineChartButton = root.findViewById(R.id.goToLineChart);

        lineChart = (LineChart) root.findViewById(R.id.linechart);


        ArrayList<Entry> lineEntries = mealVM.getMealData().getCaloriesByDay();
//
        if (lineEntries.size() >= 7) {
            redrawLineChart((ArrayList<Entry>) lineEntries.subList(lineEntries.size() - 7, lineEntries.size()), lineChart);
        } else {
            redrawLineChart(lineEntries, lineChart);
        }

        redrawLineChart(lineEntries, lineChart);
        root.findViewById(R.id.goBackToInputButton2).setOnClickListener(v -> replaceFragment(new InputMealScreenFrag()));

        return root;
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

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }
}
