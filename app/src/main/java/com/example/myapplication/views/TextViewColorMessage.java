package com.example.myapplication.views;

import android.widget.TextView;

public class TextViewColorMessage implements Observer {
    private TextView textViewMessage;
    public TextViewColorMessage(TextView textViewMessage) {
        this.textViewMessage = textViewMessage;
    }

    @Override
    public void update(String meal) {
        if (meal.isBlank()) {
            textViewMessage.setText("Not Successful");
            textViewMessage.setTextColor(0xFFFF0000);
        } else {
            textViewMessage.setText("Successfully Inputted");
            textViewMessage.setTextColor(0xFF00FF00);
        }

    }
}
