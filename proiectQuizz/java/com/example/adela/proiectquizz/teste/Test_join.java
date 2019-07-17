package com.example.adela.proiectquizz.teste;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.adela.proiectquizz.About;
import com.example.adela.proiectquizz.authentication.MainActivity;
import com.example.adela.proiectquizz.main.MeniuStudenti;
import com.example.adela.proiectquizz.quiz.QuizActivity;
import com.example.adela.proiectquizz.R;

public class Test_join extends AppCompatActivity {

    String accessCode;
    Button btnBegin = null;
    EditText etCodAcces = null;
    long testId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_join);

        btnBegin = (Button) findViewById(R.id.buttonBegin);
        etCodAcces = (EditText) findViewById(R.id.etCodAcces);

        Bundle recdData = getIntent().getExtras();
        if(recdData != null) {
            testId = recdData.getLong("testId");
            accessCode = recdData.getString("accessCode");
        }

        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    //asta trb unita cu testul
                    Intent intent = new Intent(Test_join.this, QuizActivity.class);
                    intent.putExtra("testId", testId);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public boolean validate() {
        boolean valid = true;
        if (etCodAcces.getText().toString().equals("") || etCodAcces.getText().toString().isEmpty()) {

            etCodAcces.setError("Introduceti un cod");
            valid = false;
        }
        if (!etCodAcces.getText().toString().equals(accessCode)) {
            etCodAcces.setError("Introduceti un cod valid");
            valid = false;
        }
        return valid;
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

                Intent intent1= new Intent(this,MeniuStudenti.class);
                startActivity(intent1);
                break;
            case  R.id.menuLogOut:
                //aici trebuie sa pun clasa unde se face logarea sau cea de welcome
                Intent intent2= new Intent(this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.menuAbout:
                //nu stiu dc nu merge
                Intent intent= new Intent(this,About.class);
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

