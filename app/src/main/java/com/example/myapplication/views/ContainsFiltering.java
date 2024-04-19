package com.example.myapplication.views;

public class ContainsFiltering implements FilteringStrategy {
    private String contained;
    public ContainsFiltering(String contained) {
        this.contained = contained;
    }
    @Override
    public void satisfies(String str, FilterCallback callback) {
        if (str.contains("*")) {
            str = str.substring(0, str.length() - 1);
        }

        boolean satisfies = str.contains(contained);
        callback.onFilterResult(satisfies);
    }
}


