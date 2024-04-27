package com.example.myapplication.views;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class LineVisual extends Fragment {
    private ArrayList<Entry> lineEntries;
    private LineChart lineChart;


    public LineVisual(ArrayList<Entry> lineEntries) {
        this.lineEntries = lineEntries;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater
                .inflate(R.layout.fragment_line_visual, container, false);

        lineChart = (LineChart) root.findViewById(R.id.linechart);
        if (lineEntries.size() >= 7) {
            redrawLineChart((ArrayList<Entry>) lineEntries
                    .subList(lineEntries.size() - 7, lineEntries.size()), lineChart);
        } else {
            redrawLineChart(lineEntries, lineChart);
        }

        redrawLineChart(lineEntries, lineChart);
        root.findViewById(R.id.goBackToInputButton2)
                .setOnClickListener(v -> replaceFragment(new InputMealScreenFrag()));

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
