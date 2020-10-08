package com.example.projek_hari9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    LinearLayout lineView, lineKosong, lineEditAdd;
    TextInputEditText isi, judul;
    TextView txtIsi, txtJudul;
    Button keluar;
    public static final String FILENAME = "note.txt";
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineEditAdd = findViewById(R.id.lineEditTambah);
        lineView =findViewById(R.id.lineViewNote);
        isi = findViewById(R.id.et_content);
        judul = findViewById(R.id.et_title);
        lineKosong = findViewById(R.id.lineKosong);
        txtJudul = findViewById(R.id.txtJudulNote);
        txtIsi = findViewById(R.id.txtIsiNote);
        file = new File(getFilesDir(), FILENAME);
        keluar= findViewById(R.id.btn_logout);
        getNote();

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }
    public void getNote(){
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
                String string = String.valueOf(sb);
                String [] notee = string.split("////");
                txtJudul.setText(notee[0]);
                txtIsi.setText(notee[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void ubahNote(){
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
                String string = String.valueOf(sb);
                String [] notee = string.split("////");
                judul.setText(notee[0]);
                isi.setText(notee[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveNote(String judul, String isi){
        file.delete();
        String isiFile = judul+"////"+isi;
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
            Toast.makeText(this, "Note Disimpan", Toast.LENGTH_SHORT).show();
            getNote();
        } catch (IOException e) {
            Toast.makeText(this, "Gagal Membuat Note", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void buatNote(){
        lineView.setVisibility(View.GONE);
        lineKosong.setVisibility(View.GONE);
        lineEditAdd.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        final MenuItem tambah = menu.findItem(R.id.menu_tambah);
        final MenuItem edit = menu.findItem(R.id.menu_edit);
        final MenuItem selesai = menu.findItem(R.id.menu_done);

        if (file.exists()){
            edit.setVisible(true);
            lineKosong.setVisibility(View.GONE);
            lineView.setVisibility(View.VISIBLE);
            lineEditAdd.setVisibility(View.GONE);
        }else {
            tambah.setVisible(true);
            lineKosong.setVisibility(View.VISIBLE);
            lineView.setVisibility(View.GONE);
            lineEditAdd.setVisibility(View.GONE);
        }

        tambah.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                selesai.setVisible(true);
                tambah.setVisible(false);
                edit.setVisible(false);
                return false;
            }
        });
        edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                selesai.setVisible(true);
                tambah.setVisible(false);
                edit.setVisible(false);
                return false;
            }
        });
        selesai.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                selesai.setVisible(false);
                if (file.exists()){
                    edit.setVisible(true);
                }else {
                    tambah.setVisible(true);
                }
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_tambah) {
            buatNote();
        } else if (item.getItemId() == R.id.menu_edit) {
            ubahNote();
            lineKosong.setVisibility(View.GONE);
            lineView.setVisibility(View.GONE);
            lineEditAdd.setVisibility(View.VISIBLE);
        }else if (item.getItemId()==R.id.menu_done){
            saveNote(judul.getText().toString(),isi.getText().toString());
            if (file.exists()){
                lineView.setVisibility(View.VISIBLE);
                lineKosong.setVisibility(View.GONE);
                lineEditAdd.setVisibility(View.GONE);
            }else {
                lineKosong.setVisibility(View.VISIBLE);
                lineView.setVisibility(View.GONE);
                lineEditAdd.setVisibility(View.GONE);
            }
        }
        return true;
    }
}