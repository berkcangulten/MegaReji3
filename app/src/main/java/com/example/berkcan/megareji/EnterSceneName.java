package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.flags.impl.DataUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.UUID;

public class EnterSceneName extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;
    EditText editTextEnterSceneName;
    String wayofSet;
    String lastofScenes;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.save_scene_name,menu);
              return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save_scene_name){
            upload();
            Intent intent2 =new Intent(getApplicationContext(),SceneList.class);
            intent2.putExtra("id",wayofSet);
            startActivity(intent2);


        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_scene_name);
        editTextEnterSceneName=findViewById(R.id.editTextEnterSceneName);

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        Intent iin= getIntent();
        Bundle b = iin.getExtras();if(b!=null)
        {
            wayofSet =(String) b.get("id");
        }
    }


    public void upload(){
        UUID uuid1=UUID.randomUUID();
        myRef.child("scenes").child(wayofSet).child(uuid1.toString()).child("scenesname").setValue(editTextEnterSceneName.getText().toString());
        myRef.child("scenes").child(wayofSet).child(uuid1.toString()).child("scenario").setValue("-1");
        myRef.child("scenes").child(wayofSet).child(uuid1.toString()).child("place").setValue("-1");


    }
}
