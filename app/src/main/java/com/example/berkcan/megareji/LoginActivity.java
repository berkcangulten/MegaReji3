package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; //Firebase Authentication
    EditText emailText; //Editable text field for email
    EditText passwordText; //Editable text field for password
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef; //Reference for database access in Firebase
    StorageReference mStorageRef; //Reference for storage in Firebase
    boolean[] accessableMenu; //Boolean array for menu according to user role
    int score=0; //For gamification

    //OnCreate method is called at starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //Get Activity Login design
        mAuth=FirebaseAuth.getInstance(); //Get Authentication from Firebase
        emailText = findViewById(R.id.emailText); //Access to email text in xml file
        passwordText = findViewById(R.id.passwordText); //Access to password text in xml file
        firebaseDatabase =FirebaseDatabase.getInstance(); //Get Databse from Firebase
        myRef=firebaseDatabase.getReference(); //Get reference from Firebase
        mStorageRef= FirebaseStorage.getInstance().getReference(); //Get storage from Firebase
        accessableMenu=new boolean[16]; //Create an accessable menu array with 16 elements
    }
    //This is Sign in OnClick Method
    public void signIn(View view){
        //Get email and password from user
        mAuth.signInWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Checks authentication
                        if(task.isSuccessful()){
                            final String email=emailText.getText().toString();
                            final String Uid=mAuth.getCurrentUser().getUid().toString();
                            final String[] position = new String[1];
                            DatabaseReference newReference =firebaseDatabase.getReference("Users");
newReference.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot ds: dataSnapshot.getChildren()){
            if(ds.getKey().toString().equals(Uid)){
                HashMap<String,String> hashMap=  (HashMap<String,String>) ds.getValue();
                position[0] =hashMap.get("positionofuser");
            }
        }

        if(position[0].equals("unapproved") ){
            UpdatePoint UpdatePoint2 =new UpdatePoint();
             UpdatePoint2.ChangeValue(10);
            Intent intent2 = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent2);
        }
        //It is an Admin
        else if(email.equals("berkcang_82@hotmail.com")){
            Intent intent1 = new Intent(getApplicationContext(), AdminActivity.class);
            startActivity(intent1);
        }
        //This user can see every feature in application
        else if(email.equals("superuser@super.com")){

            Intent intent1 = new Intent(getApplicationContext(), MainMenuActivity.class);
            startActivity(intent1);
        }
        //Not approved users
        else {
            UpdatePoint UpdatePoint2 =new UpdatePoint();
            UpdatePoint2.ChangeValue(10);
            Intent intent2 = new Intent(getApplicationContext(), MainMenuActivity.class);
            startActivity(intent2);
        }


    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});




                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) { //If the login process is unsuccessful
                Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show(); //Show toast to user which error occured
            }
        });
    }
    //This is Sign Up OnClick Method
    public void signUp(View view){
        //Get email and password from user
        mAuth.createUserWithEmailAndPassword(emailText.getText().toString(),passwordText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Checks authentication
                        if(task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            String email= user.getEmail();
                            String uid=user.getUid();
                            String positionOfUser="unapproved"; //Undetermined role of person

                            UUID uuid1=UUID.randomUUID(); //Generates a random number to create unique id number
                            String uuidString=uuid1.toString();

                            //Creates a user with unique id number
                            myRef.child("Users").child(uid).child("useremail").setValue(email);
                            myRef.child("Users").child(uid).child("uid").setValue(uid);
                            myRef.child("Users").child(uid).child("positionofuser").setValue(positionOfUser);
                            myRef.child("Users").child(uid).child("accessablemenu").setValue(Arrays.toString(accessableMenu));
                            myRef.child("Users").child(uid).child("score").setValue(score);

                            //Checks and direct the user to admin activity class if email is berkcang_82@hotmail.com
                            if(email.equals("berkcang_82@hotmail.com")){
                                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                                startActivity(intent);
                            }
                            //Toasts a message and directs to Profilie Activity class
                            else {
                                Toast.makeText(LoginActivity.this, "User Created!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) { //If the sign up process is unsuccessful
                Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show(); //Show toast to user which error occured
            }
        });
    }
}
