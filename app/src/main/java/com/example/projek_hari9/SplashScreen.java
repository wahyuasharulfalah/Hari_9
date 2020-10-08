package com.example.projek_hari9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    Shareprefs shareprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        shareprefs = new Shareprefs(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (shareprefs.getonLogin().equals(true)) {
                    startActivity(new Intent(SplashScreen.this,MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();
                }
            }
        },1000);
    }
}