package com.example.pendaftaran.login;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.pendaftaran.Daftar.DaftarActivity;
import com.example.pendaftaran.R;
import com.example.pendaftaran.Services.Preferences;
import com.example.pendaftaran.Services.Service;
import com.example.pendaftaran.dashboard.DashboardActivity;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    Snackbar snackbar;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = new Preferences(this);
        setStatusBarGradiant(LoginActivity.this);
        getSupportActionBar().hide();
        TextView login = findViewById(R.id.tvlogin);
        password = findViewById(R.id.et_password);
        username = findViewById(R.id.et_username);

        TextView daftar = findViewById(R.id.tvdaftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, DaftarActivity.class);
                startActivity(intent);
            }
        });

        if (preferences.getStatusLogin()) {
            this.finish();
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvlogin:
                cekdata(v);
        }
    }

    private void cekdata(View v) {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        if (user.equals("") || pass.equals("")) {
            snackbar = Snackbar
                    .make(v, "Lengkapi data terlebih dahulu", Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {
            prosesLogin(v, user, pass);
        }
    }

    private void prosesLogin(View v, String user, String pass) {

        AndroidNetworking.post(Service.URL + Service.login)
                .addBodyParameter("level", "2")
                .addBodyParameter("username", user)
                .addBodyParameter("password", pass)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.get("msg").equals("data ditemukan")) {

                                String iduser = response.getJSONObject("data").getString("user_id");
                                String nama = response.getJSONObject("data").getString("nama");
                                preferences.saveBoolean(Preferences.statuslogin, true);
                                preferences.saveString(Preferences.iduser, iduser);
                                preferences.saveString(Preferences.namauser, nama);


                                snackbar = Snackbar
                                        .make(v, "Berhasil Login"+iduser, Snackbar.LENGTH_LONG);
                                snackbar.show();
                                Log.d(TAG, "onResponse: "+iduser);

                                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                finish();
                            } else {
                                snackbar = Snackbar
                                        .make(v, "Gagal Login \n silahkan cek data anda", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(LoginActivity.this, "" + anError, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}