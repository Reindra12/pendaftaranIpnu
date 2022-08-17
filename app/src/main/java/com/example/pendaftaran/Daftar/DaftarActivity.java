package com.example.pendaftaran.Daftar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.pendaftaran.R;
import com.example.pendaftaran.Services.Service;
import com.example.pendaftaran.login.LoginActivity;

import org.json.JSONObject;

public class DaftarActivity extends AppCompatActivity {

    TextView nama, username, password, alamat;
    TextView daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        nama = findViewById(R.id.et_nama);
        username = findViewById(R.id.et_username);

        password = findViewById(R.id.et_password);
        alamat = findViewById(R.id.et_alamat);

        daftar = findViewById(R.id.tvdaftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datalengkap()){
                    prosesdaftar();
                }
            }
        });

    }

    private void prosesdaftar() {
        AndroidNetworking.post(Service.URL+Service.regis)
                .addBodyParameter("cabang_id", "19")
                .addBodyParameter("nama", nama.getText().toString().trim())
                .addBodyParameter("username", username.getText().toString().trim())
                .addBodyParameter("password", password.getText().toString().trim())
                .addBodyParameter("alamat", alamat.getText().toString().trim())
                .addBodyParameter("level", "2")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(DaftarActivity.this, "Berhasil Daftar", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DaftarActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    private boolean datalengkap() {
        String snama = nama.getText().toString().trim();
        String susername = username.getText().toString().trim();
        String spass = password.getText().toString().trim();
        String salamat = alamat.getText().toString().trim();

        if (snama.equals("")){
            nama.setError("Nama tidak boleh kosong");
            nama.requestFocus();
        }else if (susername.equals("")){
            username.setError("Username tidak boleh kosong");
            username.requestFocus();
        }else if (spass.equals("")){
            password.setError("Password tidak boleh kosong");
            password.requestFocus();
        }else if (salamat.equals("")){
            alamat.setError("Alamat tidak boleh kosong");
            alamat.requestFocus();
        }

        return true;
    }
}