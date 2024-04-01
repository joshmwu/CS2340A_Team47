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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class CircleVisual extends Fragment {
    private PieChart pieChart;
    private ArrayList<PieEntry> pieEntries;

    public CircleVisual(ArrayList<PieEntry> pieEntries) {
        this.pieEntries=pieEntries;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_circle_visual,
                container, false);

        pieChart = (PieChart) root.findViewById(R.id.piechart);

        redrawPieChart(pieEntries, pieChart);
        root.findViewById(R.id.goBackToInputButton).setOnClickListener(v -> {
                replaceFragment(new InputMealScreenFrag());
        });

        return root;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
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

        pieChart.getLegend().setTextSize(20f);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
    }

}