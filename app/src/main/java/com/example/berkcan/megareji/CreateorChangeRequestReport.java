package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.StringJoiner;
import java.util.UUID;

public class CreateorChangeRequestReport extends AppCompatActivity {
    EditText editTextCreateRequestMain;
    TextView textViewChangeorCreateRequest;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    HashMap<String,String> hashMap;
    DatabaseReference myRef;
     // String mail;
    //String lastmail;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.save_request,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save_request){
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
        setContentView(R.layout.activity_createor_change_request_report);
        editTextCreateRequestMain=findViewById(R.id.editTextCreateRequestMain);
        textViewChangeorCreateRequest=findViewById(R.id.textViewChangeorCreateRequest);
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
      //  mail=mAuth.getCurrentUser().getEmail().toString();
      //  String[] parts = mail.split("@");
      //  String part1 = parts[0];
       // String part2 = parts[1];
      //  lastmail = String.join("?" +
      //          "", parts);
        getDataFromFirebase();

    }
    public void upload(){

        myRef.child("requests").child( mAuth.getCurrentUser().getUid().toString()).child("Email").setValue(mAuth.getCurrentUser().getEmail().toString());
        myRef.child("requests").child( mAuth.getCurrentUser().getUid().toString()).child("request").setValue(editTextCreateRequestMain.getText().toString() );

    }

    public void getDataFromFirebase(){

        DatabaseReference newReference=firebaseDatabase.getReference("requests");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();
                    if(ds.getKey().toString().equals(mAuth.getCurrentUser().getUid().toString())){
                        hashMap=  (HashMap<String,String>) ds.getValue();
                        editTextCreateRequestMain.setText(hashMap.get("request").toString());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
