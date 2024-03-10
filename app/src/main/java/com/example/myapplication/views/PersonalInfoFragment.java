package com.example.myapplication.views;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.PersonalInfoViewModel;

public class PersonalInfoFragment extends Fragment {
    private PersonalInfoViewModel personalInfoViewModel;
    private EditText heightET;
    private EditText weightET;
    private EditText ageET;
    private Spinner genderSpinner;
    private TextView savedMessage;
    private Button saveButton;
    private TextView displayHeight;
    private TextView displayWeight;
    private TextView displayAge;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        personalInfoViewModel = PersonalInfoViewModel.getInstance();
        heightET = view.findViewById(R.id.enterHeight);
        weightET = view.findViewById(R.id.enterWeight);
        ageET = view.findViewById(R.id.enterAge);
        savedMessage = view.findViewById(R.id.savedMessage);
        saveButton = view.findViewById(R.id.saveButton);

        Spinner genderSpinner = view.findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender = genderSpinner.getSelectedItem().toString();
                if (checkValidEditText(heightET) && checkValidEditText(weightET) && checkValidEditText(ageET)) {
                    saveConfigurationData(heightET, weightET, ageET, gender);
                    hideKeyboard(v);
                    displayHeight = view.findViewById(R.id.displayHeight);
                    displayWeight = view.findViewById(R.id.displayWeight);
                    displayAge = view.findViewById(R.id.displayAge);
                    String height = "My height: " + personalInfoViewModel.getUserData().getHeight();
                    displayHeight.setText(height);
                    String weight = "My weight: " + personalInfoViewModel.getUserData().getWeight();
                    displayWeight.setText(weight);
                    String age = "My age: " + personalInfoViewModel.getUserData().getAge();
                    displayAge.setText(age);
                    savedMessage.setText("Saved!");
                }
                else {
                    hideKeyboard(v);
                    savedMessage.setText("Please enter a valid height and weight.");
                }

            }
        });
        return view;
    }

    private boolean isInteger(String input) {
        return input.matches("-?\\d+");
    }

    private boolean checkValidEditText(EditText editText) {
        String editTextValue = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(editTextValue) && isInteger(editTextValue)) {
            return true;
        } else {
            return false;
        }
    }

    protected void saveConfigurationData(EditText heightET, EditText weightET, EditText ageET, String gender) {
        int height = Integer.parseInt(heightET.getText().toString());
        int weight = Integer.parseInt(weightET.getText().toString());
        int age = Integer.parseInt(ageET.getText().toString());
        int calories = 2000;
        if (gender.equals("Male")) {
            calories = (int) (1.35 * (13.397 * 0.453592 * weight + 4.799 * 2.54 * height - 5.677 * age + 88.362));
        } else if (gender.equals("Female")) {
            calories = (int) (1.35 * (9.247 * 0.453592  * weight + 3.098 * 2.54 * height - 4.330 * age + 447.593));
        }
        personalInfoViewModel.updateData(height, weight, age, gender, calories);
        // Clear the EditText fields
        heightET.setText("");
        weightET.setText("");
        ageET.setText("");
    }

}