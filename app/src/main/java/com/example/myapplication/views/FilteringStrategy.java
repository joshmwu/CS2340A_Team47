package com.example.myapplication.views;

public interface FilteringStrategy {
    void satisfies(String str, FilterCallback callback);
}

