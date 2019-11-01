package com.alisson.zaptel.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alisson.zaptel.R;
import com.alisson.zaptel.dialogs.AddContactDialog;
import com.alisson.zaptel.holders.ContactHolder;
import com.alisson.zaptel.models.Contact;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {

    private final List<Contact> mContacts;

    Context mContext;
    Activity mActivity;

    public ContactAdapter(ArrayList contacts, Context context, Activity activity) {
        mContacts = contacts;
        mContext = context;
        mActivity = activity;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, final int position) {
        String name = "Name: " + mContacts.get(position).getName();
        String email = "Email: " + mContacts.get(position).getEmail();
        holder.contactName.setText(name);
        holder.contactEmail.setText(email);
        holder.buttonDeleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactAdapter.this.removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContacts != null ? mContacts.size() : 0;
    }


    public void updateList(Contact contact) {
        insertItem(contact);
    }

    private void insertItem(Contact contact) {
        mContacts.add(contact);
        notifyItemInserted(getItemCount());
    }

    private void removeItem(final int position) {
        AlertDialog alertDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Warning");
        builder.setMessage("Are you sure you want to delete this user?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                mContacts.remove(position);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                String contactsJSONString = new Gson().toJson(mContacts);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("contacts", contactsJSONString);
                editor.apply();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mContacts.size());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }
}