package com.example.pendaftaran.DetailKegiatan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pendaftaran.R;
import com.squareup.picasso.Picasso;

public class DetailKegiatanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kegiatan);


        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView isi = findViewById(R.id.tvisi);
        isi.setText(getIntent().getStringExtra("keterangan"));

        TextView nomor = findViewById(R.id.tvnama);
        nomor.setText(getIntent().getStringExtra("nama"));

        TextView tanggal = findViewById(R.id.tvtanggal);
        tanggal.setText(getIntent().getStringExtra("tanggal"));

        TextView sifat = findViewById(R.id.tvalamat);
        sifat.setText(getIntent().getStringExtra("tempat"));

        TextView perihal = findViewById(R.id.tvcabang);
        perihal.setText(getIntent().getStringExtra("cabang"));

        ImageView gambarsurat = findViewById(R.id.imgkegiatan);
        Picasso.get().load(getIntent().getStringExtra("gambar")).into(gambarsurat);

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