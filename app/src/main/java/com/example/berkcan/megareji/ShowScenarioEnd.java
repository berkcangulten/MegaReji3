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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

public class ShowScenarioEnd extends AppCompatActivity {
    EditText editTextWriteScenarioEndStage;
    EditText editTextWriteScenarioEndMain;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    String ids;
    DatabaseReference myRef;
    HashMap<String,String> hashMap;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.save_scenarioend,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save_scenarioend){
            upload();
            Intent intent2= new Intent(getApplicationContext(),ShowScenario.class);
            startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_scenario_end);
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();
        editTextWriteScenarioEndStage=findViewById(R.id.editTextWriteScenarioEndStage);
        editTextWriteScenarioEndMain=findViewById(R.id.editTextWriteScenarioEndMain);

        Intent intent =getIntent();
        ids=intent.getStringExtra("id");
        getDataFromFirebase();
    }

    public void upload(){

        myRef.child("scenarios").child(ids).child("stage").setValue(editTextWriteScenarioEndStage.getText().toString());
        myRef.child("scenarios").child(ids).child("mainscenario").setValue(editTextWriteScenarioEndMain.getText().toString());

    }
    public void getDataFromFirebase(){
        DatabaseReference newReference =firebaseDatabase.getReference("scenarios");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().toString().equals(ids)){
                        hashMap=  (HashMap<String,String>) ds.getValue();
                        editTextWriteScenarioEndStage.setText(hashMap.get("stage").toString());
                        editTextWriteScenarioEndMain.setText(hashMap.get("mainscenario").toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
