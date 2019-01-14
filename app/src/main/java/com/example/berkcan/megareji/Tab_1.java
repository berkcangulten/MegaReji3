package com.example.berkcan.megareji;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Tab_1 extends Fragment {
    TextView textViewTab_1_SceneName;
    TextView editTextTab_1Scenario;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    String ids;
    DatabaseReference myRef;
    HashMap<String,String> hashMap;
    String wayofSet;
    String wayofScene;
    String ScenarioID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);
        textViewTab_1_SceneName=rootView.findViewById(R.id.textViewTab_1_SceneName);
        editTextTab_1Scenario=rootView.findViewById(R.id.editTextTab_1Scenario);
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();

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
                        ScenarioID=hashMap.get("scenario");
                        DatabaseReference newReference1=firebaseDatabase.getReference("scenarios").child(ScenarioID).child("stage");
                newReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        textViewTab_1_SceneName.setText(dataSnapshot.getValue().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                DatabaseReference newReference2=firebaseDatabase.getReference("scenarios").child(ScenarioID).child("mainscenario");
                newReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        editTextTab_1Scenario.setText(dataSnapshot.getValue().toString());

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
