package com.example.adela.proiectquizz.profesori;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import com.example.adela.proiectquizz.About;
import com.example.adela.proiectquizz.Adapters.RecyclerViewAdapterResult;
import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.authentication.MainActivity;
import com.example.adela.proiectquizz.main.MeniuProfesori;
import com.example.adela.proiectquizz.main.MeniuStudenti;
import com.example.adela.proiectquizz.pojos.Student;

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner sp_gruop, sp_test;
    private ListView listView_result;
    private Button btn_show_results;
    private ArrayList<Student> listResults;
    private RecyclerViewAdapterResult adapterResult;

    private List<Integer> listGroups;
    private List<String> listTests;
    private List<Student> listStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        initLists();
        initListStudent();

        listResults = new ArrayList<>();

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, listGroups);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_gruop = (Spinner)findViewById(R.id.sp_group);
        sp_gruop.setAdapter(dataAdapter);

        sp_test = (Spinner)findViewById(R.id.sp_test);
        ArrayAdapter<String> dataAdapterTests = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listTests);
        sp_test.setAdapter(dataAdapterTests);

        btn_show_results = (Button)findViewById(R.id.btn_show_results);
        btn_show_results.setOnClickListener(this);

        adapterResult = new RecyclerViewAdapterResult(listResults, this);
        listView_result = (ListView) findViewById(R.id.list_item_result);
        listView_result.setAdapter(adapterResult);
    }

    private void initLists(){
        listGroups = new ArrayList<>();
        listGroups.add( 1045);
        listGroups.add( 1046);
        listGroups.add( 1047);
        listGroups.add( 1048);
        listGroups.add( 1049);

        listTests = new ArrayList<>();
        listTests.add("POO");
        listTests.add("SDD");
        listTests.add("CTS");
        listTests.add("JAVA");
    }

    private void initListStudent(){
        listStudents = new ArrayList<>();
        listStudents.add(new Student("Andrei Cosmin",
                true, 100,1049,listTests));
        listStudents.add(new Student("Popescu Andrei",
                false, 23,1047,listTests));
        listStudents.add(new Student("Anita Ion",
                false, 26,1045,listTests));
        listStudents.add(new Student("Aron Andra",
                true, 88,1048,listTests));
        listStudents.add(new Student("Andrei Popa",
                true, 55,1046,listTests));
        listStudents.add(new Student("Vasilescu Ionut",
                true, 65,1048,listTests));
        listStudents.add(new Student("Constantion Ion",
                true, 79,1046,listTests));
        listStudents.add(new Student("Apopei Ana",
                true, 67,1047,listTests));
        listStudents.add(new Student("Vasile Aron",
                true, 97,1048,listTests));
        listStudents.add(new Student("Andrei Nastase",
                true, 56,1049,listTests));
        listStudents.add(new Student("Panturu Cosmin",
                true, 65,1049,listTests));
    }

    @Override
    public void onClick(View view) {
        int group = Integer.parseInt(sp_gruop.getSelectedItem().toString());
        String test = sp_test.getSelectedItem().toString();

        listResults.clear();
        for (Student student: listStudents) {
            if( student.getGruop() == group && student.getListTests().contains(test)){
                listResults.add(student);
            }
        }
        adapterResult.notifyDataSetChanged();
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
