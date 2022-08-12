package com.example.pendaftaran.dashboard.Model;

import com.google.gson.annotations.SerializedName;

public class SuratKeluarItem{

	@SerializedName("status_keluar")
	private String statusKeluar;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("keluar_id")
	private String keluarId;

	@SerializedName("berkas_keluar")
	private String berkasKeluar;

	@SerializedName("perihal_id")
	private String perihalId;

	@SerializedName("nama_perihal")
	private String namaPerihal;

	@SerializedName("level")
	private String level;

	@SerializedName("sifat_id")
	private String sifatId;

	@SerializedName("tgl_keluar")
	private String tglKeluar;

	@SerializedName("kecamatan_id")
	private String kecamatanId;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("nama_sifat")
	private String namaSifat;

	@SerializedName("foto_cabang")
	private String fotoCabang;

	@SerializedName("password")
	private String password;

	@SerializedName("cabang_id")
	private String cabangId;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("no_surat")
	private String noSurat;

	@SerializedName("nama_cabang")
	private String namaCabang;

	@SerializedName("isi")
	private String isi;

	@SerializedName("username")
	private String username;

	public void setStatusKeluar(String statusKeluar){
		this.statusKeluar = statusKeluar;
	}

	public String getStatusKeluar(){
		return statusKeluar;
	}

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setKeluarId(String keluarId){
		this.keluarId = keluarId;
	}

	public String getKeluarId(){
		return keluarId;
	}

	public void setBerkasKeluar(String berkasKeluar){
		this.berkasKeluar = berkasKeluar;
	}

	public String getBerkasKeluar(){
		return berkasKeluar;
	}

	public void setPerihalId(String perihalId){
		this.perihalId = perihalId;
	}

	public String getPerihalId(){
		return perihalId;
	}

	public void setNamaPerihal(String namaPerihal){
		this.namaPerihal = namaPerihal;
	}

	public String getNamaPerihal(){
		return namaPerihal;
	}

	public void setLevel(String level){
		this.level = level;
	}

	public String getLevel(){
		return level;
	}

	public void setSifatId(String sifatId){
		this.sifatId = sifatId;
	}

	public String getSifatId(){
		return sifatId;
	}

	public void setTglKeluar(String tglKeluar){
		this.tglKeluar = tglKeluar;
	}

	public String getTglKeluar(){
		return tglKeluar;
	}

	public void setKecamatanId(String kecamatanId){
		this.kecamatanId = kecamatanId;
	}

	public String getKecamatanId(){
		return kecamatanId;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setNamaSifat(String namaSifat){
		this.namaSifat = namaSifat;
	}

	public String getNamaSifat(){
		return namaSifat;
	}

	public void setFotoCabang(String fotoCabang){
		this.fotoCabang = fotoCabang;
	}

	public String getFotoCabang(){
		return fotoCabang;
	}

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

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setNoSurat(String noSurat){
		this.noSurat = noSurat;
	}

	public String getNoSurat(){
		return noSurat;
	}

	public void setNamaCabang(String namaCabang){
		this.namaCabang = namaCabang;
	}

	public String getNamaCabang(){
		return namaCabang;
	}

	public void setIsi(String isi){
		this.isi = isi;
	}

	public String getIsi(){
		return isi;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}