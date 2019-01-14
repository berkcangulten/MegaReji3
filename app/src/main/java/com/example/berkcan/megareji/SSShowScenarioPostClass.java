package com.example.berkcan.megareji;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class SSShowScenarioPostClass extends ArrayAdapter<String> {
    private final ArrayList<String> email;
    private final ArrayList<String> id ;
    private final Activity context;
    private final ArrayList<String> wayofsetArray;
    private final ArrayList<String> wayofsceneArray;

    //Constructor
    public SSShowScenarioPostClass(ArrayList<String>email,ArrayList<String>id,ArrayList<String> wayofsetArray,ArrayList<String> wayofsceneArray,Activity context){

        super(context,R.layout.customlayout,email);
        this.id=id;
        this.email=email;
        this.wayofsetArray=wayofsetArray;
        this.wayofsceneArray=wayofsceneArray;

        this.context=context;

    }
    //Create customLayout and send next Activity
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View customView=layoutInflater.inflate(R.layout.customlayout,null,true);

        final TextView textView=customView.findViewById(R.id.textViewOfCustomLayout);
        textView.setText(email.get(position));

        //If the set name is clicked, start next activity and send iniformation
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(context.getApplicationContext(),SSShowScenarioEnd.class);
                intent3.putExtra("id",id.get(position));
                intent3.putExtra("id_1",wayofsceneArray.get(position));
                intent3.putExtra("id_2",wayofsetArray.get(position));
                context.startActivity(intent3);
            }
        });
        return customView;
    }

}