package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Detactor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detactor);
        getTextFromImage();
    }
    public void getTextFromImage()
    {
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.img);


       // Bitmap  = BitmapFactory.decodeStream(getAssets().open("1024x768.jpg"));
        Bitmap original=((BitmapDrawable)getResources().getDrawable(R.drawable.img)).getBitmap();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        original.compress(Bitmap.CompressFormat.PNG, 10, out);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageBitmap(decoded);
        Log.e("Original   dimensions", original.getWidth()+" "+original.getHeight());
        Log.e("Compressed dimensions", decoded.getWidth()+" "+decoded.getHeight());
        original = decoded = null;


        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if(!textRecognizer.isOperational())
        {
            Toast.makeText(getApplicationContext(), "Couldn't get text", Toast.LENGTH_LONG).show();

        }else
        {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0 ; i<items.size();i++)
            {
                TextBlock item = items.valueAt(i);
                stringBuilder.append(item.getValue());
                stringBuilder.append("\n");
            }
            ((TextView)findViewById(R.id.tv)).setText(stringBuilder.toString());

        }
    }
}
