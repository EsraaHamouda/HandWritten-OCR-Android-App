package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class MyCameraActivity extends AppCompatActivity {


    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_camera);
        this.imageView = (ImageView) this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            Bitmap photo = null;
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
            }


            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
            if (!textRecognizer.isOperational()) {
                Toast.makeText(getApplicationContext(), "Couldn't get text", Toast.LENGTH_LONG).show();

            } else {
                if (photo != null) {
                    Frame frame = new Frame.Builder().setBitmap(photo).build();
                    SparseArray<TextBlock> items = textRecognizer.detect(frame);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < items.size(); i++) {
                        TextBlock item = items.valueAt(i);
                        stringBuilder.append(item.getValue());
                        stringBuilder.append("\n");
                    }

                    if (stringBuilder.toString().isEmpty())
                        ((TextView) findViewById(R.id.tv)).setText("Cannot extract text");

                    else
                        ((TextView) findViewById(R.id.tv)).setText("The Result : " + stringBuilder.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
