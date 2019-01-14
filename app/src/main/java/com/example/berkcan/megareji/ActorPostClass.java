package com.example.berkcan.megareji;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ActorPostClass extends ArrayAdapter<String> {
    private final ArrayList<String> name;

    private final ArrayList<String> id ;
    private final Activity context;

    //Constructor
    public ActorPostClass(ArrayList<String>name,ArrayList<String>id,Activity context){

        super(context,R.layout.customlayout,name);
        this.id=id;
        this.name=name;
        this.context=context;

    }

    //List the actors with customLayout
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View customView=layoutInflater.inflate(R.layout.customlayout,null,true);

        final TextView textView=customView.findViewById(R.id.textViewOfCustomLayout);
        textView.setText(name.get(position));

        //If the actor name is clicked, start Show Actor Activity
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(context.getApplicationContext(),ShowActorEnd.class);
                intent3.putExtra("id",id.get(position));
                context.startActivity(intent3);
            }
        });
return customView;
    }






}
