package com.example.berkcan.megareji;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderPostClass extends ArrayAdapter<String> {
    private final ArrayList<String> email;

    private final ArrayList<String> id ;
    private final Activity context;

    public LeaderPostClass(ArrayList<String>email,ArrayList<String>id,Activity context){

        super(context,R.layout.customlayout,email);
        this.id=id;
        this.email=email;

        this.context=context;

    }
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View customView=layoutInflater.inflate(R.layout.customlayout,null,true);

        final TextView textView=customView.findViewById(R.id.textViewOfCustomLayout);
        textView.setText(email.get(position));

        return customView;
    }



}
