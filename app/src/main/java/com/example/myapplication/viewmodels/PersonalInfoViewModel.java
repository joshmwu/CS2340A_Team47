package com.example.myapplication.viewmodels;


import com.example.myapplication.models.UserData;

public class PersonalInfoViewModel {
    private static PersonalInfoViewModel instance;
    private final UserData userData;

    public PersonalInfoViewModel() {
        userData = new UserData();
    }

    public static synchronized PersonalInfoViewModel getInstance() {
        if (instance == null) {
            instance = new PersonalInfoViewModel();
        }
        return instance;
    }
    public UserData getUserData() {
        return userData;
    }

    public void updateData(int height, int weight, int age, String gender, int calorieGoal) {
        this.userData.setHeight(height);
        this.userData.setWeight(weight);
        this.userData.setAge(age);
        this.userData.setGender(gender);
        this.userData.setCalorieGoal(calorieGoal);
    }

}
