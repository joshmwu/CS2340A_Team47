package com.example.myapplication.views;
import java.util.List;

public interface FilteringStrategy {
    void satisfies(String str, FilterCallback callback);
}

