package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SSShowActorEnd extends AppCompatActivity {


    TextView textViewnamesurnameSSshowactorend;
    TextView textViewphoneshowSSactorend;
    TextView textViewageSSshowactorend;
    TextView textViewheightweightSSshowactorend;
    ImageView imageViewSSshowactorend;
    String ids;
    HashMap<String,String> hashMap;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;
    String wayofSet;
    String wayofScene;
    String actorList;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.sssave_actor,menu); //Menu option to save actor
        menuInflater.inflate(R.menu.complete_ss,menu); //Menu option to save the plan
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.sssave_actor){

            upload();
            Intent intent2= new Intent(getApplicationContext(),SSShowActor.class);
            intent2.putExtra("id_1",wayofScene);
            intent2.putExtra("id_2",wayofSet);
            startActivity(intent2);
        }
        if(item.getItemId()==R.id.complete_ss) {

            upload();
            UpdatePoint UpdatePoint2 =new UpdatePoint();
            UpdatePoint2.ChangeValue(30);
            Intent intent2= new Intent(getApplicationContext(),LastSetandScene.class);
            intent2.putExtra("id",wayofScene);
            intent2.putExtra("id_2",wayofSet);
            startActivity(intent2);
        }
            return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssshow_actor_end);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        textViewnamesurnameSSshowactorend=findViewById(R.id.textViewnamesurnameSSshowactorend);
        textViewphoneshowSSactorend=findViewById(R.id.textViewphoneshowSSactorend);
        textViewageSSshowactorend=findViewById(R.id.textViewageSSshowactorend);
        textViewheightweightSSshowactorend=findViewById(R.id.textViewheightweightSSshowactorend);
        imageViewSSshowactorend=findViewById(R.id.imageViewSSshowactorend);

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {
            ids =(String) b.get("id");
            wayofSet =(String) b.get("id_2");
            wayofScene=(String) b.get("id_1");
        }
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

                        textViewnamesurnameSSshowactorend.setText(hashMap.get("Name").toString()+" "+hashMap.get("Surname").toString());
                        textViewphoneshowSSactorend.setText("Phone: "+hashMap.get("Phone").toString());
                        textViewageSSshowactorend.setText("Age: "+hashMap.get("Age"));
                        textViewheightweightSSshowactorend.setText("Weight: "+hashMap.get("Weight")+" "+"Height:"+hashMap.get("Height"));

                        Picasso.get().load(hashMap.get("Url")).into(imageViewSSshowactorend);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void upload(){

        UUID uuid1=UUID.randomUUID();
        myRef.child("scenes").child(wayofSet).child(wayofScene).child("actor").child(uuid1.toString()).child("actorID").setValue(ids);


    }
}
