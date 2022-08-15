package com.example.pendaftaran.LaporanKegiatan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pendaftaran.LaporanKegiatan.Model.LaporanItem;
import com.example.pendaftaran.R;

import java.util.ArrayList;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.MyHolder> {

    ArrayList<LaporanItem> laporanItems = new ArrayList<>();
    Context context;

    public LaporanAdapter(ArrayList<LaporanItem> laporanItems, Context context) {
        this.laporanItems = laporanItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        LaporanItem laporanItem = laporanItems.get(position);
        holder.nama.setText(laporanItem.getNamaKegiatan());
        holder.tanggal.setText(laporanItem.getTgl());
        holder.tempat.setText(laporanItem.getTempat());
        holder.cabang.setText(laporanItem.getCabangId());

    }

    @Override
    public int getItemCount() {
        return laporanItems.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView nama, tanggal, cabang, tempat;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.tvnama);
            tanggal = itemView.findViewById(R.id.tvtanggal);
            cabang = itemView.findViewById(R.id.tvcabang);
            tempat = itemView.findViewById(R.id.tvtempat);


        }
    }
}
