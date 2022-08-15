package com.example.pendaftaran.Anggota;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pendaftaran.Anggota.Model.AnggotaItem;
import com.example.pendaftaran.R;

import java.util.ArrayList;


public class AnggotaAdapter extends RecyclerView.Adapter<AnggotaAdapter.MyHolder> {


    ArrayList<AnggotaItem> anggotaItems = new ArrayList<>();
    Context context;


    public AnggotaAdapter(ArrayList<AnggotaItem> anggotaItems, Context context) {
        this.anggotaItems = anggotaItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anggota, parent, false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        AnggotaItem anggotaItem = anggotaItems.get(position);
        holder.nama.setText(anggotaItem.getNama());
        holder.alamat.setText(anggotaItem.getAlamat());
        holder.kode.setText(anggotaItem.getUserId());

        String levelanggota = anggotaItem.getUserId();
        if (levelanggota.equals("1")){
            holder.level.setText("Admin Cabang");

        }else{
            holder.level.setText("Anak Cabang");

        }


    }

    @Override
    public int getItemCount() {
        return anggotaItems.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView nama, kode, alamat, level;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.tvnama);
            kode = itemView.findViewById(R.id.tvkodeanggota);
            alamat = itemView.findViewById(R.id.tvalamat);
            level = itemView.findViewById(R.id.tvlevel);
        }
    }
}
