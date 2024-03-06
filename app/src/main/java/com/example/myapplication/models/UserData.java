package com.example.myapplication.models;

public class UserData extends LoginData {
    private int height;
    private int weight;
    private String gender;

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

}
