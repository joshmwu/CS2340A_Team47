package com.example.myapplication.views;

import static androidx.core.content.ContextCompat.getSystemService;
import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
    private Spinner genderSpinner;
    private TextView savedMessage;
    private Button saveButton;
    private TextView displayHeight;
    private TextView displayWeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        personalInfoViewModel = PersonalInfoViewModel.getInstance();
        heightET = view.findViewById(R.id.enterHeight);
        weightET = view.findViewById(R.id.enterWeight);

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
                saveConfigurationData(heightET, weightET, gender);
                hideKeyboard(v);
                displayHeight = view.findViewById(R.id.displayHeight);
                displayWeight = view.findViewById(R.id.displayWeight);
                String height = "My height: " + personalInfoViewModel.getUserData().getHeight();
                displayHeight.setText(height);
                String weight = "My weight: " + personalInfoViewModel.getUserData().getWeight();
                displayWeight.setText(weight);
            }
        });
        return view;
    }

    protected void saveConfigurationData(EditText heightET, EditText weightET, String gender) {
        int height = Integer.parseInt(heightET.getText().toString());
        int weight = Integer.parseInt(weightET.getText().toString());
        personalInfoViewModel.updateData(height, weight, gender, 2000);//TODO: CALCULATE CALORIE GOAL
        // Clear the EditText fields
        heightET.setText("");
        weightET.setText("");
    }

}