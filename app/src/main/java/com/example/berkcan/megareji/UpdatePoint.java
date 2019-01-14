package com.example.berkcan.megareji;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class UpdatePoint {

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    private DatabaseReference myRef;



    public void ChangeValue(final int incrementValue){

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        myRef=firebaseDatabase.getReference();
        final int[] ValueofScore = new int[1];
        final String dsKey;
        final String UidofUser=mAuth.getCurrentUser().getUid().toString();
        int counter=0;
        final DatabaseReference newReference =firebaseDatabase.getReference("Users");
        newReference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for (DataSnapshot ds: dataSnapshot.getChildren()){
                  HashMap<String,String> hashMap=  (HashMap<String,String>) ds.getValue();
                  String MapValueofUid=hashMap.get("uid").toString();

                  if (MapValueofUid.equals(UidofUser)){
                      HashMap<String,Object> hashMap2=  (HashMap<String,Object>) ds.getValue();
                      ValueofScore[0] =Integer.parseInt(hashMap2.get("score").toString());
                      int lastintValue=ValueofScore[0];
                     lastintValue=lastintValue+incrementValue;
                 //     ValueofScore[0] =String.valueOf(lastintValue);
                      myRef.child("Users").child(ds.getKey().toString()).child("score").setValue(lastintValue);
                      newReference.removeEventListener(this);
                      return;
              }

              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

}


}
