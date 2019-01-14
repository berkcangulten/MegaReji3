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

public class EnterSetName extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;

    EditText editTextEnterSetName;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.save_set_name,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save_set_name){
            upload();
            Intent intent2 =new Intent(getApplicationContext(),EnterSetList.class);
            startActivity(intent2);


        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_set_name);
        editTextEnterSetName=findViewById(R.id.editTextEnterSetName);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();

    }
    public void upload(){
        UUID uuid1=UUID.randomUUID();
        myRef.child("sets").child(uuid1.toString()).child("Name").setValue(editTextEnterSetName.getText().toString());


    }
}
