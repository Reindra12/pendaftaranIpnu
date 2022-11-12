package com.example.pendaftaran.Services;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    public static final String iduser = "id_user";
    public static final String appname = "";
    public static final String statuslogin = "login";
    public static final String namauser  = "nama";
    public static final String level = "";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(appname, Context.MODE_PRIVATE);
    }

    public Preferences(Context context) {
        sharedPreferences = context.getSharedPreferences(appname, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public void saveBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getIduser() {
        return sharedPreferences.getString(iduser, "");
    }

    public Boolean getStatusLogin() {
        return sharedPreferences.getBoolean(statuslogin, false);
    }

    public String getNamauser(){
        return sharedPreferences.getString(namauser, "");
    }

    public String getLevel(){
        return sharedPreferences.getString(level,"");
    }

}
