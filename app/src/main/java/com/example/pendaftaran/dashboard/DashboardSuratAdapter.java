package com.example.pendaftaran.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pendaftaran.R;
import com.example.pendaftaran.Services.Service;
import com.example.pendaftaran.dashboard.Model.SuratKeluarItem;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DashboardSuratAdapter extends RecyclerView.Adapter<DashboardSuratAdapter.MyHolder> {
    ArrayList<SuratKeluarItem> suratKeluarItems;
    Context context;
    SuratKeluarItem suratKeluarItem;

    public DashboardSuratAdapter(Context context, ArrayList<SuratKeluarItem> suratKeluarItems) {
        this.context = context;
        this.suratKeluarItems = suratKeluarItems;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listsurat, parent, false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        suratKeluarItem = suratKeluarItems.get(position);

        holder.nomor.setText(suratKeluarItem.getNoSurat());
        holder.tanggal.setText(suratKeluarItem.getTglKeluar());
        holder.perihal.setText(suratKeluarItem.getNamaPerihal());
        holder.sifat.setText(suratKeluarItem.getNamaSifat());
        Picasso.get().load(Service.gambarsurat + suratKeluarItem.getBerkasKeluar())
                .into(holder.gambarsurat);

    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView nomor, tanggal, perihal, sifat;
        ImageView gambarsurat;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nomor = itemView.findViewById(R.id.tvnomor);
            tanggal = itemView.findViewById(R.id.tv_tgl);
            perihal = itemView.findViewById(R.id.tv_perihal);
            sifat = itemView.findViewById(R.id.tv_sifat);
            gambarsurat = itemView.findViewById(R.id.imgsurat);

        }
    }
    @Override
    public int getItemCount() {
        return suratKeluarItems.size();
    }


}
