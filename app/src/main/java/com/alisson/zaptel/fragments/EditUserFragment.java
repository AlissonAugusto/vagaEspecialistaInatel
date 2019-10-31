package com.alisson.zaptel.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alisson.zaptel.R;
import com.alisson.zaptel.controllers.RestController;
import com.alisson.zaptel.models.User;

public class EditUserFragment extends Fragment {
    private EditText editTextUserNameEdit;
    private EditText editTextUserEmailEdit;
    private EditText editTextUserPasswordEdit;
    private EditText editTextUserDateOfBirthEdit;
    private EditText editTextUserPhoneEdit;
    private RadioGroup radioGroupUserGenderEdit;
    private Button buttonSaveEdition;
    private Button buttonDesactivateUser;
    private Button buttonLogout;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public EditUserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register,
                container, false);

        getActivity().setTitle("Register");

        editTextUserNameEdit = (EditText) rootView.findViewById(R.id.userNameEdit);
        editTextUserEmailEdit = (EditText) rootView.findViewById(R.id.userEmailEdit);
        editTextUserPasswordEdit = (EditText) rootView.findViewById(R.id.userPasswordEdit);
        editTextUserDateOfBirthEdit = (EditText) rootView.findViewById(R.id.userDateOfBirthEdit);
        editTextUserPhoneEdit = (EditText) rootView.findViewById(R.id.userPhoneEdit);
        radioGroupUserGenderEdit = (RadioGroup) rootView.findViewById(R.id.userGenderEdit);
        buttonSaveEdition = (Button) rootView.findViewById(R.id.buttonSaveEdition);
        buttonDesactivateUser = (Button) rootView.findViewById(R.id.buttonDesactivateUser);
        buttonLogout = (Button) rootView.findViewById(R.id.buttonLogout);

        String user;
        RestController restController = new RestController();
        user = restController.getUser(mContext);

        editTextUserNameEdit.setText(user);

//        buttonSaveEdition.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!editTextUserNameEdit.getText().toString().isEmpty() &&
//                        !editTextUserEmailEdit.getText().toString().isEmpty() &&
//                        !editTextUserPasswordEdit.getText().toString().isEmpty() &&
//                        !editTextUserDateOfBirthEdit.getText().toString().isEmpty() &&
//                        !editTextUserPhoneEdit.getText().toString().isEmpty() &&
//                        radioGroupUserGenderEdit.getCheckedRadioButtonId() != -1) {
//
//                    User user = new User();
//                    user.setName(editTextUserNameEdit.getText().toString());
//                    user.setEmail(editTextUserEmailEdit.getText().toString());
//                    user.setPassword(editTextUserPasswordEdit.getText().toString());
//                    user.setDateOfBirth(editTextUserDateOfBirthEdit.getText().toString());
//                    user.setPhone(editTextUserPhoneEdit.getText().toString());
//                    switch (radioGroupUserGenderEdit.getCheckedRadioButtonId()) {
//                        case R.id.radio_male:
//                            user.setGender("Male");
//                            break;
//                        case R.id.radio_female:
//                            user.setGender("Female");
//                            break;
//                        case R.id.radio_other:
//                            user.setGender("Other");
//                            break;
//                    }
//
//                    RestController restController = new RestController();
//                    restController.createUser(mContext, user);
//                } else{
//                    Toast.makeText(mContext, "It's necessary to fill all information", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        return rootView;
    }
}
