package com.example.adela.proiectquizz.istoricElevi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.adela.proiectquizz.About;
import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.SharedPrefConstants;
import com.example.adela.proiectquizz.authentication.MainActivity;
import com.example.adela.proiectquizz.db.QuizDbHelper;
import com.example.adela.proiectquizz.main.MeniuStudenti;
import com.example.adela.proiectquizz.pojos.RezultatTest;

import java.util.ArrayList;
import java.util.List;

public class Student_Istoric extends AppCompatActivity {
    ListView lista;

    QuizDbHelper dbHelper;
    SQLiteDatabase db;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__istoric);
        lista = (ListView) findViewById(R.id.lvTesteEfectuate);
        dbHelper = new QuizDbHelper(this);
        db = dbHelper.getReadableDatabase();
        prefs=getSharedPreferences(SharedPrefConstants.SHARED_PREFS,MODE_PRIVATE);

        setListView();
    }

    private void setListView() {
        List<RezultatTest> listaRezultatTest = new ArrayList<>();
        long userId = prefs.getInt(SharedPrefConstants.USER_ID, -1);
        listaRezultatTest = dbHelper.getCompletedTestsByStudent(userId);
        AdaptorListaTesteEfectuate adaptor = new AdaptorListaTesteEfectuate(this, R.layout.rand_listview, listaRezultatTest);

        lista.setAdapter(adaptor);
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
