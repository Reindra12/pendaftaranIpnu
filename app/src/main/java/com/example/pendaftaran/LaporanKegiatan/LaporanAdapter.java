package com.example.pendaftaran.LaporanKegiatan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pendaftaran.DetailKegiatan.DetailKegiatanActivity;
import com.example.pendaftaran.LaporanKegiatan.Model.LaporanItem;
import com.example.pendaftaran.R;
import com.example.pendaftaran.Services.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.MyHolder> {

    ArrayList<LaporanItem> laporanItems = new ArrayList<>();
    Context context;
    private ItemDelete mItemDelete;

    public LaporanAdapter(ArrayList<LaporanItem> laporanItems, Context context, ItemDelete itemDelete) {
        this.laporanItems = laporanItems;
        this.context = context;
        this.mItemDelete = itemDelete;
    }

    public void addItemClickListener(ItemDelete listener) {
        mItemDelete = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan, parent, false);
        return new MyHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        LaporanItem laporanItem = laporanItems.get(position);
        holder.nama.setText(laporanItem.getNamaKegiatan());
        holder.tanggal.setText(laporanItem.getTgl());
        holder.tempat.setText(laporanItem.getTempat());
        String keterangan = laporanItem.getKeterangan();
        String idkegiatan = laporanItem.getKegiatanId();

        String cabangid = laporanItem.getCabangId();
        String gambar = Service.URLgambar+Service.gambarkegiatan+laporanItem.getFotoKegiatan();
        if (cabangid.equals("19")){
            holder.cabang.setText("Cabang Kraksaan");

        }

        Picasso.get().load(gambar).into(holder.fotolaporan);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailKegiatanActivity.class);
                intent.putExtra("nama", laporanItem.getNamaKegiatan());
                intent.putExtra("tanggal", laporanItem.getTgl());
                intent.putExtra("tempat", laporanItem.getTempat());
                intent.putExtra("cabang", "Cabang Kraksaan");
                intent.putExtra("gambar", gambar);
                intent.putExtra("keterangan", keterangan);
                context.startActivity(intent);

            }
        });

        holder.linearhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemDelete != null){
                    mItemDelete.onItemDelete(idkegiatan, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return laporanItems.size();
    }
    public interface ItemDelete {
        void onItemDelete(String idkegiatan, int position);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView nama, tanggal, cabang, tempat;
        ImageView fotolaporan;
        CardView cardView;
        LinearLayout linearhapus;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.tvnama);
            tanggal = itemView.findViewById(R.id.tvtanggal);
            cabang = itemView.findViewById(R.id.tvcabang);
            tempat = itemView.findViewById(R.id.tvtempat);
            fotolaporan = itemView.findViewById(R.id.imglaporan);
            cardView = itemView.findViewById(R.id.cvkegiatan);
            linearhapus = itemView.findViewById(R.id.lldelete);


        }
    }






}
