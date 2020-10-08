package com.example.projek_hari9;

import android.content.Context;
import android.content.SharedPreferences;

public class Shareprefs {
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public static final String app_lsp = "AppVSGA";
    public final String onLogin = "onLogin";

    public Shareprefs(Context context) {
        sp = context.getSharedPreferences(app_lsp, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void clearing() {
        spEditor.clear();
        spEditor.commit();
    }
    public void savelogin(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }
    public Boolean getonLogin(){
        return sp.getBoolean(onLogin, false);
    }

    public String getnama(){
        return sp.getString("nama", "");
    }
    public void savenama(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }
}