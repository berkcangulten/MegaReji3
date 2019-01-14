package com.example.berkcan.megareji;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    TextView userProfileEmail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth=FirebaseAuth.getInstance();
        userProfileEmail=findViewById(R.id.userProfileEmail);
        userProfileEmail.setText("Email: "+mAuth.getCurrentUser().getEmail().toString());
    }
}
