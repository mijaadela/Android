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

public class SingUpTeacher extends AppCompatActivity {

    QuizDbHelper dbHelper;
    SQLiteDatabase db;

    private EditText Name;
    private EditText LastName;
    private Spinner catedra;
    private EditText Email;
    private EditText Password;
    private EditText ConfPassword;
    private Button create;

    private String strName, strLN, strEmail, strPass, strCP;
    private String department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_teacher);

        dbHelper = new QuizDbHelper(this);
        db = dbHelper.getWritableDatabase();

        Name = (EditText) findViewById(R.id.etFirstNameT);
        LastName = (EditText) findViewById(R.id.etLastNameT);
        Email = (EditText) findViewById(R.id.etEmailT);
        Password = (EditText) findViewById(R.id.etPasswordT);
        ConfPassword = (EditText) findViewById(R.id.etConfirmPasswordT);


        catedra = (Spinner) findViewById(R.id.spCatedre);
        ArrayList<String> list = new ArrayList<>();
        list.add("Informatica si Cibernetica Economica");
        list.add("Statistica si Econemetrie");
        list.add("Matematici Aplicate");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catedra.setAdapter(adapter);


        create = (Button) findViewById(R.id.btnCreateTeacherAccount);


        //BUTONUL CATRE CONTUL PROFESORULUI

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initializeT();
                if (validateT()) {
                    insertData(strName, strLN, department, strEmail, strPass);
                    goToLogin();
                } else {
                    Toast.makeText(getApplicationContext(), "Inregistrarea a esuat!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void initializeT() {
        strName = Name.getText().toString();
        strLN = LastName.getText().toString();
        strPass = Password.getText().toString();
        strEmail = Email.getText().toString();
        strCP = ConfPassword.getText().toString();
        department = catedra.getSelectedItem().toString();
    }

    public void insertData(String strName, String strLN, String department, String strEmail, String strPass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuizContract.UsersTable.COLUMN_NAME, strName);
        contentValues.put(QuizContract.UsersTable.COLUMN_SURNAME, strLN);
        contentValues.put(QuizContract.UsersTable.COLUMN_DEPARTMENT, department);
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


    public boolean validateT() {

        boolean valid = true;
        if (strName.isEmpty() || strName.length() > 30) {

            Name.setError("Introduceti un nume valid");
            valid = false;
        }


        if (TextUtils.isEmpty(strLN) || strLN.length() > 30) {
            LastName.setError("Introduceti un nume valid ");
            valid = false;
        }

        if (TextUtils.isEmpty(strEmail) || !isEmailValid(strEmail) || !strEmail.contains("@ie.ase.ro")) {
            Email.setError("Introduceti o adresa de email institutionala valida ex: Profesor@ie.ase.ro");
            valid = false;
        }

        if (TextUtils.isEmpty(strPass) || strPass.length() < 6) {

            Password.setError("Parola trebuie sa contina minim 6 caractere");
            valid = false;
        }

        if (TextUtils.isEmpty(strCP) || strCP.length() < 6) {

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

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
