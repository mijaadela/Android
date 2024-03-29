package com.example.adela.proiectquizz;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.adela.proiectquizz.authentication.MainActivity;

public class SplashScreen extends AppCompatActivity {
private static int  SPLASH_SCREEN_TIME_OUT=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_TIME_OUT);
    }
}
