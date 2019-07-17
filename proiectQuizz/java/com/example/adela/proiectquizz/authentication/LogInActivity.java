package com.example.adela.proiectquizz.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adela.proiectquizz.SharedPrefConstants;
import com.example.adela.proiectquizz.db.QuizContract;
import com.example.adela.proiectquizz.db.QuizDbHelper;
import com.example.adela.proiectquizz.main.MeniuProfesori;
import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.main.MeniuStudenti;

public class LogInActivity extends AppCompatActivity {

    QuizDbHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    String[] userProjection = {
            BaseColumns._ID,
            QuizContract.UsersTable.COLUMN_EMAIL,
            QuizContract.UsersTable.COLUMN_PASSWORD
    };

    private SharedPreferences prefs;

    private EditText NameL;
    private EditText Password;
    private Button Login;
    private String email, strPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_student);

        dbHelper = new QuizDbHelper(this);
        db = dbHelper.getReadableDatabase();


        NameL = (EditText) findViewById(R.id.etEmaillog);
        Password = (EditText) findViewById(R.id.etPasswordlog);

        Login = (Button) findViewById(R.id.btnLogIn);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            initialize();
            cursor = db.rawQuery("SELECT _id, email, password FROM " + QuizContract.UsersTable.TABLE_NAME + " WHERE " + QuizContract.UsersTable.COLUMN_EMAIL + " =? AND " + QuizContract.UsersTable.COLUMN_PASSWORD + " =? ", new String[]{email, strPassword});
            if (cursor != null) {
                cursor.moveToNext();
                if (cursor.getCount() > 0) {
                    int userId = cursor.getInt(cursor.getColumnIndex(QuizContract.UsersTable._ID));
                    cursor.close();

                    if (email.contains("@stud.ase.ro")) { //TRECE LA PAGINA STUDENTULUI
                        Intent intent1 = new Intent(LogInActivity.this, MeniuStudenti.class);
                        saveUserId(userId);
                        startActivity(intent1);
                    } else if (email.contains("@ie.ase.ro")) {//TRECE LA PAGINA PROFESORULUI
                        Intent i2 = new Intent(LogInActivity.this, MeniuProfesori.class);
                        saveUserId(userId);
                        startActivity(i2);
                    }
                    Toast.makeText(getApplicationContext(), "Login succesfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
            }
        });
    }

    public void initialize() {
        email = NameL.getText().toString();
        strPassword = Password.getText().toString();
    }

    private void saveUserId(int userId) {
        prefs=getSharedPreferences(SharedPrefConstants.SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt(SharedPrefConstants.USER_ID, userId);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
