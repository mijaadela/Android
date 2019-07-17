package com.example.adela.proiectquizz.db;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract() {
    }

    public static class UsersTable implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_CLASS = "class";
        public static final String COLUMN_DEPARTMENT = "department";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_PROF_ID = "profId";
        public static final String COLUMN_TEST_ID = "testId";
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTIO = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
        public static final String COLUMN_TEST_ID = "testId";
    }

    public static class TestsTable implements BaseColumns {
        public static final String TABLE_NAME = "tests";
        public static final String COLUMN_CLASS_CATEGORY = "class_category";
        public static final String COLUMN_TEST_NAME = "testName";
        public static final String COLUMN_CREATED_BY = "createdBy";
        public static final String COLUMN_ACCESS_CODE = "accessCode";
    }

    public static class CompletedTestTable implements BaseColumns {
        public static final String TABLE_NAME = "completedTest";
        public static final String COLUMN_CLASS_CATEGORY = "class_category";
        public static final String COLUMN_TEST_NAME = "testName";
        public static final String COLUMN_TOTAL_QUESTIONS = "totalQuestions";
        public static final String COLUMN_CORRECT_QUESTIONS = "correctQuestions";
        public static final String COLUMN_TEST_TIME = "testTime";
        public static final String COLUMN_COMPLETED_BY = "completedBy";
    }
}