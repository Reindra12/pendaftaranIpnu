package com.example.pendaftaran.LaporanKegiatan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.pendaftaran.LaporanKegiatan.Model.LaporanItem;
import com.example.pendaftaran.R;
import com.example.pendaftaran.Services.Preferences;
import com.example.pendaftaran.Services.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LaporanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaporanFragment extends Fragment {

    RecyclerView recyclerViewlaporan;
    String iduser;
    Preferences preferences;
    ArrayList<LaporanItem> laporanItems = new ArrayList<>();
    TextView jumlahkeluar;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LaporanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LaporanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LaporanFragment newInstance(String param1, String param2) {
        LaporanFragment fragment = new LaporanFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_laporan, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewlaporan = view.findViewById(R.id.rvlaporan);

        jumlahkeluar = view.findViewById(R.id.tvjumlahlaporan);
        preferences = new Preferences(getContext());
        iduser = preferences.getIduser();

        dataLaporan();
        jumlahlaporan();

    }

    private void jumlahlaporan() {
        AndroidNetworking.get(Service.URL+Service.jumlahlaporan+iduser)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String jumlah = response.getString("laporankeluar");
                            jumlahkeluar.setText(jumlah);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void dataLaporan() {
        AndroidNetworking.get(Service.URL+Service.listlaporan+iduser)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("laporan");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                LaporanItem laporanItem = new LaporanItem();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                laporanItem.setNamaKegiatan(jsonObject.getString("nama_kegiatan"));
                                laporanItem.setTgl(jsonObject.getString("tgl"));
                                laporanItem.setFotoKegiatan(jsonObject.getString("foto_kegiatan"));
                                laporanItem.setTempat(jsonObject.getString("tempat"));
                                laporanItem.setCabangId(jsonObject.getString("cabang_id"));
                                laporanItem.setFotoKegiatan(jsonObject.getString("foto_kegiatan"));
                                laporanItems.add(laporanItem);

                            }

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            LaporanAdapter laporanAdapter = new LaporanAdapter(laporanItems, getContext());
                            recyclerViewlaporan.setLayoutManager(layoutManager);
                            recyclerViewlaporan.setAdapter(laporanAdapter);
                            
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}