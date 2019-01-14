package com.example.berkcan.megareji;

import android.graphics.Bitmap;
import android.net.Uri;

public class Places {

    private static Places instance;



    private Bitmap image;
    private String scene;
    private Uri uri;
    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public static Places getInstance(){
        if(instance==null){
            instance=new Places();
        }
        return instance;
    }
}