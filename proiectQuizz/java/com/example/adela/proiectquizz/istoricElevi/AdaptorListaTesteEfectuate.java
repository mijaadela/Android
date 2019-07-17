package com.example.adela.proiectquizz.istoricElevi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.pojos.RezultatTest;

import java.util.ArrayList;
import java.util.List;

class ViewHolder{

};



public class AdaptorListaTesteEfectuate extends ArrayAdapter<RezultatTest> {

    private Context mContext;
    int mResource;
    private List<RezultatTest> rezultatTestList;

    public AdaptorListaTesteEfectuate( Context context, int resource, List<RezultatTest> objects) {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
        rezultatTestList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String teste = rezultatTestList.get(position).getName();
        String nrIntrebari = rezultatTestList.get(position).getNrIntrebari();
        String nrIntrebariCorecte = rezultatTestList.get(position).getNrIntrebariCorecte();
        String timp = rezultatTestList.get(position).getTimp();
        String materieTest = rezultatTestList.get(position).getMaterieTest();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTitlu= (TextView)convertView.findViewById(R.id.tvTitluTestEfectuat);
        TextView tvNrIntrebari= (TextView)convertView.findViewById(R.id.tvNrIntrebariTestEfectuat);
        TextView tvTimp= (TextView)convertView.findViewById(R.id.tvTimpTestEfectuat);
        tvTitlu.setText(teste + " - " + materieTest);
        tvNrIntrebari.setText("Nr intrebari corecte: "+nrIntrebariCorecte + " din " + nrIntrebari);
        tvTimp.setText("Timp test: "+timp);

        return convertView;


    }

}
