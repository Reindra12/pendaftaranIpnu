package com.example.pendaftaran.LaporanKegiatan;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.util.Calendar;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.pendaftaran.R;
import com.example.pendaftaran.Services.Preferences;
import com.example.pendaftaran.Services.Service;
import com.example.pendaftaran.Surat.SuratActivity;
import com.example.pendaftaran.dashboard.DashboardActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class LaporanKegiatanActivity extends AppCompatActivity {

    EditText nama, tanggal, tempat, keterangan;
    Button kirim;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String iduser, path;
    Preferences preferences;
    ImageView fotolaporan;
    Bitmap bitmap;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private static final int PICK_IMAGE_REQUEST = 102;

    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_kegiatan);

        nama = findViewById(R.id.etnama);
        tanggal = findViewById(R.id.ettanggal);
        tempat = findViewById(R.id.ettempat);
        kirim = findViewById(R.id.btnkirim);
        fotolaporan = findViewById(R.id.img_laporan);
        keterangan = findViewById(R.id.etketerangan);

        preferences = new Preferences(this);
        iduser = preferences.getIduser();

        if (allpermissionGranted()) {
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }


        tanggal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                menampilkantanggal();
            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (periksadata()) {
                    kirimkeapi();
                }
            }
        });

        fotolaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pilihfoto();
            }
        });


    }

    private boolean allpermissionGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void menampilkantanggal() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                tanggal.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    private void kirimkeapi() {
        File file = persistImage(bitmap, "kegiatan");

        AndroidNetworking.upload(Service.URL + Service.laporan)
                .addMultipartParameter("nama_kegiatan", nama.getText().toString().trim())
                .addMultipartParameter("tempat", tempat.getText().toString().trim())
                .addMultipartParameter("tgl", tanggal.getText().toString().trim())
                .addMultipartParameter("user_id", iduser)
                .addMultipartParameter("cabang_id", "19")
                .addMultipartParameter("keterangan", keterangan.getText().toString())
                .addMultipartFile("foto_kegiatan", file)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(LaporanKegiatanActivity.this, "Terkirim", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LaporanKegiatanActivity.this, DashboardActivity.class));
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

    private boolean periksadata() {
        String snama = nama.getText().toString().trim();
        String stgl = tanggal.getText().toString().trim();
        String stempat = tempat.getText().toString().trim();
        String sketerangan = keterangan.getText().toString().trim();

        if (snama.equals("")) {
            nama.setError("Nama Kegiatan tidak boleh kosong");
            nama.requestFocus();
        } else if (stgl.equals("")) {
            tanggal.setError("Tanggal kegiatan tidak boleh kosong");
            nama.requestFocus();
        } else if (stempat.equals("")) {
            tempat.setError("Tempat kegiatan tidak boleh kosong");
            tempat.requestFocus();
        } else if (sketerangan.equals("")) {
            tempat.setError("Tempat kegiatan tidak boleh kosong");
            tempat.requestFocus();
        }

        return true;
    }

    private void pilihfoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Pilih gambar"), PICK_IMAGE_REQUEST);

    }

    public String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID
                + " = ? ", new String[]{document_id}, null);

        cursor.moveToFirst();
        @SuppressLint("Range")
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            try {

                Uri uri = data.getData();
                path = getRealPathFromUri(getApplicationContext(), uri);

// ===== tampil foto
                bitmap = BitmapFactory.decodeFile(path);
                RotateImage(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void RotateImage(Bitmap mybitmap) {

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                bitmap = rotateImage(mybitmap, 90);
//                rel_add.setVisibility(View.GONE);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                bitmap = rotateImage(mybitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                bitmap = rotateImage(mybitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                bitmap = mybitmap;
        }
        fotolaporan.setImageBitmap(bitmap);


    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = getApplicationContext().getFilesDir();
        if (name == null) {
            Toast.makeText(LaporanKegiatanActivity.this, "Silahkan Pilih Foto Terlebih Dahulu", Toast.LENGTH_SHORT).show();
        }
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }

        return imageFile;
    }
}