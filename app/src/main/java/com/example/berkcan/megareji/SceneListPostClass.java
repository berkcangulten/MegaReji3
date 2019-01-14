package com.example.berkcan.megareji;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

    public class SceneListPostClass extends ArrayAdapter<String> {
        private final ArrayList<String> email;
        private final ArrayList<String> id ;
        private final Activity context;
        private final ArrayList<String> wayofSetArray;

        public SceneListPostClass(ArrayList<String>email,ArrayList<String>id,ArrayList<String> wayofSetArray,Activity context){

            super(context,R.layout.customlayout,email);
            this.id=id;
            this.email=email;
            this.wayofSetArray=wayofSetArray;
            this.context=context;

        }
        //List the scenes with customLayout
        public View getView(final int position, View convertView, ViewGroup parent){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            View customView=layoutInflater.inflate(R.layout.customlayout,null,true);

            final TextView textView=customView.findViewById(R.id.textViewOfCustomLayout);
            textView.setText(email.get(position));

            //If the set name is clicked, start Last Secene and Set Activity
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent3 = new Intent(context.getApplicationContext(),LastSetandScene.class);//////
                   intent3.putExtra("id",id.get(position)); //Send unique Scene Id to next activity
                   intent3.putExtra("id_2",wayofSetArray.get(position)); //Send unique Set Id to next activity
                   context.startActivity(intent3);
                }
            });
            return customView;
        }



    }

