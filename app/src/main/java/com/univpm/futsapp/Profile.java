package com.univpm.futsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.univpm.futsapp.Main.MainActivity;
import com.univpm.futsapp.utilities.data.DataLoad;
import com.univpm.futsapp.utilities.data.DataSave;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Profile extends AppCompatActivity {
    private static final int GALLERY_REQUEST_CODE = 123;
    ImageView imageView;
    Button buttonimage;


    private DataSave SaveImage=new DataSave();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        imageView = findViewById(R.id.profilePic);
        buttonimage = findViewById(R.id.buttonimage);
        try {MainActivity.LoadImage.LoadImage(MainActivity.username,imageView); } catch (IOException e) {
            e.printStackTrace();
        }
        buttonimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick an image"), GALLERY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageData = data.getData();
            imageView.setImageURI(imageData);
            SaveImage.saveImage(imageData,Profile.this);
        }
    }


}