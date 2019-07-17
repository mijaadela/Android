package com.example.adela.proiectquizz.quiz;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.SharedPrefConstants;
import com.example.adela.proiectquizz.db.QuizContract;
import com.example.adela.proiectquizz.pojos.Question;
import com.example.adela.proiectquizz.db.QuizDbHelper;
import com.example.adela.proiectquizz.main.MeniuStudenti;
import com.example.adela.proiectquizz.pojos.RezultatTest;
import com.example.adela.proiectquizz.pojos.Test;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE="scorExtra";
    private static final long COUNTDOWN_IN_MILLIS=30000;

    QuizDbHelper dbHelper;
    SQLiteDatabase db;
    private SharedPreferences prefs;

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private List<Question> questionList;
    private Test test;

    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;
    private long testId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        prefs=getSharedPreferences(SharedPrefConstants.SHARED_PREFS,MODE_PRIVATE);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        textViewQuestion = findViewById(R.id.text_view_question);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewScore = findViewById(R.id.text_view_score);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        buttonConfirmNext = findViewById(R.id.button_confirm_next);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd=textViewCountDown.getTextColors();

        Bundle recdData = getIntent().getExtras();
        if(recdData != null) {
            testId = recdData.getLong("testId");
        }

        dbHelper = new QuizDbHelper(this);
        db = dbHelper.getWritableDatabase();
        questionList = dbHelper.getQuestionsByTestId(testId);
        test = dbHelper.getTestById(testId);

        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();
        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, "Selectati un raspuns!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + " / " + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");

            timeLeftInMillis=COUNTDOWN_IN_MILLIS;
            startCoundDown();
        } else {
            finishQuiz();
        }
    }

    private void startCoundDown(){
        countDownTimer=new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis=millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis=0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();

    }

    private void updateCountDownText(){
        int minutes = (int)(timeLeftInMillis/1000)/60;
        int seconds=(int)(timeLeftInMillis/1000)%60;

        String timeFormatted=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);

        textViewCountDown.setText(timeFormatted);

        if(timeLeftInMillis<10000){
            textViewCountDown.setTextColor(Color.RED);
        }
        else{
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }
    private void checkAnswer() {
        answered = true;
        countDownTimer.cancel();
        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Scor: " + score);
            textViewQuestion.setText("Corect!");
        }
        else{
            textViewQuestion.setText("Gresit!");
        }

        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                //textViewQuestion.setText("Raspuns corect: 1");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
               // textViewQuestion.setText("Raspuns corect: 2");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                //textViewQuestion.setText("Raspuns corect: 3");
                break;
        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Continua");
        } else {
            buttonConfirmNext.setText("Sfarsit");
        }
    }

    private void finishQuiz() {
        Intent resultIntent=new Intent();
        resultIntent.putExtra(EXTRA_SCORE,score);
        setResult(RESULT_OK,resultIntent);
        String nrIntrebariTotal = questionList.size()+"";
        long userId = prefs.getInt(SharedPrefConstants.USER_ID, -1);
        String nrIntrebariCorecte = score + "";

        RezultatTest rezultatTest = new RezultatTest(test.getTestName(), test.getTestClass(), nrIntrebariCorecte, nrIntrebariTotal, "", userId);
        insertCompletedTest(rezultatTest);
        Intent intent =new Intent(QuizActivity.this,MeniuStudenti.class);
        startActivity(intent);
        finish();
    }

    public void insertCompletedTest(RezultatTest rezultatTest) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizContract.CompletedTestTable.COLUMN_TEST_NAME, rezultatTest.getName());
        contentValues.put(QuizContract.CompletedTestTable.COLUMN_CLASS_CATEGORY, rezultatTest.getMaterieTest());
        contentValues.put(QuizContract.CompletedTestTable.COLUMN_TOTAL_QUESTIONS, rezultatTest.getNrIntrebari());
        contentValues.put(QuizContract.CompletedTestTable.COLUMN_CORRECT_QUESTIONS, rezultatTest.getNrIntrebariCorecte());
        contentValues.put(QuizContract.CompletedTestTable.COLUMN_COMPLETED_BY, rezultatTest.getStudentId());
        contentValues.put(QuizContract.CompletedTestTable.COLUMN_TEST_TIME, rezultatTest.getTimp());
        db.insert(QuizContract.CompletedTestTable.TABLE_NAME, null, contentValues);
    }

    @Override
    protected void onDestroy() {
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
        dbHelper.close();
        super.onDestroy();
    }
}
