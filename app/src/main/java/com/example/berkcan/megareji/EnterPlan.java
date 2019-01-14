package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.UUID;

public class EnterPlan extends AppCompatActivity {
    CalendarView calendarView;
    EditText textViewEnterPlan;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;
    String date;

    //Options Menu Initialize
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.save_plan,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //If the Options Menu is selected, upload data and start activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save_plan){
            upload();
            UpdatePoint UpdatePoint2 =new UpdatePoint();
            UpdatePoint2.ChangeValue(20);
            Intent intent2 =new Intent(getApplicationContext(),ShowPlan.class);
            startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    //OnCreate method is called at starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_plan);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        date="00/00/0000";
        calendarView=findViewById(R.id.calendarView);
        textViewEnterPlan=findViewById(R.id.textViewEnterPlan);

        try {
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                     date= dayOfMonth+"/"+month+"/"+year;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void upload(){
        UUID uuid1=UUID.randomUUID();
        myRef.child("plans").child(uuid1.toString()).child("Email").setValue(mAuth.getCurrentUser().getEmail().toString());
        myRef.child("plans").child(uuid1.toString()).child("date").setValue(date);
        myRef.child("plans").child(uuid1.toString()).child("comment").setValue(textViewEnterPlan.getText().toString());

    }
}
