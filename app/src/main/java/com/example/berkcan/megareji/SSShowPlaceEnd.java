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

import java.util.HashMap;


public class SSShowPlaceEnd extends AppCompatActivity {
    TextView textviewSSSceneShowPlace;

    TextView textviewLatitudeSSShowPlaceEnd;
    TextView textviewLongitudeSSShowPlaceEnd;
    ImageView imageViewSSShowPlaceEnd;
    String ids;
    HashMap<String,String> hashMap;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;
    String wayofSet;
    String wayofScene;


    //Options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.sssave_place,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //When a menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sssave_place){
            upload();
            Intent intent2 =new Intent(getApplicationContext(),SSShowScenario.class);
            intent2.putExtra("id_1",wayofScene); //Send it to SS Show Scenario
            intent2.putExtra("id_2",wayofSet);
            startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    //OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssshow_place_end);

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();



        textviewSSSceneShowPlace=findViewById(R.id.textviewTShowPlace);
        textviewLatitudeSSShowPlaceEnd=findViewById(R.id.textviewLatitudeTShowPlaceEnd);
        textviewLongitudeSSShowPlaceEnd=findViewById(R.id.textviewLongitudeTShowPlaceEnd);
        imageViewSSShowPlaceEnd=findViewById(R.id.imageViewTShowPlaceEnd);

        //Get information from previous activity
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
    //Get place information from Firebase
    public void getDataFromFirebase(){
        DatabaseReference newReference =firebaseDatabase.getReference("places");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().toString().equals(ids)){
                        hashMap=  (HashMap<String,String>) ds.getValue();

                        textviewSSSceneShowPlace.setText("Scene: "+hashMap.get("Scene").toString());
                        textviewLatitudeSSShowPlaceEnd.setText("Latitude: "+hashMap.get("Latitude").toString());
                        textviewLongitudeSSShowPlaceEnd.setText("Longitude: "+hashMap.get("Longitude"));
                        Picasso.get().load(hashMap.get("Url")).into(imageViewSSShowPlaceEnd);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void upload(){
        myRef.child("scenes").child(wayofSet).child(wayofScene).child("place").setValue(ids);
    }
}