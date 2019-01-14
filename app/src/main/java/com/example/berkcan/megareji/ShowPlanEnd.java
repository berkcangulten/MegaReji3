package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ShowPlanEnd extends AppCompatActivity {

    TextView textViewShowPlanEndComment;
    CalendarView calendarViewShowPlanEnd;
    String ids;
    HashMap<String,String> hashMap;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plan_end);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        textViewShowPlanEndComment=findViewById(R.id.textViewShowPlanEndComment);
        calendarViewShowPlanEnd=findViewById(R.id.calendarViewShowPlanEnd);
        Intent intent =getIntent();
        ids=intent.getStringExtra("id");
        getDataFromFirebase();
    }



    public void getDataFromFirebase(){
        DatabaseReference newReference =firebaseDatabase.getReference("plans");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    if(ds.getKey().toString().equals(ids)){
                        hashMap=  (HashMap<String,String>) ds.getValue();

                     //   SimpleDateFormat formatter = new SimpleDateFormat("ddMMMyyyy");
                     //   Date date = (Date)formatter.parse(hashMap.get("date").toString().replaceAll("/",""), Locale.ENGLISH);
                     //   long mills = date.getTime();

                      //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMMyyyy");
                      //  LocalDate dt = LocalDate.parse(hashMap.get("date").toString().replaceAll("/",""), formatter);

                        String date = hashMap.get("date").toString();
                        String parts[] = date.split("/");

                        int day = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]);
                        int year = Integer.parseInt(parts[2]);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);

                        long milliTime = calendar.getTimeInMillis();


                        calendarViewShowPlanEnd.setDate(milliTime, true, true);
                        textViewShowPlanEndComment.setText(hashMap.get("comment").toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

