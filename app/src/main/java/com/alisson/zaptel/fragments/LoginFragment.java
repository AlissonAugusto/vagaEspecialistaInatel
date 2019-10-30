package com.alisson.zaptel.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alisson.zaptel.R;
import com.alisson.zaptel.controllers.RestController;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister;
    private CheckBox chkRemember;
    private static String STATE_USER_EMAIL = "user_email";
    private static String STATE_USER_PASSWORD = "user_password";
    private static String STATE_CHK_REMEMBER = "chk_remember";

    private String email;
    private String password;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login,
                container, false);

        getActivity().setTitle("Login");

        editTextEmail = (EditText) rootView.findViewById(R.id.userEmail);
        editTextPassword = (EditText) rootView.findViewById(R.id.userPassword);
        buttonLogin = (Button) rootView.findViewById(R.id.buttonLogin);
        buttonRegister = (Button) rootView.findViewById(R.id.buttonRegister);
        chkRemember = (CheckBox) rootView.findViewById(R.id.checkBoxRemember);

        SharedPreferences sharedSettings = getActivity().
                getSharedPreferences(getActivity().getClass().
                        getSimpleName(), Context.MODE_PRIVATE);
        final boolean remember = sharedSettings.getBoolean(STATE_CHK_REMEMBER, false);
        chkRemember.setChecked(remember);
        if (remember) {
            editTextEmail.setText(sharedSettings.getString(STATE_USER_EMAIL, ""));
            editTextPassword.setText(sharedSettings.getString(STATE_USER_PASSWORD, ""));
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().isEmpty() &&
                        !editTextPassword.getText().toString().isEmpty()) {
                    email = String.valueOf(editTextEmail.getText());
                    password = String.valueOf(editTextPassword.getText());

                    RestController restController = new RestController();
                    String tokenId = restController.loginUser(mContext, email, password);
                    if (!tokenId.equals("")) {
                        final SharedPreferences sharedSettings = getActivity().
                                getSharedPreferences(getActivity().getClass().
                                        getSimpleName(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedSettings.edit();
                        editor.putBoolean(STATE_CHK_REMEMBER, chkRemember.isChecked());
                        editor.putString(STATE_USER_EMAIL, editTextEmail.getText().toString());
                        editor.putString(STATE_USER_PASSWORD, editTextPassword.getText().toString());
                        editor.apply();
                    } else{
                        Toast.makeText(mContext, "ERROR", Toast.LENGTH_LONG);
                    }
                }
            }
        });

        if (savedInstanceState != null) {
            editTextEmail.setText(savedInstanceState.getString(STATE_USER_EMAIL));
            editTextPassword.setText(savedInstanceState.getString(STATE_USER_PASSWORD));
        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
