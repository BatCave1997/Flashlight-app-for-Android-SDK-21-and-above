package com.example.acer.torch;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean hasFlash;
    boolean isFlashOn = false ;
    ImageView i;
    CameraManager cameraManager;
    String camerId;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        i = findViewById(R.id.iv);

        hasFlash = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if(!hasFlash){
            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setTitle("Error")
                    .setMessage("Sorry, your device doesn't support camera flash")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }

        GetCamera();

    }

    public void OnOffButton(View view){

        if(isFlashOn){
            TurnOffFlash();
            i.setImageResource(R.drawable.off);
        }else{
            TurnOnFlash();
            i.setImageResource(R.drawable.on);
        }
    }

    private void GetCamera(){
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            camerId = cameraManager.getCameraIdList()[0];
        }catch(CameraAccessException e){
            e.printStackTrace();
        }
    }

    void TurnOnFlash()  {
        try {
            cameraManager.setTorchMode(camerId,true);
            isFlashOn = true;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    void TurnOffFlash(){
        try {
            cameraManager.setTorchMode(camerId, false);
            isFlashOn = false;
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


}
