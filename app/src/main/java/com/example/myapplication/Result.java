package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setTitle("Scanning Result");
        String res = getIntent().getStringExtra("Result");
        TextView textView = findViewById(R.id.tv);
        textView.setText(res);
    }
}
