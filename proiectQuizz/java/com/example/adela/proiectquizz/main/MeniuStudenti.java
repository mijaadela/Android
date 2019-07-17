package com.example.adela.proiectquizz.main;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adela.proiectquizz.About;
import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.istoricElevi.Student_Istoric;
import com.example.adela.proiectquizz.Student_grafice;
import com.example.adela.proiectquizz.teste.Teste_Main;
import com.example.adela.proiectquizz.authentication.MainActivity;

public class MeniuStudenti extends AppCompatActivity {


    Button buttonTeste= null;
    Button buttonIstoric= null;
    Button buttonGrafice= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);


        buttonTeste = (Button) findViewById(R.id.buttonTeste);
        buttonIstoric = (Button)findViewById(R.id.buttonIstoric);
        buttonGrafice = (Button)findViewById(R.id.buttonGrafice);


        buttonTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1;
                intent1 = new Intent(MeniuStudenti.this, Teste_Main.class);
                startActivity(intent1);

            }
        });

        buttonIstoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2= new Intent(MeniuStudenti.this,Student_Istoric.class);
                startActivity(intent2);
            }
        });

        buttonGrafice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3= new Intent(MeniuStudenti.this, Student_grafice.class);
                startActivity(intent3);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menuHome:

                Toast.makeText(MeniuStudenti.this,"Te afli deja in Home Student",Toast.LENGTH_LONG).show();
                break;
            case  R.id.menuLogOut:
                //aici trebuie sa pun clasa unde se face logarea sau cea de welcome
                Intent intent2= new Intent(MeniuStudenti.this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.menuAbout:
                //nu stiu dc nu merge
                Intent intent= new Intent(MeniuStudenti.this,About.class);
                startActivity(intent);
                break;
            case R.id.menuExit:
                moveTaskToBack(true);
                android.os.Process.killProcess(Process.myPid());
                System.exit(1);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
