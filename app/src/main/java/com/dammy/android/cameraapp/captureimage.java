package com.dammy.android.cameraapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class captureimage extends AppCompatActivity {
    static final int IMAGE_CAPTURE_REQUEST = 1;
    ImageView imageView;
    Button takepicture;
    String currentphotopath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captureimage);

        takepicture = findViewById(R.id.captureimage);

        takepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(captureimage.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(captureimage.this,new String[]{Manifest.permission.CAMERA},1);
                }
                else {
                    dispatchTakePictureIntent();


                }
            }
        });



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //do stuffs permission granted
                    dispatchTakePictureIntent();

                }
                else{
                    //permissions denied
                }
        }

    }



    private void dispatchTakePictureIntent(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent takepictureintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takepictureintent.resolveActivity(getPackageManager()) != null){
            File photofile = null;
            try{
                photofile = createImagefile();
            }
            catch (IOException E){
                E.printStackTrace();
            }
                takepictureintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photofile));
                startActivityForResult(takepictureintent,IMAGE_CAPTURE_REQUEST);

        }
    }

    private File createImagefile() throws IOException {

        File storagedir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile("myimage",".jpg",storagedir);
        currentphotopath = image.getAbsolutePath();
        return image;

    }
    private void galleryAddPic(){
       Intent mediaScanIntent =  new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
       File f = new File(currentphotopath);
       Uri contenturi = Uri.fromFile(f);
       mediaScanIntent.setData(contenturi);
       this.sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CAPTURE_REQUEST) {

            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(photo);
            galleryAddPic();




        }

    }

}
