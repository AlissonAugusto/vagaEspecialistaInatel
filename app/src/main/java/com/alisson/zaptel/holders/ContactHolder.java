package com.alisson.zaptel.holders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alisson.zaptel.R;

public class ContactHolder extends RecyclerView.ViewHolder {

    public TextView contactName;
    public TextView contactEmail;
    public ImageButton buttonDeleteContact;

    public ContactHolder(View itemView) {
        super(itemView);
        contactName = (TextView) itemView.findViewById(R.id.contactName);
        contactEmail = (TextView) itemView.findViewById(R.id.contactEmail);
        buttonDeleteContact = (ImageButton) itemView.findViewById(R.id.buttonDeleteContact);
    }
}