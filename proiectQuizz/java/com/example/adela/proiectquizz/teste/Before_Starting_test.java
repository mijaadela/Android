package com.example.adela.proiectquizz.teste;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.adela.proiectquizz.About;
import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.authentication.MainActivity;
import com.example.adela.proiectquizz.db.QuizDbHelper;
import com.example.adela.proiectquizz.main.MeniuStudenti;
import com.example.adela.proiectquizz.pojos.Question;
import com.example.adela.proiectquizz.pojos.Test;

import java.util.ArrayList;
import java.util.List;

public class Before_Starting_test extends AppCompatActivity {

    QuizDbHelper dbHelper;
    SQLiteDatabase db;

    private String value;
    private long testId;
    private String accessCode;
    private Test test;
    private List<Question> questionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before__starting_test);

        dbHelper = new QuizDbHelper(this);
        db = dbHelper.getWritableDatabase();

        Bundle recdData = getIntent().getExtras();
        if(recdData != null) {
            value = recdData.getString("value");
            testId = recdData.getLong("testId");
            test = dbHelper.getTestById(testId);
            questionList = dbHelper.getQuestionsByTestId(testId);
        }
        String myVal = test.getTestName();

        Integer nrIntrebari = questionList.size();

        TextView tvHintTitluPreluat = (TextView) findViewById(R.id.tvHintTitluPreluat);
        tvHintTitluPreluat.setText(myVal);

        TextView tvHintNrIntrebariPreluate = (TextView) findViewById(R.id.tvHintNrIntrebariPreluate);
        tvHintNrIntrebariPreluate.setText(nrIntrebari.toString());

        Button btnStart = (Button) findViewById(R.id.BtnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessCode = test.getAccessCode();
                Intent intent1;
                intent1 = new Intent(Before_Starting_test.this, Test_join.class);
                intent1.putExtra("testId", testId);
                intent1.putExtra("accessCode", accessCode);
                startActivity(intent1);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
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
