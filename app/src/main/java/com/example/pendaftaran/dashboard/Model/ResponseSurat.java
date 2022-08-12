package com.example.pendaftaran.dashboard.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSurat{

	@SerializedName("surat_keluar")
	private List<SuratKeluarItem> suratKeluar;

	public void setSuratKeluar(List<SuratKeluarItem> suratKeluar){
		this.suratKeluar = suratKeluar;
	}

	public List<SuratKeluarItem> getSuratKeluar(){
		return suratKeluar;
	}
}