package com.example.berkcan.megareji;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PlanPostClass extends ArrayAdapter<String> {
    private final ArrayList<String> email;

    private final ArrayList<String> id ;
    private final Activity context;

    public PlanPostClass(ArrayList<String>email,ArrayList<String>id,Activity context){

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
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(context.getApplicationContext(),ShowPlanEnd.class);
                intent3.putExtra("id",id.get(position));
                context.startActivity(intent3);
            }
        });
        return customView;
    }



}
