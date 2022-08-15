package com.example.pendaftaran.LaporanKegiatan.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseLaporan {

    @SerializedName("laporan")
    private List<LaporanItem> laporan;

    public void setLaporan(List<LaporanItem> laporan){
        this.laporan = laporan;
    }

    public List<LaporanItem> getLaporan(){
        return laporan;
    }
}