package com.example.berkcan.megareji;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> useremailFromFB; //Arraylist of user email in Firebase
    PostClass customAdapter; //Adapter for listview
    ListView listviewlast; //Listview

    //OnCreate method is called at starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listviewlast= findViewById(R.id.listviewlast);

        useremailFromFB =new ArrayList<String>();
        firebaseDatabase=firebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();

        customAdapter=new PostClass(useremailFromFB,this); //Create PostClass and send context
        listviewlast.setAdapter(customAdapter);
        getDataFromFirebase();
    }
    //This is a method to get users' e mail
    public void getDataFromFirebase(){
        DatabaseReference newReference=firebaseDatabase.getReference("Users");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue(); //HashMap to get children in Firebase tree
                    useremailFromFB.add(hashMap.get("useremail"));
                    //System.out.println("gulten002"+hashMap.get("useremail"));
                    customAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
