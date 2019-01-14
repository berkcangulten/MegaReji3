package com.example.berkcan.megareji;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PostClass extends ArrayAdapter<String> {
    private final ArrayList<String> userEmail;
    private final Activity context;

    //Constructor of PostClass class
    public PostClass(ArrayList<String>userEmail,Activity context){
        super(context,R.layout.customlayout,userEmail);
        this.userEmail=userEmail;
        this.context=context;
    }

    //
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater =context.getLayoutInflater();
        View customView=layoutInflater.inflate(R.layout.customlayout,null,true); //Create custom layout

        final TextView textView= customView.findViewById(R.id.textViewOfCustomLayout); //Set the Text View of customLayout
        textView.setText(userEmail.get(position));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3= new Intent(context.getApplicationContext(), Admin2_Activity.class);
                intent3.putExtra("email",textView.getText()); //Tranfer the information to choosen activities
                context.startActivity(intent3);
            }
        });

        return customView;

    }
}
