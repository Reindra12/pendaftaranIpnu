package com.example.pendaftaran.LaporanKegiatan.Model;

import com.google.gson.annotations.SerializedName;

public class LaporanItem{

    @SerializedName("foto_kegiatan")
    private String fotoKegiatan;

    @SerializedName("kegiatan_id")
    private String kegiatanId;

    @SerializedName("tempat")
    private String tempat;

    @SerializedName("cabang_id")
    private String cabangId;

    @SerializedName("nama_kegiatan")
    private String namaKegiatan;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("tgl")
    private String tgl;

    @SerializedName("keterangan")
    private String keterangan;

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setFotoKegiatan(String fotoKegiatan){
        this.fotoKegiatan = fotoKegiatan;
    }

    public String getFotoKegiatan(){
        return fotoKegiatan;
    }

    public void setKegiatanId(String kegiatanId){
        this.kegiatanId = kegiatanId;
    }

    public String getKegiatanId(){
        return kegiatanId;
    }

    public void setTempat(String tempat){
        this.tempat = tempat;
    }

    public String getTempat(){
        return tempat;
    }

    public void setCabangId(String cabangId){
        this.cabangId = cabangId;
    }

    public String getCabangId(){
        return cabangId;
    }

    public void setNamaKegiatan(String namaKegiatan){
        this.namaKegiatan = namaKegiatan;
    }

    public String getNamaKegiatan(){
        return namaKegiatan;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setTgl(String tgl){
        this.tgl = tgl;
    }

    public String getTgl(){
        return tgl;
    }
}