package com.alisson.zaptel.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alisson.zaptel.R;
import com.alisson.zaptel.controllers.RestController;
import com.alisson.zaptel.models.User;

public class RegisterFragment extends Fragment {
    private EditText editTextUserNameRegister;
    private EditText editTextUserEmailRegister;
    private EditText editTextUserPasswordRegister;
    private EditText editTextUserDateOfBirthRegister;
    private EditText editTextUserPhoneRegister;
    private RadioGroup radioGroupUserGenderRegister;
    private Button buttonSave;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public RegisterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register,
                container, false);

        editTextUserNameRegister = (EditText) rootView.findViewById(R.id.userNameRegister);
        editTextUserEmailRegister = (EditText) rootView.findViewById(R.id.userEmailRegister);
        editTextUserPasswordRegister = (EditText) rootView.findViewById(R.id.userPasswordRegister);
        editTextUserDateOfBirthRegister = (EditText) rootView.findViewById(R.id.userDateOfBirthRegister);
        editTextUserPhoneRegister = (EditText) rootView.findViewById(R.id.userPhoneRegister);
        radioGroupUserGenderRegister = (RadioGroup) rootView.findViewById(R.id.userGenderRegister);
        buttonSave = (Button) rootView.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextUserNameRegister.getText().toString().isEmpty() &&
                        !editTextUserEmailRegister.getText().toString().isEmpty() &&
                        !editTextUserPasswordRegister.getText().toString().isEmpty() &&
                        !editTextUserDateOfBirthRegister.getText().toString().isEmpty() &&
                        !editTextUserPhoneRegister.getText().toString().isEmpty() &&
                        radioGroupUserGenderRegister.getCheckedRadioButtonId() != -1) {

                    User user = new User();
                    user.setName(editTextUserNameRegister.getText().toString());
                    user.setEmail(editTextUserEmailRegister.getText().toString());
                    user.setPassword(editTextUserPasswordRegister.getText().toString());
                    user.setDateOfBirth(editTextUserDateOfBirthRegister.getText().toString());
                    user.setPhone(editTextUserPhoneRegister.getText().toString());
                    switch (radioGroupUserGenderRegister.getCheckedRadioButtonId()) {
                        case R.id.radio_male:
                            user.setGender("Male");
                            break;
                        case R.id.radio_female:
                            user.setGender("Female");
                            break;
                        case R.id.radio_other:
                            user.setGender("Other");
                            break;
                    }

                    RestController restController = new RestController();
                    Integer userId = restController.createUser(mContext, user);
                    if (userId != null) {
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(mContext, "ERROR", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(mContext, "It's necessary to fill all information", Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }
}
