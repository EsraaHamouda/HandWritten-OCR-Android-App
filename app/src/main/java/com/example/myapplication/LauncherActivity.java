package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class LauncherActivity extends AppCompatActivity {
    public static final int CAMERA_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        GregorianCalendar expDate = new GregorianCalendar(2018, 6, 31); // midnight
        GregorianCalendar now = new GregorianCalendar();


        Button method1 = findViewById(R.id.camera_capture);
        Button method2 = findViewById(R.id.live_scan);
        boolean isExpired = now.after(expDate);
        if (isExpired) {
            method1.setEnabled(false);
            method2.setEnabled(false);

        }


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
        } else {


            method1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MyCameraActivity.class);
                    startActivity(intent);
                }
            });
            method2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LiveScanning.class);
                    startActivity(intent);
                }
            });


        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getApplicationContext(), LiveScanning.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();
                }
        }
    }
}
