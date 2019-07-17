package com.example.adela.proiectquizz.authentication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.db.QuizContract;
import com.example.adela.proiectquizz.db.QuizDbHelper;

import java.util.ArrayList;

public class SingUpStudent extends AppCompatActivity {

    QuizDbHelper dbHelper;
    SQLiteDatabase db;
    private EditText etName;
    private EditText LastName;
    private Spinner grupa;
    private EditText Email;
    private EditText Password;
    private EditText ConfPassword;
    private Button create;

    private String strName, strLN, strEmail, strPass, strCP, strGrupa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_student);
        dbHelper = new QuizDbHelper(this);
        db = dbHelper.getWritableDatabase();

        etName = (EditText) findViewById(R.id.Name);
        LastName = (EditText) findViewById(R.id.etLastName);
        Email = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPassword);
        ConfPassword = (EditText) findViewById(R.id.etConfirmPassword);
        create = (Button) findViewById(R.id.btnCreateStudentAccount);


        grupa = (Spinner) findViewById(R.id.spGrupa);
        int g = 1000;
        ArrayList<Integer> lista = new ArrayList<>();
        for (int i = 0; i < 82; i++) {
            lista.add(g);
            g++;
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grupa.setAdapter(adapter);

        // BUTONUL CATRE CONTUL STUDENTULUI

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize();
                if (!validate()) {
                    Toast.makeText(getApplicationContext(), "Inregistrarea a esuat!", Toast.LENGTH_SHORT).show();
                } else {
                    insertData(strName, strLN, strGrupa, strEmail, strPass);
                    goToLogin();
                }
            }
        });


    }


    public void initialize() {
        strName = etName.getText().toString().trim();
        strLN = LastName.getText().toString();
        strEmail = Email.getText().toString();
        strPass = Password.getText().toString();
        strCP = ConfPassword.getText().toString();
        strGrupa = grupa.getSelectedItem().toString();
    }

    public void insertData(String strName, String strLN, String strGrupa, String strEmail, String strPass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizContract.UsersTable.COLUMN_NAME, strName);
        contentValues.put(QuizContract.UsersTable.COLUMN_SURNAME, strLN);
        contentValues.put(QuizContract.UsersTable.COLUMN_CLASS, strGrupa);
        contentValues.put(QuizContract.UsersTable.COLUMN_EMAIL, strEmail);
        contentValues.put(QuizContract.UsersTable.COLUMN_PASSWORD, strPass);
        db.insert(QuizContract.UsersTable.TABLE_NAME, null, contentValues);
        Toast.makeText(getApplicationContext(), "Inregistrarea a fost efectuata cu succes!", Toast.LENGTH_SHORT).show();
    }

    public void goToLogin() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean validate() {

        boolean valid = true;

        if (strName.isEmpty() || strName.length() > 30) {

            etName.setError("Introduceti un nume valid");
            valid = false;
        }


        if (TextUtils.isEmpty(strLN) || strLN.length() > 30) {
            LastName.setError("Introduceti un nume valid ");
            valid = false;
        }

        if (TextUtils.isEmpty(strEmail) || !isEmailValid(strEmail) || !strEmail.contains("@stud.ase.ro")) {
            Email.setError("Introduceti o adresa de email institutionala valida ex: student@stud.ase.ro");
            valid = false;
        }

        if (TextUtils.isEmpty(strPass) || strPass.length() < 6) {

            Password.setError("Parola trebuie sa contina minim 6 caractere");
            valid = false;
        }

        if (TextUtils.isEmpty(strCP) || strCP.length() < 6) {
            //Toast.makeText(this, "Introduceti parola",Toast.LENGTH_SHORT).show();
            Password.setError("Parola trebuie sa contina minim 6 caractere");
            valid = false;
        }

        if (!strCP.equals(strPass)) {
            Toast.makeText(getApplicationContext(), "Cele doua parole nu corespund!", Toast.LENGTH_SHORT).show();
            // ConfPassword.setError("Cele doua parole trebuie sa corespunda");
            valid = false;
        }

        return valid;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}