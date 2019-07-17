package com.example.adela.proiectquizz.authentication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adela.proiectquizz.R;

public class MainActivity extends AppCompatActivity {
     private Button button1;
     private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=(Button) findViewById(R.id.loginstart);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogInStudent();

            }
        });


        button2=(Button)findViewById(R.id.sing_up);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSingUp();

            }
        });
        
    }


   public void openLogInStudent(){
        Intent intent= new Intent(this, LogInActivity.class);
        startActivity(intent);
   }

   public void openSingUp(){
        Intent intent= new Intent(this, SingUp.class);
        startActivity(intent);

    }
}
