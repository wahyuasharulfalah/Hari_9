package com.example.projek_hari9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    Button register, gotolog;
    TextInputEditText us, em, ps, nm,as,al;
    ProgressDialog pg;
    File file;
    public static final String FILENAME = "login.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.register);
        gotolog = findViewById(R.id.btn_gotolog);
        nm = findViewById(R.id.regnama);
        us = findViewById(R.id.reguser);
        ps = findViewById(R.id.regpass);
        em = findViewById(R.id.regemail);
        as = findViewById(R.id.regasalskolah);
        al = findViewById(R.id.regalamat);

        file = new File(getFilesDir(), FILENAME);

        pg = new ProgressDialog(this);
        pg.setIndeterminate(false);
        pg.setCancelable(false);
        pg.setMessage("Tunggu Sebentar ...");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(nm.getText())){
                    Toast.makeText(RegisterActivity.this, "Nama Kosong", Toast.LENGTH_SHORT).show();
                    nm.setError("Masukkan Nama");
                }else if (TextUtils.isEmpty(us.getText())){
                    Toast.makeText(RegisterActivity.this, "Username Kosong", Toast.LENGTH_SHORT).show();
                    us.setError("Masukkan Username");
                }else if (TextUtils.isEmpty(ps.getText())){
                    Toast.makeText(RegisterActivity.this, "Password Kosong", Toast.LENGTH_SHORT).show();
                    ps.setError("Masukkan Password");
                }else if (TextUtils.isEmpty(em.getText())) {
                    Toast.makeText(RegisterActivity.this, "Email Kosong", Toast.LENGTH_SHORT).show();
                    em.setError("Masukkan Email");
                }else if (TextUtils.isEmpty(as.getText())){
                    Toast.makeText(RegisterActivity.this, "Asal Sekolah Kosong", Toast.LENGTH_SHORT).show();
                    as.setError("Masukkan Asal Sekolah");
                }else if (TextUtils.isEmpty(al.getText())){
                    Toast.makeText(RegisterActivity.this, "Alamat Kosong", Toast.LENGTH_SHORT).show();
                    al.setError("Masukkan Alamat");
                }else {
                    reqReg(us.getText().toString(), ps.getText().toString(), nm.getText().toString(),em.getText().toString(), as.getText().toString(), al.getText().toString());
                }
            }
        });

        gotolog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    public void reqReg(String user, String pass, String nama, String email, String asal, String alamat) {
        pg.show();
        file.delete();
        String isiFile = user+";"+pass+";"+nama+";"+email+";"+asal+";"+alamat;
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
            Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        } catch (IOException e) {
            pg.dismiss();
            Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}