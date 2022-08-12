package com.example.pendaftaran.Camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pendaftaran.R;
import com.example.pendaftaran.Surat.SuratActivity;

import java.io.File;

public class ShowPhotoActivity extends AppCompatActivity {
    ImageView imageView;
    Button btn_edit;

    String path = "";
    String nosurat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);

        imageView = findViewById(R.id.imgview);
        btn_edit = findViewById(R.id.btn_edit);

        Intent intent = getIntent();
        nosurat = intent.getStringExtra("nosurat");
        Toast.makeText(this, ""+nosurat, Toast.LENGTH_SHORT).show();

        path = getIntent().getExtras().getString("path");
        File imgfile = new File(path);

        if (imgfile.exists()) {
            Bitmap mybitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());

//            untuk android baru atau keatas os 10
            imageView.setImageBitmap(rotateBitmap(mybitmap, 90));
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // buka file di lain activity fitur edit

                Intent intent = new Intent(ShowPhotoActivity.this, SuratActivity.class);
                intent.putExtra("path", path);
                intent.putExtra("intent", "2");
                intent.putExtra("nosurat", nosurat);

                startActivity(intent);
//                finish();
            }
        });

    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);


    }
}