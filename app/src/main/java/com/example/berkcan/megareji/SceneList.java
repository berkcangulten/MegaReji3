package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SceneList extends AppCompatActivity {
    ListView ListViewSceneList;
    String wayofSet;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> date;
    ArrayList<String> email;
    ArrayList<String>ids;
    ArrayList<String>wayofSetArray;

    SceneListPostClass customAdapter;

    //Options Menu Initialize
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.add_new_scene,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //If the Options Menu is selected, upload data and start activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_new_scene){
            Intent intent2 =new Intent(getApplicationContext(),EnterSceneName.class);
            intent2.putExtra("id",wayofSet);
            startActivity(intent2);

        }
        return super.onOptionsItemSelected(item);
    }

    //OnCreate method is called at starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_list);
        ListViewSceneList=findViewById(R.id.ListViewSceneList);
        Intent iin= getIntent(); //Information of previous activity
        Bundle b = iin.getExtras();
        email=new ArrayList<String>();
        ids=new ArrayList<String>();
        wayofSetArray=new ArrayList<String>();

        if(b!=null)
        {
            wayofSet =(String) b.get("id"); //This is unique ID of set.
        }
        firebaseDatabase=firebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        customAdapter=new SceneListPostClass(email,ids,wayofSetArray,this);
        ListViewSceneList.setAdapter(customAdapter);
        getDataFromFirebase();

        ListViewSceneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),SceneList.class);
                intent.putExtra("id",ids.get(position).toString());
                intent.putExtra("id_2",wayofSet);


                startActivity(intent);
            }
        });

    }

    public void getDataFromFirebase() {
        DatabaseReference newReference=firebaseDatabase.getReference("scenes").child(wayofSet);
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();

                    email.add(hashMap.get("scenesname"));
                    ids.add(ds.getKey());
                    wayofSetArray.add(wayofSet);
                    customAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;


    }
}
