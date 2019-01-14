package com.example.berkcan.megareji;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.flags.impl.DataUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
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

public class Tab_3 extends Fragment  {


   TextView textviewTShowPlace;
   ImageView imageViewTShowPlaceEnd;
    TextView textviewLatitudeTShowPlaceEnd;
    TextView textviewLongitudeTShowPlaceEnd;

    String latitudeString;
    String longitudeString;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;
    String wayofSet;
    String wayofScene;
    String PlaceID;
    String imagename;
    String Latitude;
    String Longitude;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
        textviewTShowPlace=rootView.findViewById(R.id.textviewTShowPlace);
        imageViewTShowPlaceEnd=rootView.findViewById(R.id.imageViewTShowPlaceEnd);
        textviewLatitudeTShowPlaceEnd=rootView.findViewById(R.id.textviewLatitudeTShowPlaceEnd);
        textviewLongitudeTShowPlaceEnd=rootView.findViewById(R.id.textviewLongitudeTShowPlaceEnd);

        Intent iin=  getActivity().getIntent();
        Bundle b = iin.getExtras();
        if(b!=null)
        {

            wayofSet =(String) b.get("id_2");
            wayofScene=(String) b.get("id_1");
        }
        getDataFromFirebase();

        return rootView;
    }



    public void getDataFromFirebase(){
        DatabaseReference newReference =firebaseDatabase.getReference("scenes").child(wayofSet).child(wayofScene);
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {




                HashMap<String,String> hashMap=  (HashMap<String,String>) dataSnapshot.getValue();
                PlaceID=hashMap.get("place");
                DatabaseReference newReference1=firebaseDatabase.getReference("places").child(PlaceID).child("Latitude");
                newReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Latitude=(dataSnapshot.getValue().toString());
                        textviewLatitudeTShowPlaceEnd.setText(Latitude);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                DatabaseReference newReference2=firebaseDatabase.getReference("places").child(PlaceID).child("Longitude");
                newReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       Longitude=(dataSnapshot.getValue().toString());
                        textviewLongitudeTShowPlaceEnd.setText(Longitude);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                DatabaseReference newReference3=firebaseDatabase.getReference("places").child(PlaceID).child("Url");
                newReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String Url=dataSnapshot.getValue().toString();
                        Picasso.get().load(Url).into(imageViewTShowPlaceEnd);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}