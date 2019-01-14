package com.example.berkcan.megareji;

import android.Manifest;
import android.app.Activity;
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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

public class EnterPlace extends AppCompatActivity {

    EditText textEnterPlace;
    ImageView imageViewEnterPlace;
    Bitmap ChoosenImage;
    Uri uri; //Path of image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_place);
        textEnterPlace=findViewById(R.id.textEnterPlace);
        imageViewEnterPlace=findViewById(R.id.imageViewEnterPlace);

    }

    //OnClick image and get permission
    public void selectpicture(View view){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2); //If cannot get permission, permission code is 2

        }
        else{
            Intent intent= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1); //If get permission, permission code is 1
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null){
             uri=data.getData();
            try {
                ChoosenImage=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                imageViewEnterPlace.setImageBitmap(ChoosenImage);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    //OnClick next button
    public void next(View view){
        Places placesClass=Places.getInstance();
        String scene=textEnterPlace.getText().toString();
        placesClass.setUri(uri);
        placesClass.setScene(scene);
        placesClass.setImage(ChoosenImage);
        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
        startActivity(intent);

    }
}
