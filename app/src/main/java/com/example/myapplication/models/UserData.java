package com.example.myapplication.models;

public class UserData {
    private static volatile UserData instance;
    private int height;
    private int weight;
    private int age;
    private String gender = "No Input";
    private int calorieGoal = 2000; //default value

    private UserData() { }

    public static UserData getInstance() {
        if (instance == null) {
            synchronized (UserData.class) {
                if (instance == null) {
                    instance = new UserData();
                }
            }
        }
        return instance;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCalorieGoal(int calories) {
        this.calorieGoal = calories;
    }

    public int getCalorieGoal() {
        return calorieGoal;
    }
}
