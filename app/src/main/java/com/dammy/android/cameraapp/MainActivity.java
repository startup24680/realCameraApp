package com.dammy.android.cameraapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button recordvideo,captureimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recordvideo = findViewById(R.id.shootvideo);
        captureimage = findViewById(R.id.takepicture);

        recordvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, com.dammy.android.cameraapp.recordvideo.class);
                startActivity(intent);
            }
        });
        captureimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,captureimage.class);
                startActivity(intent);
            }
        });
    }
}
