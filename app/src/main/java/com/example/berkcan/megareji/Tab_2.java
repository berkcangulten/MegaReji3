package com.example.berkcan.megareji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Tab_2 extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> namesurname;
    ArrayList<String> phoneActor;
    ArrayList<String>Url;
    ArrayList<String>idsActor;


    TActorPostClass customAdapter;
    ListView listViewTab2;
    String wayofSet;
    String wayofScene;
    ArrayList<String> ActorID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);

        listViewTab2=rootView.findViewById(R.id.listViewTab2);
        namesurname =new ArrayList<String>();
        phoneActor=new ArrayList<String>();
        Url=new ArrayList<String>();
        idsActor=new ArrayList<String>();
        ActorID=new ArrayList<String>();

        firebaseDatabase=firebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();

        customAdapter=new TActorPostClass(namesurname,Url,phoneActor,idsActor,getActivity());
        listViewTab2.setAdapter(customAdapter);
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
        final int[] counter = {0};
        DatabaseReference newReference =firebaseDatabase.getReference("scenes").child(wayofSet).child(wayofScene).child("actor");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();

                    ActorID.add(hashMap.get("actorID"));
                    counter[0]++;

                }
                for(int i=0;i<counter[0];i++) {
                    DatabaseReference newReference1 = firebaseDatabase.getReference("actors").child(ActorID.get(i));
                    newReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {

                            HashMap<String, String> hashMap2 = (HashMap<String, String>) dataSnapshot2.getValue();
                            namesurname.add(hashMap2.get("Name") + " " + hashMap2.get("Surname"));
                            phoneActor.add(hashMap2.get("Phone"));
                            Url.add(hashMap2.get("Url"));
                            idsActor.add(dataSnapshot2.getKey());
                            customAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                counter[0]=0;












            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}