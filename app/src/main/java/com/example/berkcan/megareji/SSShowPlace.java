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

public class SSShowPlace extends AppCompatActivity {
    String wayofScene;
    String wayofSet;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> useremailFromFB;
    ArrayList<String> ids;
    SSPlacePostClass customAdapter;
    ListView listViewSSShowPlace;
    ArrayList<String> wayofsetArray;
    ArrayList<String> wayofsceneArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssshow_place);

        listViewSSShowPlace = findViewById(R.id.listViewSSShowPlace);

        useremailFromFB = new ArrayList<String>();
        ids = new ArrayList<String>();
        wayofsetArray= new ArrayList<String>();
        wayofsceneArray= new ArrayList<String>();
        firebaseDatabase = firebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            wayofSet =(String) b.get("id_2");
            wayofScene=(String) b.get("id");
        }

        customAdapter = new SSPlacePostClass(useremailFromFB, ids,wayofsetArray,wayofsceneArray, this);
        listViewSSShowPlace.setAdapter(customAdapter);
        getDataFromFirebase();

        //
        listViewSSShowPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), SSShowPlaceEnd.class);
                intent.putExtra("id", ids.get(position).toString());
                intent.putExtra("id_1",wayofScene);
                intent.putExtra("id_2",wayofSet);


                startActivity(intent);
            }
        });
    }

    //Get information of places
    public void getDataFromFirebase() {
        DatabaseReference newReference = firebaseDatabase.getReference("places");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    useremailFromFB.add(hashMap.get("Email") + "_" + hashMap.get("Scene"));
                    ids.add(ds.getKey());
                    wayofsetArray.add(wayofSet);
                    wayofsceneArray.add(wayofScene);
                    customAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}