package com.example.myapplication.views;


public class CanMakeFiltering implements FilteringStrategy {
    private String spinnerVal;
    public CanMakeFiltering(String spinnerVal) {
        this.spinnerVal = spinnerVal;
    }
    @Override
    public void satisfies(String str, FilterCallback callback) {
        if (spinnerVal.equals("None")) {
            callback.onFilterResult(true);
        } else if (spinnerVal.equals("Can Make")) {
            boolean satisfies = !str.contains("*");
            callback.onFilterResult(satisfies);
        } else if (spinnerVal.equals("Cannot Make")) {
            boolean satisfies = str.contains("*");
            callback.onFilterResult(satisfies);
        }
    }
}


