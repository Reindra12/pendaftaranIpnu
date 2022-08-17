package com.example.pendaftaran.DetailSurat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pendaftaran.R;
import com.squareup.picasso.Picasso;

public class DetailSuratActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_surat);


        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView isi = findViewById(R.id.tvisi);
        isi.setText(getIntent().getStringExtra("isi"));

        TextView nomor = findViewById(R.id.tvnomor);
        nomor.setText(getIntent().getStringExtra("nosurat"));

        TextView tanggal = findViewById(R.id.tvtanggal);
        tanggal.setText(getIntent().getStringExtra("tglsurat"));

        TextView sifat = findViewById(R.id.tv_sifat);
        sifat.setText(getIntent().getStringExtra("sifat"));

        TextView perihal = findViewById(R.id.tv_perihal);
        perihal.setText(getIntent().getStringExtra("perihal"));

        ImageView gambarsurat = findViewById(R.id.imgdetailsurat);
        Picasso.get().load(getIntent().getStringExtra("gambarsurat")).into(gambarsurat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}