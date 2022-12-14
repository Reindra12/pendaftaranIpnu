package com.example.pendaftaran.Camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.LifecycleOwner;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.pendaftaran.R;
import com.example.pendaftaran.Surat.SuratActivity;

import java.io.File;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    TextureView view_finder;
    ImageButton imgCapture;
    String nomorsurat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        view_finder = findViewById(R.id.view_finder);
        imgCapture = findViewById(R.id.imgcapture);
        Intent intent = getIntent();
        nomorsurat = intent.getStringExtra("nosurat");
        Toast.makeText(this, ""+nomorsurat, Toast.LENGTH_SHORT).show();
        startCamera();
    }

    public void startCamera() {

        CameraX.unbindAll();

        Rational aspecRatio = new Rational(view_finder.getWidth(), view_finder.getHeight());
        Size screen = new Size(view_finder.getWidth(), view_finder.getHeight());
        PreviewConfig pConfig = new PreviewConfig.Builder().setTargetAspectRatio(aspecRatio).setTargetResolution(screen).build();
        Preview preview = new Preview(pConfig);

        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput output) {
                ViewGroup parent = (ViewGroup) view_finder.getParent();
                parent.removeView(view_finder);
                parent.addView(view_finder, 0);

//                code untuk slelalu merefresh camera tampilan
                view_finder.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();
            }
        });

        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();

        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);


        imgCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = "photo";
                File imageFile = null;
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

//                File file = new File(Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".jpg");
                try {
                    imageFile = File.createTempFile(filename, ".jpg", storageDirectory);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                long timestamp = System.currentTimeMillis();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, timestamp);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

                imgCap.takePicture(imageFile, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        String msg = "hasil jepret woy " + file.getAbsolutePath();
                        Toast.makeText(CameraActivity.this, msg, Toast.LENGTH_SHORT).show();

//                        untuk kirim file ke activity baru
                        Intent intent = new Intent(CameraActivity.this, ShowPhotoActivity.class);
//                        Intent intent = new Intent(CameraActivity.this, SuratActivity.class);
//                        intent.putExtra("intent", "2");
                        intent.putExtra("path", file.getAbsoluteFile() + "");
                        intent.putExtra("nosurat", nomorsurat);
                        startActivity(intent);
                        finish();
                    }


                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        String msg = "hasil jepret gagal " + message;
                        Toast.makeText(CameraActivity.this, msg, Toast.LENGTH_SHORT).show();

                        if (cause != null) {
                            cause.printStackTrace();
                        }
                    }
                });
            }
        });

//        butuh bind ke lifecycle
        CameraX.bindToLifecycle((LifecycleOwner) this, preview, imgCap);
    }

    public void updateTransform() {

        Matrix mx = new Matrix();
        float w = view_finder.getMeasuredWidth();
        float h = view_finder.getMeasuredHeight();

        float cx = w / 2f;
        float cy = h / 2f;

        int rotationDgr = 90;
        int rotation = (int) view_finder.getRotation();

        switch (rotation) {
            case Surface
                    .ROTATION_0:
                rotationDgr = 0;
                break;
            case Surface
                    .ROTATION_90:
                rotationDgr = 90;
                break;
            case Surface
                    .ROTATION_180:
                rotationDgr = 180;
                break;
            case Surface
                    .ROTATION_270:
                rotationDgr = 270;
                break;
            default:
                return;
        }
        mx.postRotate((float) rotationDgr, cx, cy);

        view_finder.setTransform(mx);
    }
}