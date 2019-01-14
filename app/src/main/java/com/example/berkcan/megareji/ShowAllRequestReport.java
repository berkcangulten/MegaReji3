package com.example.berkcan.megareji;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowAllRequestReport extends AppCompatActivity {
    ListView listViewShowAllRequestReport;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> name;

    ArrayList<String>ids;



    ShowAllRequestReportPostClass customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_request_report);
        listViewShowAllRequestReport=findViewById(R.id.listViewShowAllRequestReport);
        name =new ArrayList<String>();
        ids=new ArrayList<String>();
        firebaseDatabase=firebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();

        customAdapter=new ShowAllRequestReportPostClass(name,ids,this);
        listViewShowAllRequestReport.setAdapter(customAdapter);
        getDataFromFirebase();

    }
    public void getDataFromFirebase() {
        DatabaseReference newReference=firebaseDatabase.getReference("requests");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();

                    name.add(hashMap.get("Email"));
                    ids.add(ds.getKey());
                    customAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;


    }
}
