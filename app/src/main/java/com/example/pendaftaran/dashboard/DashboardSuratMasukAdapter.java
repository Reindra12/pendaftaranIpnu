package com.example.pendaftaran.dashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pendaftaran.DetailSurat.DetailSuratActivity;
import com.example.pendaftaran.R;
import com.example.pendaftaran.Services.Service;
import com.example.pendaftaran.dashboard.Model.SuratKeluarItem;
import com.example.pendaftaran.dashboard.Model.suratmasuk.SuratMasukItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DashboardSuratMasukAdapter extends RecyclerView.Adapter<DashboardSuratMasukAdapter.MyHolder> {
    ArrayList<SuratMasukItem> suratMasukItems;
    Context context;
    SuratMasukItem suratMasukItem;

    public DashboardSuratMasukAdapter(Context context, ArrayList<SuratMasukItem> suratMasukItems) {
        this.context = context;
        this.suratMasukItems = suratMasukItems;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listsurat, parent, false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        suratMasukItem = suratMasukItems.get(position);

        holder.nomor.setText(suratMasukItem.getNoSurat());
        holder.tanggal.setText(suratMasukItem.getTglMasuk());
        holder.perihal.setText(suratMasukItem.getNamaPerihal());
        holder.sifat.setText(suratMasukItem.getNamaSifat());
        String foto = Service.URLgambar+Service.gambarsurat+suratMasukItem.getBerkas();
        String isi = suratMasukItem.getIsi();
        Picasso.get().load(foto)
                .into(holder.gambarsurat);

        holder.cardViewsurat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailSuratActivity.class);
                intent.putExtra("nosurat", suratMasukItem.getNoSurat());
                intent.putExtra("tglsurat", suratMasukItem.getTglMasuk());
                intent.putExtra("perihal", suratMasukItem.getNamaPerihal());
                intent.putExtra("sifat", suratMasukItem.getNamaSifat());
                intent.putExtra("nosurat", suratMasukItem.getNoSurat());
                intent.putExtra("gambarsurat", foto);
                intent.putExtra("isi", isi);
                context.startActivity(intent);

            }

        });

    }


    public class MyHolder extends RecyclerView.ViewHolder {
        TextView nomor, tanggal, perihal, sifat;
        ImageView gambarsurat;
        CardView cardViewsurat;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nomor = itemView.findViewById(R.id.tvnomor);
            tanggal = itemView.findViewById(R.id.tv_tgl);
            perihal = itemView.findViewById(R.id.tv_perihal);
            sifat = itemView.findViewById(R.id.tv_sifat);
            gambarsurat = itemView.findViewById(R.id.imgsurat);
            cardViewsurat = itemView.findViewById(R.id.cvbarang);

        }
    }
    @Override
    public int getItemCount() {
        return suratMasukItems.size();
    }


}
