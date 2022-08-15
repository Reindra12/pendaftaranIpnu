package com.example.pendaftaran.Anggota;

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
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.pendaftaran.Anggota.Model.AnggotaItem;
import com.example.pendaftaran.Anggota.Model.ResponseAnggota;
import com.example.pendaftaran.R;
import com.example.pendaftaran.Services.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnggotaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnggotaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerViewanggota;
    TextView nama, kode, alamat, level;

    ArrayList<AnggotaItem> anggotaItems = new ArrayList<>();


    public AnggotaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnggotaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnggotaFragment newInstance(String param1, String param2) {
        AnggotaFragment fragment = new AnggotaFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewanggota = view.findViewById(R.id.rvanggota);
        nama = view.findViewById(R.id.tvnama);
        kode = view.findViewById(R.id.tvkodeanggota);
        alamat = view.findViewById(R.id.tvalamat);
        level = view.findViewById(R.id.tvlevel);

        datadariApi();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anggota, container, false);


    }

    private void datadariApi() {
        AndroidNetworking.get(Service.URL+Service.anggota)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray arrayanggota = response.getJSONArray("anggota");

                            for (int i = 0; i < arrayanggota.length(); i++) {
                                AnggotaItem anggotaItem = new AnggotaItem();
                                JSONObject objectanggota = arrayanggota.getJSONObject(i);

                                anggotaItem.setNama(objectanggota.getString("nama"));
                                anggotaItem.setAlamat(objectanggota.getString("alamat"));
                                anggotaItem.setUserId(objectanggota.getString("user_id"));
                                anggotaItem.setLevel(objectanggota.getString("level"));

                                anggotaItems.add(anggotaItem);
                            }

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            AnggotaAdapter anggotaAdapter = new AnggotaAdapter(anggotaItems, getContext());
                            recyclerViewanggota.setLayoutManager(layoutManager);
                            recyclerViewanggota.setAdapter(anggotaAdapter);

//                            Toast.makeText(getContext(), "anggota"+anggotaItem.getNama(), Toast.LENGTH_SHORT).show();

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