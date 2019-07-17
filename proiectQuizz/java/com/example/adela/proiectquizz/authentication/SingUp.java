package com.example.adela.proiectquizz.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.adela.proiectquizz.R;

public class SingUp extends AppCompatActivity {
    private Button btnstud;
    private Button btnteacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);


        btnstud = (Button) findViewById(R.id.btnStudent);
        btnstud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();

            }
        });

        btnteacher = (Button) findViewById(R.id.btnTeacher);
        btnteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opent();
            }
        });

    }


    public void open() {
        Intent intent = new Intent(this, SingUpStudent.class);
        startActivity(intent);
    }

    public void opent() {
        Intent intent2 = new Intent(this, SingUpTeacher.class);
        startActivity(intent2);
    }
}