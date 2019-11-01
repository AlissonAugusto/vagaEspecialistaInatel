package com.alisson.zaptel.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alisson.zaptel.R;
import com.alisson.zaptel.models.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddContactDialog extends Dialog implements
        android.view.View.OnClickListener {

    private EditText contactNameDialog;
    private EditText contactEmailDialog;
    private Button buttonOkDialog;
    private Button buttonCancelDialog;

    public AddContactDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_contact_dialog);
        contactNameDialog = (EditText) findViewById(R.id.contactEmailDialog);
        contactEmailDialog = (EditText) findViewById(R.id.contactEmailDialog);
        buttonOkDialog = (Button) findViewById(R.id.buttonOkDialog);
        buttonCancelDialog = (Button) findViewById(R.id.buttonCancelDialog);
        buttonOkDialog.setOnClickListener(this);
        buttonCancelDialog.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonOkDialog:
                if (!contactNameDialog.getText().toString().isEmpty() &&
                        !contactEmailDialog.getText().toString().isEmpty()) {
                    Contact contact = new Contact();
                    contact.setName(contactNameDialog.getText().toString());
                    contact.setEmail(contactEmailDialog.getText().toString());

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String json = sharedPreferences.getString("contacts", "");
                    Type type = new TypeToken<ArrayList<Contact>>() {
                    }.getType();
                    Gson gson = new Gson();
                    ArrayList<Contact> contacts = gson.fromJson(json, type);

                    if (contacts != null) {
                        contacts.add(contact);
                    } else {
                        contacts = new ArrayList<Contact>();
                        contacts.add(contact);
                    }

                    String contactsJSONString = new Gson().toJson(contacts);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("contacts", contactsJSONString);
                    editor.apply();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "It's necessary to fill all information", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                dismiss();
                break;
        }
    }
}