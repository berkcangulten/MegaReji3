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

public class SSShowActor extends AppCompatActivity {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> name;
    ArrayList<String> surname;
    ArrayList<String>ids;

    ArrayList<String> wayofsetArray;
    ArrayList<String> wayofsceneArray;
    SSShowActorPostClass customAdapter;
    ListView listViewSSShowActor;
    String wayofScene;
    String wayofSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssshow_actor);
        listViewSSShowActor=findViewById(R.id.listViewSSShowActor);
        name =new ArrayList<String>();
        surname=new ArrayList<String>();
        ids=new ArrayList<String>();
        firebaseDatabase=firebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        wayofsetArray= new ArrayList<String>();
        wayofsceneArray= new ArrayList<String>();
        customAdapter=new SSShowActorPostClass(name,ids,wayofsetArray,wayofsceneArray,this);
        listViewSSShowActor.setAdapter(customAdapter);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            wayofSet =(String) b.get("id_2");
            wayofScene=(String) b.get("id_1");
        }
        getDataFromFirebase();

        listViewSSShowActor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),ShowActorEnd.class);
                intent.putExtra("id",ids.get(position).toString());


                startActivity(intent);
            }
        });
    }

    public void getDataFromFirebase() {
        DatabaseReference newReference=firebaseDatabase.getReference("actors");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();

                    name.add(hashMap.get("Name")+" "+hashMap.get("Surname"));
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

