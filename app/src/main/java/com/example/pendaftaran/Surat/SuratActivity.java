package com.example.pendaftaran.Surat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.pendaftaran.Camera.CameraActivity;
import com.example.pendaftaran.Camera.ShowPhotoActivity;
import com.example.pendaftaran.R;
import com.example.pendaftaran.Services.Preferences;
import com.example.pendaftaran.Services.Service;
import com.example.pendaftaran.dashboard.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SuratActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner penerima, sifat, perihal;
    String spinpenerima, spinsifat, spinperihal, spinidpenerima, spinidsifat, spinidperihal, snomor, stgl, spengirim, sisi,
            path, statusintent, iduser, namauser;
    EditText nomorsurat, tglsurat, pengirim, isi;

    SuratSpinnerAdapter suratSpinnerAdapter;
    ArrayList<String> dataCabangSpinner = new ArrayList<>();
    ArrayList<String> dataSifatSuratSpinner = new ArrayList<>();
    ArrayList<String> dataPerihalSuratSpinner = new ArrayList<>();

    ArrayList<String> dataidCabangSpinner = new ArrayList<>();
    ArrayList<String> dataidSifatSuratSpinner = new ArrayList<>();
    ArrayList<String> dataidPerihalSuratSpinner = new ArrayList<>();
    Bitmap bitmapsurat;

    ImageView gambarsurat;
    private int REQUEST_CODE_PERMISSIONS = 101;
    private static final int PICK_IMAGE_REQUEST = 102;

    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
    };

    private SharedPreferences mPrefs;
    Preferences preferences;


    String tanggalsekarang = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surat);

//        if (savedInstanceState != null){
//            nomorsurat.setText(savedInstanceState.getString("nosurat"));
//            isi.setText(savedInstanceState.getString("isi"));
//        }
        AndroidNetworking.initialize(getApplicationContext());
        tampilDataSpinner();

        preferences = new Preferences(this);

        nomorsurat = findViewById(R.id.etnomorsurat);
        tglsurat = findViewById(R.id.ettglsurat);
        pengirim = findViewById(R.id.etpengirim);
        isi = findViewById(R.id.et_isi);

        penerima = findViewById(R.id.spinnerpenerima);
        sifat = findViewById(R.id.spinnersifatsurat);
        perihal = findViewById(R.id.spinnerperihalsurat);
        AppCompatButton kirim = findViewById(R.id.btnkirim);
        gambarsurat = findViewById(R.id.img_surat);


        gambarsurat.setOnClickListener(this);
        kirim.setOnClickListener(this);
        tglsurat.setText(tanggalsekarang);

        semuaSpinner();
        namauser = preferences.getNamauser();
        iduser = preferences.getIduser();

        pengirim.setText(namauser);

        mPrefs = getSharedPreferences("surat", MODE_PRIVATE);

        if (allpermissionGranted()) {
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }


