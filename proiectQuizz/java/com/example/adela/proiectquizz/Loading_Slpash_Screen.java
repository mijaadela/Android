package com.example.adela.proiectquizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.adela.proiectquizz.quiz.QuizActivity;

public class Loading_Slpash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading__slpash__screen);
        Thread t = new Thread()
        {
            @Override
            public void run() {
                super.run();
                try
                {
                    sleep(3000);
                }
                catch (InterruptedException ex)
                {
                    Log.e("SplashScreen" ,ex.getMessage());
                }
                finally {

                    //aici nu e main activiti ci aia pt test
                    Intent intent1 = new Intent(getApplicationContext(),
                            QuizActivity.class);
                    startActivity(intent1);
                    finish();
                }
            };
        };
        t.start();

    }
}
