package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class WriteScenario extends AppCompatActivity {
    EditText editTextWriteScenarioStageName;
    EditText editTextWriteScenarioMain;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;

    DatabaseReference myRef;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.save_scenario,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.save_scenario){
            upload();
            UpdatePoint UpdatePoint2 =new UpdatePoint();
            UpdatePoint2.ChangeValue(20);
            Intent intent2= new Intent(getApplicationContext(),ShowScenario.class);
            startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_scenario);
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mAuth=FirebaseAuth.getInstance();

        editTextWriteScenarioMain= findViewById(R.id.editTextWriteScenarioMain);
        editTextWriteScenarioStageName=findViewById(R.id.editTextWriteScenarioStageName);

    }
    public void upload(){
        UUID uuid1=UUID.randomUUID();
        myRef.child("scenarios").child(uuid1.toString()).child("Email").setValue(mAuth.getCurrentUser().getEmail().toString());
        myRef.child("scenarios").child(uuid1.toString()).child("stage").setValue(editTextWriteScenarioStageName.getText().toString());
        myRef.child("scenarios").child(uuid1.toString()).child("mainscenario").setValue(editTextWriteScenarioMain.getText().toString());

    }
}
