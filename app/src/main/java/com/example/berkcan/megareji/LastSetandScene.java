package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LastSetandScene extends AppCompatActivity {
    String wayofScene;
    String wayofSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_setand_scene);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            wayofSet =(String) b.get("id_2"); //Unique ID of way of set
            wayofScene=(String) b.get("id"); //Unique ID of way of scene
        }
    }
    //When onClicked Scene Add or Change button, send information and start SS Show Place activity
    public void setSceneAddorChange(View view){
        Intent intent=new Intent(getApplicationContext(),SSShowPlace.class);
        intent.putExtra("id",wayofScene);
        intent.putExtra("id_2",wayofSet);

        startActivity(intent);
    }
    //When onClicked Scene Show button, send information and start Scroll Set and Scene activity
    public void  setSceneShowPlan(View view){
        Intent intent=new Intent(getApplicationContext(), ScrollSetAndScene.class);
        intent.putExtra("id_1",wayofScene);
        intent.putExtra("id_2",wayofSet);
        startActivity(intent);
    }

}
