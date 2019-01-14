package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SSShowScenario extends AppCompatActivity {



    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> date;
    ArrayList<String> email;
    ArrayList<String>ids;
    ListView listViewSSShowScenario;
    SSShowScenarioPostClass customAdapter;
    String wayofScene;
    String wayofSet;
    ArrayList<String> wayofsetArray;
    ArrayList<String> wayofsceneArray;

    //OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssshow_scenario);
        listViewSSShowScenario=findViewById(R.id.listViewSSShowScenario);
        date=new ArrayList<String>();
        email=new ArrayList<String>();
        ids=new ArrayList<String>();
        wayofsetArray= new ArrayList<String>();
        wayofsceneArray= new ArrayList<String>();
        firebaseDatabase=firebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();

        customAdapter=new SSShowScenarioPostClass(email,ids,wayofsetArray,wayofsceneArray,this);
        listViewSSShowScenario.setAdapter(customAdapter);

        //Get information from previous activity
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            wayofSet =(String) b.get("id_2");
            wayofScene=(String) b.get("id_1");
        }
        getDataFromFirebase();
        listViewSSShowScenario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),SSShowScenarioEnd.class);
                intent.putExtra("id",ids.get(position).toString());
                intent.putExtra("id_1",wayofScene);
                intent.putExtra("id_2",wayofSet);

                startActivity(intent);
            }
        });
    }
    //Get scenario information from Firebase
    public void getDataFromFirebase() {
        DatabaseReference newReference=firebaseDatabase.getReference("scenarios");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();

                    email.add(hashMap.get("stage"));
                    ids.add(ds.getKey());
                    wayofsetArray.add(wayofSet);
                    wayofsceneArray.add(wayofScene);
                    customAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;


    }
}
