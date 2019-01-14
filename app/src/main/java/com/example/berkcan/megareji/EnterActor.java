package com.example.berkcan.megareji;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class EnterActor extends AppCompatActivity {

    EditText textNameEnterActor;
    EditText textSurnameEnterActor;
    EditText textPhoneEnterActor;
    EditText textWeightEnterActor;
    EditText textHeightEnterActor;
    EditText textAgeEnterActor;
    ImageView imageViewEnterActor;
    Uri selectedImage; //The path of data
    Uri uri;
    Bitmap ChoosenImage; //The place of holding data
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageRef;
    DatabaseReference myRef;
    String dowloadUrl;

    //Options Menu Initialize
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.save_actor,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //If the Options Menu is selected, upload data and start activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save_actor){
           upload();
            UpdatePoint UpdatePoint2 =new UpdatePoint();
            UpdatePoint2.ChangeValue(20);
            Intent intent2 =new Intent(getApplicationContext(),ShowActor.class);
            startActivity(intent2);
        }

        return super.onOptionsItemSelected(item);
    }
    //OnCreate method is called at starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_actor);
        textNameEnterActor=findViewById(R.id.textNameEnterActor);
        textSurnameEnterActor=findViewById(R.id.textSurnameEnterActor);
        textPhoneEnterActor=findViewById(R.id.textPhoneEnterActor);
        textWeightEnterActor=findViewById(R.id.textWeightEnterActor);
        textHeightEnterActor=findViewById(R.id.textHeightEnterActor);
        textAgeEnterActor=findViewById(R.id.textAgeEnterActor);
        imageViewEnterActor=findViewById(R.id.imageViewEnterActor);

        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();

    }
    public void upload(){

        UUID uuid=UUID.randomUUID();
        final String imagename="actors/"+uuid+".jpg";

        StorageReference storageReference = mStorageRef.child(imagename); //Datapath of image
        storageReference.putFile(selectedImage).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //download url

                StorageReference newReference = FirebaseStorage.getInstance().getReference(imagename);
                newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {


                    }
                });

            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        //Upload the actor with unique id
        UUID uuid1=UUID.randomUUID();
        myRef.child("actors").child(uuid1.toString()).child("Email").setValue(mAuth.getCurrentUser().getEmail().toString());
        myRef.child("actors").child(uuid1.toString()).child("Name").setValue(textNameEnterActor.getText().toString());
        myRef.child("actors").child(uuid1.toString()).child("Surname").setValue(textSurnameEnterActor.getText().toString());
        myRef.child("actors").child(uuid1.toString()).child("Phone").setValue(textPhoneEnterActor.getText().toString());
        myRef.child("actors").child(uuid1.toString()).child("Age").setValue(textAgeEnterActor.getText().toString());
        myRef.child("actors").child(uuid1.toString()).child("Weight").setValue(textWeightEnterActor.getText().toString());
        myRef.child("actors").child(uuid1.toString()).child("Height").setValue(textHeightEnterActor.getText().toString());
        myRef.child("actors").child(uuid1.toString()).child("Url").setValue(selectedImage.toString());
        myRef.child("actors").child(uuid1.toString()).child("Approve").setValue("false");


    }
    //When select picture button is clicked, and get permission
    public void selectpictureenteractor(View view){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},2);

        }
        else{
            Intent intent= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==2){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //Set image view with selected image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null){
            selectedImage=data.getData();
            try {
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                imageViewEnterActor.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        super.onActivityResult(requestCode, resultCode, data);
    }




}
