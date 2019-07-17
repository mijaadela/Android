package com.example.adela.proiectquizz.profesori;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.adela.proiectquizz.Adapters.RecyclerViewAdapterQuestion;
import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.SharedPrefConstants;
import com.example.adela.proiectquizz.pojos.Question;
import com.example.adela.proiectquizz.db.QuizContract;
import com.example.adela.proiectquizz.db.QuizDbHelper;

public class TestsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private SharedPreferences prefs;

    private EditText et_questions, et_responsA, et_responsB, et_responsC;
    private Spinner sp_points;
    private Spinner class_sp;
    private Button btn_save_question, btn_save_test;

    private LinearLayout questionLl;
    private LinearLayout testInfoLl;
    private Button saveInfoBtn;
    private EditText testNameEt;
    private EditText accessCodeEt;

    QuizDbHelper dbHelper;
    SQLiteDatabase db;
    private long createdTestId;

    private ArrayList<Question> dataModels;
    private ListView listView;
    private static RecyclerViewAdapterQuestion adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        dbHelper = new QuizDbHelper(this);
        db = dbHelper.getWritableDatabase();

        dataModels = new ArrayList<>();


        testInfoLl = (LinearLayout) findViewById(R.id.testInfoLl);
        testNameEt = (EditText) findViewById(R.id.testNameEt);
        saveInfoBtn = (Button) findViewById(R.id.saveInfo);
        accessCodeEt = (EditText) findViewById(R.id.accessCodeEt);

        questionLl = (LinearLayout) findViewById(R.id.questionLl);
        listView = (ListView)findViewById(R.id.list_item);
        btn_save_test = (Button)findViewById(R.id.btn_save_test);

        saveInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String testName = testNameEt.getText().toString();
                String selectedClassCategory = class_sp.getSelectedItem().toString();
                String accessCode = accessCodeEt.getText().toString();
                createTest(testName, selectedClassCategory, accessCode);
            }
        });

        initView();
    }

    private void createTest(String testName, String selectedClassCategory, String accessCode) {
        testInfoLl.setVisibility(View.GONE);
        insertTest(testName, selectedClassCategory, accessCode);
        questionLl.setVisibility(View.VISIBLE);
        listView.setVisibility(View.VISIBLE);
        btn_save_test.setVisibility(View.VISIBLE);
    }

    private void initView(){

        listView.setOnItemClickListener(this);

        adapter = new RecyclerViewAdapterQuestion(dataModels, this);
        listView.setAdapter(adapter);

        et_questions = (EditText)findViewById(R.id.et_question);
        et_responsA = (EditText)findViewById(R.id.et_responseA);
        et_responsB = (EditText)findViewById(R.id.et_responseB);
        et_responsC = (EditText)findViewById(R.id.et_responseC);


        List<String> list = new ArrayList<String>();
        for(int i=1; i <= 3; i++){
            list.add(String.valueOf(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.class_arrays));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        sp_points = (Spinner)findViewById(R.id.sp_points);
        sp_points.setAdapter(dataAdapter);

        class_sp = (Spinner) findViewById(R.id.class_sp);
        class_sp.setAdapter(classAdapter);

        btn_save_question = (Button)findViewById(R.id.btn_save_question);
        btn_save_question.setOnClickListener(this);

        btn_save_test.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save_question:

                if(et_questions.getText().toString().isEmpty()) {
                    et_questions.setError(this.getResources().getString(R.string.invalid));
                    return;
                }

                if(et_responsA.getText().toString().isEmpty()) {
                    et_responsA.setError(this.getResources().getString(R.string.invalid));
                    return;
                }

                if(et_responsB.getText().toString().isEmpty()) {
                    et_responsB.setError(this.getResources().getString(R.string.invalid));
                    return;
                }

                if(et_responsC.getText().toString().isEmpty()) {
                    et_responsC.setError(this.getResources().getString(R.string.invalid));
                    return;
                }

                Question question = new Question(
                        et_questions.getText().toString(),
                        et_responsA.getText().toString(),
                        et_responsB.getText().toString(),
                        et_responsC.getText().toString(),
                        sp_points.getSelectedItemPosition() + 1,
                        createdTestId);

                et_questions.setText("");
                et_responsA.setText("");
                et_responsB.setText("");
                et_responsC.setText("");

                et_questions.setHint(this.getResources().getString(R.string.question));
                et_responsA.setHint(this.getResources().getString(R.string.response_a));
                et_responsB.setHint(this.getResources().getString(R.string.response_b));
                et_responsC.setHint(this.getResources().getString(R.string.response_c));

                dataModels.add(question);

                adapter.notifyDataSetChanged();
                break;

            case R.id.btn_save_test:
                saveAllQuestions();
                Toast.makeText(getApplicationContext(), "Test salvat cu succes!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                finish();
                break;
        }
    }

    private void saveAllQuestions() {
        for (Question question : dataModels) {
            insertData(question.getQuestion(), question.getOption1(), question.getOption2(), question.getOption3(),
                    question.getAnswerNr(), question.getTestId());
        }
    }

    public void insertData(String question, String firstAnswer, String secondAnswer, String thirdAnswer,
                           int correctAnswerPosition, long testId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizContract.QuestionsTable.COLUMN_QUESTIO, question);
        contentValues.put(QuizContract.QuestionsTable.COLUMN_OPTION1, firstAnswer);
        contentValues.put(QuizContract.QuestionsTable.COLUMN_OPTION2, secondAnswer);
        contentValues.put(QuizContract.QuestionsTable.COLUMN_OPTION3, thirdAnswer);
        contentValues.put(QuizContract.QuestionsTable.COLUMN_ANSWER_NR, correctAnswerPosition);
        contentValues.put(QuizContract.QuestionsTable.COLUMN_TEST_ID, testId);
        db.insert(QuizContract.QuestionsTable.TABLE_NAME, null, contentValues);
    }

    public void insertTest(String name, String category, String accessCode) {
        prefs=getSharedPreferences(SharedPrefConstants.SHARED_PREFS,MODE_PRIVATE);
        int userId = prefs.getInt(SharedPrefConstants.USER_ID, -1);
        Log.d("TestActivity", userId+"");

        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizContract.TestsTable.COLUMN_TEST_NAME, name);
        contentValues.put(QuizContract.TestsTable.COLUMN_CLASS_CATEGORY, category);
        contentValues.put(QuizContract.TestsTable.COLUMN_ACCESS_CODE, accessCode);
        contentValues.put(QuizContract.TestsTable.COLUMN_CREATED_BY, userId);
        createdTestId = db.insert(QuizContract.TestsTable.TABLE_NAME, null, contentValues);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        dataModels.remove(i);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
