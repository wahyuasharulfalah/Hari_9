package com.example.projek_hari9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {
    Button masuk, gotoReg;
    ProgressDialog pg;
    TextInputEditText us, ps;
    Shareprefs sharedpref;
    public static final String FILENAME = "login.txt";
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        masuk = findViewById(R.id.btn_login);
        gotoReg = findViewById(R.id.btn_gotoreg);
        us = findViewById(R.id.etUser);
        ps = findViewById(R.id.etPass);
        sharedpref = new Shareprefs(this);
        file = new File(getFilesDir(), FILENAME);
        pg = new ProgressDialog(this);
        pg.setIndeterminate(false);
        pg.setCancelable(false);
        pg.setMessage("Tunggu Sebentar ...");

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(us.getText())){
                    Toast.makeText(LoginActivity.this, "Username Kosong", Toast.LENGTH_SHORT).show();
                    us.setError("Masukan Username");
                }else if (TextUtils.isEmpty(ps.getText())){
                    Toast.makeText(LoginActivity.this, "Password Kosong", Toast.LENGTH_SHORT).show();
                    ps.setError("Masukan Password");
                }else {
                    reqLogin(us.getText().toString(), ps.getText().toString());
                }
            }
        });
        gotoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
    public void reqLogin(String user, String pass) {
        pg.show();
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = openFileInput(FILENAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                Log.d("TAG", "reqLogin: "+sb);
                String string = String.valueOf(sb);
                String [] valid = string.split(";");
                if (user.equals(valid[0]) && pass.equals(valid[1])){
                    pg.dismiss();
                    sharedpref.savelogin(sharedpref.onLogin,true);
                    sharedpref.savenama("nama",valid[2]);
                    Toast.makeText(this, "Login Sukses", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else {
                    pg.dismiss();
                    Toast.makeText(this, "Username atau Password anda salah", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                pg.dismiss();
                Toast.makeText(this, "Login Gagal, Coba Lagi", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }else {
            pg.dismiss();
            Toast.makeText(this, "Anda Harus Register Dulu", Toast.LENGTH_SHORT).show();
        }
    }

}