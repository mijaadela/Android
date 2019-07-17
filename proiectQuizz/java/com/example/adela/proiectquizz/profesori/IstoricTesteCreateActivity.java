package com.example.adela.proiectquizz.profesori;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.adela.proiectquizz.About;
import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.SharedPrefConstants;
import com.example.adela.proiectquizz.authentication.MainActivity;
import com.example.adela.proiectquizz.db.QuizDbHelper;
import com.example.adela.proiectquizz.main.MeniuProfesori;
import com.example.adela.proiectquizz.main.MeniuStudenti;
import com.example.adela.proiectquizz.pojos.Test;

import java.util.List;

public class IstoricTesteCreateActivity extends AppCompatActivity {

    QuizDbHelper dbHelper;
    SQLiteDatabase db;
    private SharedPreferences prefs;
    private ListView createdTestsLv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_istoric_teste_create);

        dbHelper = new QuizDbHelper(this);
        db = dbHelper.getReadableDatabase();
        prefs = getSharedPreferences(SharedPrefConstants.SHARED_PREFS,MODE_PRIVATE);

        createdTestsLv = findViewById(R.id.createdTestsLv);
        setListView();
    }

    private void setListView() {
        long userId = prefs.getInt(SharedPrefConstants.USER_ID, -1);
        List<Test> testList = dbHelper.getTesteByCreatedId(userId);
        CreatedTestsAdapter createdTestsAdapter = new CreatedTestsAdapter(testList, this, db);
        createdTestsLv.setAdapter(createdTestsAdapter);
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

                Intent intent1= new Intent(this,MeniuProfesori.class);
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
