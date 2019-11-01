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
import com.alisson.zaptel.holders.ContactHolder;
import com.alisson.zaptel.models.Contact;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {

    private final List<Contact> mContacts;

    Context mContext;

    public ContactAdapter(ArrayList contacts, Context context) {
        mContacts = contacts;
        mContext = context;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, final int position) {
        holder.contactName.setText(mContacts.get(position).getName());
        holder.contactEmail.setText(mContacts.get(position).getEmail());
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

    private void removeItem(int position) {
        mContacts.remove(position);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String contactsJSONString = new Gson().toJson(mContacts);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("contacts", contactsJSONString);
        editor.apply();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mContacts.size());
    }
}