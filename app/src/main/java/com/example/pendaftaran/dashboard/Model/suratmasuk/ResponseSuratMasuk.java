package com.example.pendaftaran.dashboard.Model.suratmasuk;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSuratMasuk{

    @SerializedName("surat_masuk")
    private List<SuratMasukItem> suratMasuk;

    public void setSuratMasuk(List<SuratMasukItem> suratMasuk){
        this.suratMasuk = suratMasuk;
    }

    public List<SuratMasukItem> getSuratMasuk(){
        return suratMasuk;
    }
}