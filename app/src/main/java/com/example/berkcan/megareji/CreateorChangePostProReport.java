package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CreateorChangePostProReport extends AppCompatActivity {

    EditText editTextCreatePostProNeed;
    EditText editTextCreatePostProDone;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    HashMap<String,String> hashMap;
    DatabaseReference myRef;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.save_postpro,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save_postpro){
            upload();
            UpdatePoint UpdatePoint2 =new UpdatePoint();
            UpdatePoint2.ChangeValue(20);
            Intent intent2= new Intent(getApplicationContext(),MainMenuActivity.class);
            startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createor_change_post_pro_report);
        editTextCreatePostProNeed=findViewById(R.id.editTextCreatePostProNeed);
        editTextCreatePostProDone=findViewById(R.id.editTextCreatePostProDone);
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        getDataFromFirebase();

    }

    public void upload(){

        myRef.child("postpro").child( mAuth.getCurrentUser().getUid().toString()).child("Email").setValue(mAuth.getCurrentUser().getEmail().toString());
        myRef.child("postpro").child( mAuth.getCurrentUser().getUid().toString()).child("need").setValue(editTextCreatePostProNeed.getText().toString() );
        myRef.child("postpro").child( mAuth.getCurrentUser().getUid().toString()).child("done").setValue(editTextCreatePostProDone.getText().toString() );






    }
    public void getDataFromFirebase(){

        DatabaseReference newReference=firebaseDatabase.getReference("postpro");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();
                    if(ds.getKey().toString().equals(mAuth.getCurrentUser().getUid().toString())){
                        hashMap=  (HashMap<String,String>) ds.getValue();
                        editTextCreatePostProNeed.setText(hashMap.get("need").toString());
                        editTextCreatePostProDone.setText(hashMap.get("done").toString());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
