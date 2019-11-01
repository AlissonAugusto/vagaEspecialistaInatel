package com.alisson.zaptel.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alisson.zaptel.R;
import com.alisson.zaptel.adapters.ContactAdapter;
import com.alisson.zaptel.controllers.RestController;
import com.alisson.zaptel.dialogs.AddContactDialog;
import com.alisson.zaptel.models.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {
    private RecyclerView recyclerView;

    private Context mContext;

    private ContactAdapter contactAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public ContactsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts,
                container, false);

        getActivity().setTitle("Contact");
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.contacts_recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        final Gson gson = new Gson();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        String json = sharedPreferences.getString("contacts", "");
        Type type = new TypeToken<ArrayList<Contact>>() {
        }.getType();
        ArrayList<Contact> contacts = gson.fromJson(json, type);
        contactAdapter = new ContactAdapter(contacts, mContext.getApplicationContext());
        recyclerView.setAdapter(contactAdapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContactDialog addContactDialog = new AddContactDialog(mContext);
                addContactDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
                        String json = sharedPreferences.getString("contacts", "");
                        Type type = new TypeToken<ArrayList<Contact>>() {
                        }.getType();
                        ArrayList<Contact> contacts = gson.fromJson(json, type);
                        contactAdapter = new ContactAdapter(contacts, mContext.getApplicationContext());
                        recyclerView.setAdapter(contactAdapter);
                    }
                });
                addContactDialog.show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        menu.clear();
        menu.add("Edit Profile");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                RestController restController = new RestController();
                FragmentManager fragmentManager = getFragmentManager();
                restController.getUser(mContext, fragmentManager);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
