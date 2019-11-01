package com.alisson.zaptel.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alisson.zaptel.MainActivity;
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
    private Button buttonDeactivateUser;
    private Button buttonLogout;

    private Context mContext;

    private User user;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public EditUserFragment(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_user,
                container, false);

        getActivity().setTitle("Edit User");

        editTextUserNameEdit = (EditText) rootView.findViewById(R.id.userNameEdit);
        editTextUserEmailEdit = (EditText) rootView.findViewById(R.id.userEmailEdit);
        editTextUserPasswordEdit = (EditText) rootView.findViewById(R.id.userPasswordEdit);
        editTextUserDateOfBirthEdit = (EditText) rootView.findViewById(R.id.userDateOfBirthEdit);
        editTextUserPhoneEdit = (EditText) rootView.findViewById(R.id.userPhoneEdit);
        radioGroupUserGenderEdit = (RadioGroup) rootView.findViewById(R.id.userGenderEdit);
        buttonSaveEdition = (Button) rootView.findViewById(R.id.buttonSaveEdition);
        buttonDeactivateUser = (Button) rootView.findViewById(R.id.buttonDeactivateUser);
        buttonLogout = (Button) rootView.findViewById(R.id.buttonLogout);

        editTextUserNameEdit.setText(user.getName());
        editTextUserEmailEdit.setText(user.getEmail());
        editTextUserPasswordEdit.setText(user.getPassword());
        editTextUserDateOfBirthEdit.setText(user.getDateOfBirth());
        editTextUserPhoneEdit.setText(user.getPhone());
        switch (user.getGender()) {
            case "Female":
                radioGroupUserGenderEdit.check(R.id.radio_female);
                break;
            case "Other":
                radioGroupUserGenderEdit.check(R.id.radio_other);
                break;
            default:
                radioGroupUserGenderEdit.check(R.id.radio_male);
        }


        buttonSaveEdition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextUserNameEdit.getText().toString().isEmpty() &&
                        !editTextUserEmailEdit.getText().toString().isEmpty() &&
                        !editTextUserPasswordEdit.getText().toString().isEmpty() &&
                        !editTextUserDateOfBirthEdit.getText().toString().isEmpty() &&
                        !editTextUserPhoneEdit.getText().toString().isEmpty() &&
                        radioGroupUserGenderEdit.getCheckedRadioButtonId() != -1) {

                    User user = new User();
                    user.setName(editTextUserNameEdit.getText().toString());
                    user.setEmail(editTextUserEmailEdit.getText().toString());
                    user.setPassword(editTextUserPasswordEdit.getText().toString());
                    user.setDateOfBirth(editTextUserDateOfBirthEdit.getText().toString());
                    user.setPhone(editTextUserPhoneEdit.getText().toString());
                    switch (radioGroupUserGenderEdit.getCheckedRadioButtonId()) {
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
                    restController.editUser(mContext, user);
                } else {
                    Toast.makeText(mContext, "It's necessary to fill all information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDeactivateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Warning");
                builder.setMessage("Are you sure you want to delete this user?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        AlertDialog alertDialogPassword;
                        AlertDialog.Builder builderPassword = new AlertDialog.Builder(mContext);
                        builderPassword.setTitle("Warning");
                        builderPassword.setMessage("Please enter with the user password to confirm");
                        final EditText input = new EditText(mContext);
                        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        builderPassword.setView(input);
                        builderPassword.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                RestController restController = new RestController();
                                FragmentManager fragmentManager = getFragmentManager();
                                restController.deleteUser(mContext, fragmentManager, input.getText().toString());
                            }
                        });
                        alertDialogPassword = builderPassword.create();
                        alertDialogPassword.show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestController restController = new RestController();
                FragmentManager fragmentManager = getFragmentManager();
                restController.logoutUser(mContext, fragmentManager);
            }
        });

        return rootView;
    }
}
