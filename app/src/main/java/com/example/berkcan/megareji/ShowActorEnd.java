package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
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

public class ShowActorEnd extends AppCompatActivity {

    TextView textViewnamesurnameshowactorend;
    TextView textViewphoneshowactorend;
    TextView textViewageshowactorend;
    TextView textViewheightweightshowactorend;
    ImageView imageViewshowactorend;
    String ids;
    HashMap<String,String> hashMap;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_actor_end);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        textViewnamesurnameshowactorend=findViewById(R.id.textViewnamesurnameSSshowactorend);
        textViewphoneshowactorend=findViewById(R.id.textViewphoneshowSSactorend);
        textViewageshowactorend=findViewById(R.id.textViewageSSshowactorend);
        textViewheightweightshowactorend=findViewById(R.id.textViewheightweightSSshowactorend);
        imageViewshowactorend=findViewById(R.id.imageViewSSshowactorend);
        Intent intent =getIntent();
        ids=intent.getStringExtra("id"); //Unique ID of the clicked actor
        getDataFromFirebase();
    }

    public void getDataFromFirebase(){
        DatabaseReference newReference =firebaseDatabase.getReference("actors");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().toString().equals(ids)){
                        hashMap=  (HashMap<String,String>) ds.getValue();

                        textViewnamesurnameshowactorend.setText(hashMap.get("Name").toString()+" "+hashMap.get("Surname").toString());
                        textViewphoneshowactorend.setText("Phone: "+hashMap.get("Phone").toString());
                        textViewageshowactorend.setText("Age: "+hashMap.get("Age"));
                        textViewheightweightshowactorend.setText("Weight: "+hashMap.get("Weight")+" "+"Height:"+hashMap.get("Height"));

                        Picasso.get().load(hashMap.get("Url")).into(imageViewshowactorend); //Show image of clicked actor with Picasso library
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
