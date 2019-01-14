package com.example.berkcan.megareji;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TActorPostClass extends ArrayAdapter<String> {

    private final ArrayList<String> namesurname;
    private final ArrayList<String> imageurl;
    private final ArrayList<String> phone;
    private final ArrayList<String> id ;
    private final Activity context;

    public TActorPostClass(ArrayList<String>namesurname,ArrayList<String> imageurl,ArrayList<String> phone,ArrayList<String>id,Activity context){

        super(context,R.layout.actor_customlayout,namesurname);
        this.id=id;
        this.namesurname=namesurname;
        this.imageurl=imageurl;
        this.phone=phone;
        this.context=context;

    }
    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View customView=layoutInflater.inflate(R.layout.actor_customlayout,null,true);
       final TextView textViewTNameSurnameActor=customView.findViewById(R.id.textViewTNameSurnameActor);
        final TextView textViewTPhoneActor =customView.findViewById(R.id.textViewTPhoneActor);
        final ImageView imageViewTActor=customView.findViewById(R.id.imageViewTActor);
        textViewTNameSurnameActor.setText(namesurname.get(position));
        textViewTPhoneActor.setText(phone.get(position));
        Picasso.get().load(imageurl.get(position)).into(imageViewTActor);




        return customView;
    }

}


