package com.example.adela.proiectquizz.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adela.proiectquizz.About;
import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.authentication.MainActivity;
import com.example.adela.proiectquizz.pojos.DummyApiModel;
import com.example.adela.proiectquizz.profesori.IstoricTesteCreateActivity;
import com.example.adela.proiectquizz.profesori.ResultsActivity;
import com.example.adela.proiectquizz.profesori.TestsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MeniuProfesori extends AppCompatActivity implements View.OnClickListener {

    private Button btn_tests, btn_results, btn_istoric;
    private TextView nameTv;

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

                Toast.makeText(MeniuProfesori.this,"Te afli deja in Home Teacher!",Toast.LENGTH_LONG).show();
                break;
            case  R.id.menuLogOut:
                //aici trebuie sa pun clasa unde se face logarea sau cea de welcome
                Intent intent2= new Intent(MeniuProfesori.this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.menuAbout:
                //nu stiu dc nu merge
                Intent intent= new Intent(MeniuProfesori.this,About.class);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meniu_profesori);

        nameTv = findViewById(R.id.nameTv);

        btn_results = (Button) findViewById(R.id.btn_results);
        btn_results.setOnClickListener(this);

        btn_tests = (Button) findViewById(R.id.btn_tests);
        btn_tests.setOnClickListener(this);

        btn_istoric = (Button) findViewById(R.id.btn_istoric);
        btn_istoric.setOnClickListener(this);

        URL url = null;
        try {
            url = new URL("https://jsonplaceholder.typicode.com/users/1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if(url != null) {
            new AsyncTaskHelper(nameTv).execute(url);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_results:
                startActivity(new Intent(this, ResultsActivity.class));
                break;

            case R.id.btn_tests:
                startActivity(new Intent(this, TestsActivity.class));
                break;
            case R.id.btn_istoric:
                startActivity(new Intent(this, IstoricTesteCreateActivity.class));
                break;
        }
    }

    private class AsyncTaskHelper extends AsyncTask<URL, Integer, String> {

        private TextView textView;

        public AsyncTaskHelper(TextView textView) {
            this.textView = textView;
        }

        protected String doInBackground(URL... urls) {
            String name = "Loading...";
            try {
                URL url = urls[0];
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                JSONObject jsonObject = new JSONObject(builder.toString());
                name = jsonObject.getString("name");

                urlConnection.disconnect();
            } catch (IOException | JSONException e)

            {
                e.printStackTrace();
            }
            return name;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String model) {
            textView.setText(model);
        }


    }
}
