package com.example.berkcan.megareji;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LeaderBoard extends AppCompatActivity {

    ListView listviewLeaderBoard;
    LinkedHashMap<String, Integer> sorted;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> useremailFromFB;
    Object[] a;
    ArrayList<String>ids;
    LeaderPostClass customAdapter;
    HashMap<String,Integer> MapofLeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        listviewLeaderBoard=findViewById(R.id.listviewLeaderBoard);

        MapofLeader=new HashMap<String,Integer>();
        useremailFromFB =new ArrayList<String>();
        ids=new ArrayList<String>();
        firebaseDatabase=firebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        sorted = new LinkedHashMap<>();




        getDataFromFirebase();
       // sortMap();
       // applyChanges();
      //  System.out.println("berkcan");

       customAdapter=new LeaderPostClass(useremailFromFB,ids,this);
        listviewLeaderBoard.setAdapter(customAdapter);
    }

    public void getDataFromFirebase(){
        final int[] foo = new int[1];
        final String[] cache = new String[1];
        DatabaseReference newReference=firebaseDatabase.getReference("Users");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,Object> hashMap= (HashMap<String,Object> )ds.getValue();
                    cache[0] =hashMap.get("score").toString();
                     foo[0] =Integer.parseInt(cache[0]);
                    MapofLeader.put(hashMap.get("useremail").toString(), foo[0]);

          //          customAdapter.notifyDataSetChanged();
                } sortMap();
                applyChanges();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void sortMap(){

        List<String> mapKeys = new ArrayList<>(MapofLeader.keySet());
        List<Integer> mapValues = new ArrayList<>(MapofLeader.values());
        Collections.sort(mapValues);
        Collections.reverse(mapValues);
        Collections.sort(mapKeys);
        Collections.reverse(mapKeys);


        LinkedHashMap<String, Integer> sortedMap =
                new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Integer comp1 = MapofLeader.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();

                    sortedMap.put(key, val);
                    break;
                }
            }
        }

            // TreeMap to store values of HashMap


            // Copy all data from hashMap into TreeMap
            sorted.putAll(sortedMap);




        }
    public void applyChanges(){

        for (Map.Entry<String, Integer> entry : sorted.entrySet()) {




                useremailFromFB.add(((entry.getKey()) + " \nScore: " + entry.getValue()));


    }
        customAdapter.notifyDataSetChanged();
    }

}
