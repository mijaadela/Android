package com.example.adela.proiectquizz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.adela.proiectquizz.pojos.RezultatTest;
import com.example.adela.proiectquizz.pojos.Test;
import com.example.adela.proiectquizz.pojos.Question;
import com.example.adela.proiectquizz.db.QuizContract.QuestionsTable;
import com.example.adela.proiectquizz.db.QuizContract.UsersTable;
import com.example.adela.proiectquizz.db.QuizContract.TestsTable;
import com.example.adela.proiectquizz.db.QuizContract.CompletedTestTable;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " +
            UsersTable.TABLE_NAME + " ( " +
            UsersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UsersTable.COLUMN_NAME + " TEXT," +
            UsersTable.COLUMN_SURNAME + " TEXT," +
            UsersTable.COLUMN_DEPARTMENT + " TEXT," +
            UsersTable.COLUMN_EMAIL + " TEXT," +
            UsersTable.COLUMN_CLASS + " TEXT," +
            UsersTable.COLUMN_PASSWORD + " TEXT" +
            ")";

    private static final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
            QuestionsTable.TABLE_NAME + " ( " +
            QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QuestionsTable.COLUMN_QUESTIO + " TEXT," +
            QuestionsTable.COLUMN_OPTION1 + " TEXT," +
            QuestionsTable.COLUMN_OPTION2 + " TEXT," +
            QuestionsTable.COLUMN_OPTION3 + " TEXT," +
            QuestionsTable.COLUMN_ANSWER_NR + " INTEGER," +
            QuestionsTable.COLUMN_TEST_ID + " LONG" +
            ")";

    private static final String SQL_CREATE_TESTS_TABLE = "CREATE TABLE " +
            TestsTable.TABLE_NAME + " ( " +
            TestsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TestsTable.COLUMN_TEST_NAME + " TEXT," +
            TestsTable.COLUMN_CREATED_BY + " LONG," +
            TestsTable.COLUMN_ACCESS_CODE + " TEXT," +
            TestsTable.COLUMN_CLASS_CATEGORY + " TEXT" +
            ")";

    private static final String SQL_CREATE_COMPLETED_TESTS_TABLE = "CREATE TABLE " +
            CompletedTestTable.TABLE_NAME + " ( " +
            CompletedTestTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CompletedTestTable.COLUMN_TEST_NAME + " TEXT," +
            CompletedTestTable.COLUMN_COMPLETED_BY + " LONG," +
            CompletedTestTable.COLUMN_TEST_TIME + " LONG," +
            CompletedTestTable.COLUMN_TOTAL_QUESTIONS + " TEXT," +
            CompletedTestTable.COLUMN_CORRECT_QUESTIONS + " TEXT," +
            CompletedTestTable.COLUMN_CLASS_CATEGORY + " TEXT" +
            ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME;
    private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + UsersTable.TABLE_NAME;
    private static final String SQL_DELETE_TESTS = "DROP TABLE IF EXISTS " + TestsTable.TABLE_NAME;
    private static final String SQL_DELETE_COMPLETED_TESTS = "DROP TABLE IF EXISTS " + CompletedTestTable.TABLE_NAME;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_USERS_TABLE);
        db.execSQL(SQL_CREATE_TESTS_TABLE);
        db.execSQL(SQL_CREATE_COMPLETED_TESTS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_USERS);
        db.execSQL(SQL_DELETE_TESTS);
        db.execSQL(SQL_DELETE_COMPLETED_TESTS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Cat face 2+2?", "a) 2", "b) 3", "c) 4", 3, 1);
        addQuestion(q1);
        Question q2 = new Question("Cat face 2+3?", "a) 2", "b) 5", "c) 4", 2, 1);
        addQuestion(q2);
        Question q3 = new Question("Cat face 2-2?", "a) 2", "b) 0", "c) 4",
                2, 1);
        addQuestion(q3);
        Question q4 = new Question("Cat face 2:2?", "a) 2", "b) 3", "c) 1", 3, 1);
        addQuestion(q4);
        Question q5 = new Question("Cat face 2*2?", "a) 4", "b) 3", "c) 13", 1, 1);
        addQuestion(q5);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTIO, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTIO)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setTestId(c.getLong(c.getColumnIndex(QuestionsTable.COLUMN_TEST_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public List<Test> getAllTests() {
        List<Test> testList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TestsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Test test = new Test();
                test.setTestName(c.getString(c.getColumnIndex(TestsTable.COLUMN_TEST_NAME)));
                test.setTestClass(c.getString(c.getColumnIndex(TestsTable.COLUMN_CLASS_CATEGORY)));
                test.setAccessCode(c.getString(c.getColumnIndex(TestsTable.COLUMN_ACCESS_CODE)));
                test.setCreatedBy(c.getLong(c.getColumnIndex(TestsTable.COLUMN_CREATED_BY)));
                test.setId(c.getLong(c.getColumnIndex(TestsTable._ID)));
                testList.add(test);
            } while (c.moveToNext());
        }
        c.close();
        return testList;
    }

    public Test getTestById(long id) {
        Test test = null;
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TestsTable.TABLE_NAME + " WHERE _ID = " + id, null);
        if (c.moveToFirst()) {
            do {
                test = new Test();
                test.setTestName(c.getString(c.getColumnIndex(TestsTable.COLUMN_TEST_NAME)));
                test.setTestClass(c.getString(c.getColumnIndex(TestsTable.COLUMN_CLASS_CATEGORY)));
                test.setAccessCode(c.getString(c.getColumnIndex(TestsTable.COLUMN_ACCESS_CODE)));
                test.setCreatedBy(c.getLong(c.getColumnIndex(TestsTable.COLUMN_CREATED_BY)));
                test.setId(c.getLong(c.getColumnIndex(TestsTable._ID)));
            } while (c.moveToNext());
        }
        c.close();
        return test;
    }

    public List<Test> getTesteByCreatedId(long createdId) {
        List<Test> testList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TestsTable.TABLE_NAME + " WHERE createdBy = " + createdId, null);
        if (c.moveToFirst()) {
            do {
                Test test = new Test();
                test.setTestName(c.getString(c.getColumnIndex(TestsTable.COLUMN_TEST_NAME)));
                test.setTestClass(c.getString(c.getColumnIndex(TestsTable.COLUMN_CLASS_CATEGORY)));
                test.setAccessCode(c.getString(c.getColumnIndex(TestsTable.COLUMN_ACCESS_CODE)));
                test.setCreatedBy(c.getLong(c.getColumnIndex(TestsTable.COLUMN_CREATED_BY)));
                test.setId(c.getLong(c.getColumnIndex(TestsTable._ID)));
                testList.add(test);
            } while (c.moveToNext());
        }
        c.close();
        return testList;
    }

    public List<Question> getQuestionsByTestId(long id) {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE testId = " + id, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTIO)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setTestId(c.getLong(c.getColumnIndex(QuestionsTable.COLUMN_TEST_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public List<RezultatTest> getCompletedTestsByStudent(long completedBy) {
        List<RezultatTest> testList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CompletedTestTable.TABLE_NAME + " WHERE completedBy = " + completedBy, null);
        if (c.moveToFirst()) {
            do {
                RezultatTest test = new RezultatTest();
                test.setName(c.getString(c.getColumnIndex(CompletedTestTable.COLUMN_TEST_NAME)));
                test.setMaterieTest(c.getString(c.getColumnIndex(CompletedTestTable.COLUMN_CLASS_CATEGORY)));
                test.setNrIntrebariCorecte(c.getString(c.getColumnIndex(CompletedTestTable.COLUMN_CORRECT_QUESTIONS)));
                test.setNrIntrebari(c.getString(c.getColumnIndex(CompletedTestTable.COLUMN_TOTAL_QUESTIONS)));
                testList.add(test);
            } while (c.moveToNext());
        }
        c.close();
        return testList;
    }
}
