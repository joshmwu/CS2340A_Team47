package com.example.myapplication.views;

import android.widget.TextView;


public class TextViewMessage implements Observer {
    private TextView textViewMessage;
    public TextViewMessage(TextView textViewMessage) {
        this.textViewMessage = textViewMessage;
    }

    @Override
    public void update(String meal) {
        textViewMessage.setText("Recent: " + meal);
    }
}
