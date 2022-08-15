package com.example.pendaftaran.Anggota.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseAnggota {

    @SerializedName("anggota")
    private List<AnggotaItem> anggota;

    @SerializedName("status")
    private Boolean status;

    public void setAnggota(List<AnggotaItem> anggota){
        this.anggota = anggota;
    }

    public List<AnggotaItem> getAnggota(){
        return anggota;
    }

    public void setStatus(Boolean status){
        this.status = status;
    }

    public Boolean isStatus(){
        return status;
    }
}