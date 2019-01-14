package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ShowAllRequestReportEnd extends AppCompatActivity {
    TextView textViewShowAllRequestReportEnd4;
    String ids;
    HashMap<String,String> hashMap;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_request_report_end);
        textViewShowAllRequestReportEnd4=findViewById(R.id.textViewShowAllRequestReportEnd4);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        Intent intent =getIntent();
        ids=intent.getStringExtra("id"); //Unique ID of the clicked actor
        getDataFromFirebase();

    }
    public void getDataFromFirebase(){
        DatabaseReference newReference =firebaseDatabase.getReference("requests");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().toString().equals(ids)){
                        hashMap=  (HashMap<String,String>) ds.getValue();
                        textViewShowAllRequestReportEnd4.setText(hashMap.get("request").toString());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
