package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SendNotification extends AppCompatActivity {

     EditText editTextSubjectSendNotification;
     EditText editTextMessageSendNotification;
     Spinner spinnerSendNotification;
     FirebaseAuth mAuth;
     String selectedspinner;
    ArrayList<String> useremailFromFB;
    ArrayList<String> sendmail;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        useremailFromFB =new ArrayList<String>();
         sendmail=new ArrayList<String>();
        editTextSubjectSendNotification=findViewById(R.id.editTextSubjectSendNotification);
        editTextMessageSendNotification=findViewById(R.id.editTextMessageSendNotification);
        spinnerSendNotification=findViewById(R.id.spinnerSendNotification);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.positions,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSendNotification.setAdapter(adapter);

        spinnerSendNotification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] positionsOfString=getResources().getStringArray(R.array.positions);
                selectedspinner =positionsOfString[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button button_send=findViewById(R.id.button_send);
        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }
    private void sendMail(){
        DatabaseReference newReference=firebaseDatabase.getReference("Users");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();



                    if(hashMap.get("positionofuser").equals(selectedspinner)){
                       sendmail.add(hashMap.get("usermail").toString());


                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        String[] maillist = sendmail.toArray(new String[sendmail.size()]);
        String subject = editTextSubjectSendNotification.getText().toString();
        String message = editTextMessageSendNotification.getText().toString();
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,maillist);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));

    }
}
