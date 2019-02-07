
package com.dammy.android.cameraapp;

import android.graphics.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class recordvideo extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    Camera camera = null;
    boolean isrecording = false;
    MediaRecorder recorder;
    Button record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        recorder = new MediaRecorder();
        initrecorder();
        setContentView(R.layout.activity_recordvideo);
        SurfaceView cameraview = (SurfaceView)findViewById(R.id.surfaceview);
        record = findViewById(R.id.record);
        surfaceHolder = cameraview.getHolder();
        surfaceHolder.addCallback(this);
        record.setOnClickListener(this);

    }

    private void initrecorder() {
    recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
    recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

        CamcorderProfile camcorderProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        recorder.setProfile(camcorderProfile);
        recorder.setOutputFile(new File(getFilesDir(),"mymovie-"+ UUID.randomUUID().toString()).getAbsolutePath());

    }

    private void prepareRecorder(){
        recorder.setPreviewDisplay(surfaceHolder.getSurface());


        try{
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
         if(isrecording){
             recorder.stop();
             isrecording = false;
         }
         recorder.release();
         finish();
    }

    @Override
    public void onClick(View v) {

        if(isrecording){
            record.setText("record");
            recorder.stop();
            isrecording = false;
            initrecorder();
            prepareRecorder();
        }else{
            record.setText("Stop");
            isrecording = true;
            recorder.start();
        }
    }
}
