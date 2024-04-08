package com.example.myapplication.viewmodels;


import com.example.myapplication.models.FirebaseService;
import com.example.myapplication.models.UserData;

public class PersonalInfoViewModel {
    private static PersonalInfoViewModel instance;
    private UserData userData = UserData.getInstance();
    private static FirebaseService firebaseService  = FirebaseService.getInstance();

    public PersonalInfoViewModel() { }

    public static synchronized PersonalInfoViewModel getInstance() {
        if (instance == null) {
            instance = new PersonalInfoViewModel();
        }
        return instance;
    }

    public UserData getUserData() {
        return userData;
    }

    public static FirebaseService getFirebaseService() {
        return firebaseService;
    }

    public void updateData(String username, int height, int weight,
                           int age, String gender, int calorieGoal) {
        // we should update firebase data, then update local data
        firebaseService.updatePersonalData(username, height, weight, age, gender, calorieGoal);
        firebaseService.setAllData();
    }

}
