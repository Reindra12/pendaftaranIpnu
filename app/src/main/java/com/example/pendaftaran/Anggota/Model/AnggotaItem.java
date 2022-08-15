package com.example.pendaftaran.Anggota.Model;

import com.google.gson.annotations.SerializedName;

public class AnggotaItem{

    @SerializedName("password")
    private String password;

    @SerializedName("cabang_id")
    private String cabangId;

    @SerializedName("nama")
    private String nama;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("level")
    private String level;

    @SerializedName("username")
    private String username;

    @SerializedName("alamat")
    private String alamat;

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setCabangId(String cabangId){
        this.cabangId = cabangId;
    }

    public String getCabangId(){
        return cabangId;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public String getLevel(){
        return level;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setAlamat(String alamat){
        this.alamat = alamat;
    }

    public String getAlamat(){
        return alamat;
    }
}