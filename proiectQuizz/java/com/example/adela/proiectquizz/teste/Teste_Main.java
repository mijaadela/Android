package com.example.adela.proiectquizz.teste;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.adela.proiectquizz.About;
import com.example.adela.proiectquizz.pojos.Test;
import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.authentication.MainActivity;
import com.example.adela.proiectquizz.db.QuizDbHelper;
import com.example.adela.proiectquizz.main.MeniuStudenti;

import java.util.ArrayList;
import java.util.List;

public class Teste_Main extends AppCompatActivity {


    QuizDbHelper dbHelper;
    SQLiteDatabase db;
    private List<Test> testList;

    private Spinner selectClassSp;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste__main);

        dbHelper = new QuizDbHelper(this);
        db = dbHelper.getWritableDatabase();

        listView = (ListView)findViewById(R.id.lvListaTeste);
        selectClassSp = (Spinner) findViewById(R.id.select_class_spinner);

        testList = dbHelper.getAllTests();

        setListView("");
        setClassSpinner();
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
    private void setListView(String materie) {

        List<String> items = new ArrayList<>();
        if(materie.equals("")) {
            for (Test test : testList) {
                items.add(test.getTestName());
            }
        } else {
            for (Test test : testList) {
                if(materie.equals(test.getTestClass())) {
                    items.add(test.getTestName());
                }
            }
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, items  );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = listView.getAdapter().getItem(position);
                String value= obj.toString();
                long selectedId = 1;
                for (Test test : testList) {
                    if(value.equals(test.getTestName())) {
                        selectedId = test.getId();
                    }
                }

                Intent intent = new Intent(Teste_Main.this, Before_Starting_test.class);
                intent.putExtra("value", value);
                intent.putExtra("testId", selectedId);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setClassSpinner() {
        List<String> items = new ArrayList<>();
        for (Test test : testList) {
            String item = test.getTestClass();
            if(!items.contains(item)) {
                items.add(item);
            }
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, items  );
        selectClassSp.setAdapter(spinnerAdapter);
        selectClassSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setListView(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