//        statusintent = getIntent().getStringExtra("intent");
//        if (statusintent.equals("1")) {
//            gambarsurat.setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_24));
//        } else if (statusintent.equals("2")) {
//            path = getIntent().getExtras().getString("path");
//            File imgfile = new File(path);
//
//
//            nomorsurat.setText(mPrefs.getString("nosurat", ""));
//            pengirim.setText(mPrefs.getString("pengirim", ""));
//            isi.setText(mPrefs.getString("isi", ""));
//
//
//            if (imgfile.exists()) {
//                Bitmap mybitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
//
////            untuk android baru atau keatas os 10
//                gambarsurat.setImageBitmap(rotateBitmap(mybitmap, 90));
//            }
//        }

    }

    private void semuaSpinner() {
        perihal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinperihal = sifat.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sifat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinsifat = sifat.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        penerima.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinpenerima = penerima.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void tampilDataSpinner() {

        AndroidNetworking.get(Service.URL + Service.spinsurat)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray arrayCabang = response.getJSONArray("cabang");
                            JSONArray arraySifatSurat = response.getJSONArray("sifat");
                            JSONArray arrayPerihalSurat = response.getJSONArray("perihal");

                            dataSifatSuratSpinner.clear();
                            dataCabangSpinner.clear();
                            dataPerihalSuratSpinner.clear();

                            dataidSifatSuratSpinner.clear();
                            dataidCabangSpinner.clear();
                            dataidPerihalSuratSpinner.clear();

                            for (int i = 0; i < arrayCabang.length(); i++) {
                                String namaCabang = arrayCabang.getJSONObject(i).getString("nama_cabang");
                                String idcabang = arrayCabang.getJSONObject(i).getString("cabang_id");

                                dataidCabangSpinner.add(idcabang);
                                dataCabangSpinner.add(namaCabang);
                            }
                            for (int j = 0; j < arraySifatSurat.length(); j++) {
                                String sifatsurat = arraySifatSurat.getJSONObject(j).getString("nama_sifat");
                                String id = arraySifatSurat.getJSONObject(j).getString("sifat_id");
                                dataidSifatSuratSpinner.add(id);
                                dataSifatSuratSpinner.add(sifatsurat);

                            }
                            for (int k = 0; k < arrayPerihalSurat.length(); k++) {
                                String perihalsurat = arrayPerihalSurat.getJSONObject(k).getString("nama_perihal");
                                String id = arrayPerihalSurat.getJSONObject(k).getString("perihal_id");
                                dataidPerihalSuratSpinner.add(id);
                                dataPerihalSuratSpinner.add(perihalsurat);
                            }

                            cabangSpinnertoAdapter();
                            sifatSpinnertoAdapter();
                            perihalSpinnertoAdapter();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SuratActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(SuratActivity.this, "" + anError, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void perihalSpinnertoAdapter() {
        suratSpinnerAdapter = new SuratSpinnerAdapter(SuratActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        suratSpinnerAdapter.addAll(dataPerihalSuratSpinner);

        suratSpinnerAdapter.add("Pilih perihal Surat");
        perihal.setAdapter(suratSpinnerAdapter);
        perihal.setSelection(suratSpinnerAdapter.getCount());
    }

    private void sifatSpinnertoAdapter() {
        suratSpinnerAdapter = new SuratSpinnerAdapter(SuratActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        suratSpinnerAdapter.addAll(dataSifatSuratSpinner);

        suratSpinnerAdapter.add("Pilih Sifat Surat");
        sifat.setAdapter(suratSpinnerAdapter);
        sifat.setSelection(suratSpinnerAdapter.getCount());
    }

    private void cabangSpinnertoAdapter() {
        suratSpinnerAdapter = new SuratSpinnerAdapter(SuratActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        suratSpinnerAdapter.addAll(dataCabangSpinner);

        suratSpinnerAdapter.add("Pilih Penerima Surat");
        penerima.setAdapter(suratSpinnerAdapter);
        penerima.setSelection(suratSpinnerAdapter.getCount());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnkirim:
                if (cekLengkapData()){
                    uploadSurat();
                }
                break;
            case R.id.img_surat:
                pilihfoto();

        }
    }



    private boolean allpermissionGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
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
                bitmapsurat = BitmapFactory.decodeFile(path);
                RotateImage(bitmapsurat);
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
                bitmapsurat = rotateImage(mybitmap, 90);
//                rel_add.setVisibility(View.GONE);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                bitmapsurat = rotateImage(mybitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                bitmapsurat = rotateImage(mybitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                bitmapsurat = mybitmap;
        }
        gambarsurat.setImageBitmap(bitmapsurat);


    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private boolean cekLengkapData() {

        if (nomorsurat.getText().toString().trim().isEmpty()) {
            nomorsurat.setError("Nomor surat tidak boleh kosong");
            nomorsurat.requestFocus();
        } else if (tglsurat.getText().toString().trim().isEmpty()) {
            tglsurat.setError("Tanggal Surat tidak boleh kosong");
            tglsurat.requestFocus();
        } else if (pengirim.getText().toString().trim().isEmpty()) {
            pengirim.setError("Pengirim Surat tidak boleh kosong");
            pengirim.requestFocus();
        } else if (isi.getText().toString().trim().isEmpty()) {
            isi.setError("isi Surat tidak boleh kosong");
            isi.requestFocus();
        } else if (spinpenerima.equalsIgnoreCase("Pilih Penerima Surat")) {
            Toast.makeText(this, "Pilih Penerima terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else if (spinperihal.equalsIgnoreCase("Pilih Perihal Surat")) {
            Toast.makeText(this, "Pilih perihal Surat terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else if (spinsifat.equalsIgnoreCase("Pilih Sifat Surat")) {
            Toast.makeText(this, "Pilih Sifat Surat terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            snomor = nomorsurat.getText().toString();
            stgl = tglsurat.getText().toString();
            spengirim = pengirim.getText().toString();
            sisi = pengirim.getText().toString();

            spinidpenerima = dataidCabangSpinner.get(penerima.getSelectedItemPosition());
            spinidperihal = dataidPerihalSuratSpinner.get(perihal.getSelectedItemPosition());
            spinidsifat = dataidSifatSuratSpinner.get(sifat.getSelectedItemPosition());

            return true;
        }
        return false;
    }
    private File persistImage(Bitmap bitmap, String name) {
        File filesDir = getApplicationContext().getFilesDir();
        if (name == null) {
            Toast.makeText(SuratActivity.this, "Silahkan Pilih Foto Terlebih Dahulu", Toast.LENGTH_SHORT).show();
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
    private void uploadSurat() {
//        Toast.makeText(this, "proses", Toast.LENGTH_SHORT).show();
        File file = persistImage(bitmapsurat, "surat");
        AndroidNetworking.upload(Service.URL+Service.suratkeluar)
                .addMultipartParameter("no_surat",snomor)
                .addMultipartParameter("tgl_keluar",stgl)
                .addMultipartParameter("user_id", iduser)
                .addMultipartParameter("cabang_id",spinidpenerima)
                .addMultipartParameter("sifat_id", spinsifat)
                .addMultipartParameter("perihal_id", spinidperihal)
                .addMultipartParameter("isi", sisi)
                .addMultipartParameter("status_keluar","2")
                .addMultipartFile("berkas_keluar",file)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(SuratActivity.this, "Surat keluar Berhasil ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SuratActivity.this, DashboardActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(SuratActivity.this, ""+anError, Toast.LENGTH_SHORT).show();
                    }
                });






    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Toast.makeText(this, "onsave", Toast.LENGTH_SHORT).show();
        outState.putString("nosurat", nomorsurat.getText().toString());
        outState.putString("tgl", stgl);
        outState.putString("pengirim", spengirim);
        outState.putString("isi", isi.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "restore", Toast.LENGTH_SHORT).show();
        nomorsurat.setText(savedInstanceState.getString("nosurat"));
        pengirim.setText(savedInstanceState.getString("pengirim"));
        isi.setText(savedInstanceState.getString("isi"));
    }
}