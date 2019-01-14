package com.example.berkcan.megareji;

import android.graphics.Bitmap;
import android.net.Uri;

public class Actors {
    private static Actors instance;
    private Bitmap image;
    private String name ;
    private String surname;
    private String height;
    private String weight;
    private String phone;
    private String age;
    private Uri uri;

    //Getters and Setters for Actors
    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public static Actors getInstance(){
        if(instance==null){
            instance=new Actors();
        }
        return instance;
    }
}
