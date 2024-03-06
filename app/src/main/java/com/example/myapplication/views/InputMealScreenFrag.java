package com.example.myapplication.views;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
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
    PieChart pieChart;
    LineChart lineChart;
    public InputMealScreenFrag() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_input_meal_screen, container, false);
        pieChart = (PieChart) root.findViewById(R.id.piechart);
        //pieChart.setVisibility(View.INVISIBLE);
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(33, "Day's Caloric Intake"));
        pieEntries.add(new PieEntry(67, "Daily Goal"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Label");
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.invalidate();

        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);

        pieDataSet.setSliceSpace(1f);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(20f);
        pieChart.setTransparentCircleRadius(60f);

        lineChart = (LineChart) root.findViewById(R.id.linechart);
        ArrayList<Entry> lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(1, 2000));
        lineEntries.add(new Entry(2, 1500));
        lineEntries.add(new Entry(3, 2200));
        lineEntries.add(new Entry(4, 2000));

        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Label");
        lineDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        lineChart.getDescription().setEnabled(false);
        lineChart.animateY(1400, Easing.EaseInOutQuad);
        lineChart.invalidate();
        // Inflate the layout for this fragment
        return root;

    }
}