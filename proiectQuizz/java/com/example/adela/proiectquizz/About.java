package com.example.adela.proiectquizz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adela.proiectquizz.authentication.MainActivity;
import com.example.adela.proiectquizz.main.MeniuProfesori;
import com.example.adela.proiectquizz.main.MeniuStudenti;
import com.example.adela.proiectquizz.main.MeniuStudenti;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class About extends AppCompatActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView tvAbout =findViewById(R.id.tvAbout);
        TextView tvVersiune =findViewById(R.id.tvVersiune);
        TextView tvEchipa =findViewById(R.id.tvEchipa);
        TextView tvAdresa =findViewById(R.id.tvAdresa);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setMinZoomPreference(14);
        LatLng ase_cibe = new LatLng( 44.448034,  26.098970);
        mMap.addMarker(new MarkerOptions().position(ase_cibe).title("CSIE"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ase_cibe, 10F));


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

                Intent intent1 = new Intent(About.this, MeniuStudenti.class);
                startActivity(intent1);
                break;
            case  R.id.menuLogOut:
                //aici trebuie sa pun clasa unde se face logarea sau cea de welcome
                Intent intent2= new Intent(About.this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.menuAbout:
                //nu stiu dc nu merge
                Toast.makeText(About.this,"Te afli deja in activitatea About",Toast.LENGTH_LONG).show();
                break;
            case R.id.menuExit:
                moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
