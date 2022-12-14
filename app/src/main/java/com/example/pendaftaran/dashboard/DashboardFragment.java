package com.example.pendaftaran.dashboard;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.pendaftaran.R;
import com.example.pendaftaran.Services.Preferences;
import com.example.pendaftaran.Services.Service;
import com.example.pendaftaran.Surat.SuratActivity;
import com.example.pendaftaran.dashboard.Model.SuratKeluarItem;
import com.example.pendaftaran.dashboard.Model.suratmasuk.SuratMasukItem;
import com.example.pendaftaran.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    int[] sampleImages = {R.drawable.a, R.drawable.b, R.drawable.c};
    CarouselView banner;
    FloatingActionButton pesanbaru;
    RecyclerView recyclerViewsurat, recyclerViewTerima;
    String iduser;
    Preferences preferences;
    ArrayList<SuratKeluarItem> suratKeluarItems = new ArrayList<>();
    ArrayList<SuratMasukItem> suratMasukItems = new ArrayList<>();
    DashboardSuratAdapter dashboardSuratAdapter;
    TextView jumlahsuratkirim, jumlahsuratterima, nama, keluar;


    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        banner = view.findViewById(R.id.banner);
        pesanbaru = view.findViewById(R.id.fab);
        recyclerViewsurat = view.findViewById(R.id.rvsurat);
        jumlahsuratkirim = view.findViewById(R.id.tvjumlahterkirim);
        jumlahsuratterima = view.findViewById(R.id.tvjumlahterima);
        nama = view.findViewById(R.id.tvnama);
        keluar = view.findViewById(R.id.tvkeluar);
        recyclerViewTerima  = view.findViewById(R.id.rvditerima);

        preferences = new Preferences(getActivity());

        pesanbaru.setOnClickListener(this);
//        banner.setPageCount(sampleImages.length);
        banner.setViewListener(viewListener);
        banner.setSlideInterval(3000);


        keluar.setOnClickListener(this);
        nama.setText(preferences.getNamauser());
        iduser = preferences.getIduser();


        prosesListSurat();
        prosesJumlahSurat();
        prosesSuratDiterima();

    }

    private void prosesSuratDiterima() {
        AndroidNetworking.get(Service.URL + Service.suratmasuk+iduser)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "terima: " + response);

                        try {
                            JSONArray jsonArray = response.getJSONArray("surat_masuk");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                SuratMasukItem suratMasukItem = new SuratMasukItem();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                suratMasukItem.setNoSurat(jsonObject.getString("no_surat"));
                                suratMasukItem.setNamaPerihal(jsonObject.getString("nama_perihal"));
                                suratMasukItem.setNamaSifat(jsonObject.getString("nama_sifat"));
                                suratMasukItem.setNamaCabang(jsonObject.getString("nama_cabang"));
                                suratMasukItem.setTglMasuk(jsonObject.getString("tgl_masuk"));
                                suratMasukItem.setBerkas(jsonObject.getString("berkas"));
                                suratMasukItem.setIsi(jsonObject.getString("isi"));


                                suratMasukItems.add(suratMasukItem);



                            }
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerViewTerima.setLayoutManager(layoutManager);

                            DashboardSuratMasukAdapter dashboardSuratAdapter = new DashboardSuratMasukAdapter(getContext(), suratMasukItems);
                            recyclerViewTerima.setAdapter(dashboardSuratAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void prosesJumlahSurat() {
        AndroidNetworking.get(Service.URL + Service.totalsurat+iduser)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String keluar = response.getString("hitungkeluar");
                            String masuk = response.getString("hitungmasuk");

                            jumlahsuratkirim.setText(keluar);
                            jumlahsuratterima.setText(masuk);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void prosesListSurat() {
        AndroidNetworking.get(Service.URL + Service.listsuratkeluar + iduser)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, "surat: " + response);

                        try {
                            JSONArray jsonArray = response.getJSONArray("surat_keluar");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                SuratKeluarItem suratKeluarItem = new SuratKeluarItem();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                suratKeluarItem.setNoSurat(jsonObject.getString("no_surat"));
                                suratKeluarItem.setNamaPerihal(jsonObject.getString("nama_perihal"));
                                suratKeluarItem.setNamaSifat(jsonObject.getString("nama_sifat"));
                                suratKeluarItem.setNamaCabang(jsonObject.getString("nama_cabang"));
                                suratKeluarItem.setTglKeluar(jsonObject.getString("tgl_keluar"));
                                suratKeluarItem.setBerkasKeluar(jsonObject.getString("berkas_keluar"));
                                suratKeluarItem.setIsi(jsonObject.getString("isi"));


                                suratKeluarItems.add(suratKeluarItem);




                            }
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerViewsurat.setLayoutManager(layoutManager);

                            dashboardSuratAdapter = new DashboardSuratAdapter(getContext(), suratKeluarItems);
                            recyclerViewsurat.setAdapter(dashboardSuratAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {

            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);

            ImageView fruitImageView = (ImageView) customView.findViewById(R.id.fruitImageView);

            fruitImageView.setImageResource(sampleImages[position]);

            banner.setIndicatorGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);

            return customView;
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Intent intent = new Intent(getContext(), SuratActivity.class);
                intent.putExtra("intent", "1");
                startActivity(intent);
                break;
            case R.id.tvkeluar:
             dialogKeluar();

        }
    }

    private void dialogKeluar() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Peringatan");
        alertDialog.setIcon(R.drawable.ic_laporan_icon);
        alertDialog.setMessage("Apakah anda mau keluar?")
                .setCancelable(false)
                .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //go to activity
                        Intent intent1 = new Intent(getContext(), LoginActivity.class);
                        preferences.saveString(Preferences.iduser, "");
                        preferences.saveBoolean(Preferences.statuslogin, false);
                        startActivity(intent1);
                        getActivity().finish();
                    }

                });
        alertDialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.create().show();

    }

}