package com.example.adela.proiectquizz;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.adela.proiectquizz.authentication.MainActivity;
import com.example.adela.proiectquizz.main.MeniuStudenti;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;

import java.util.ArrayList;

public class Student_grafice extends AppCompatActivity {
    BarChart barChart;
    float barWidth;
    float barSpace;
    float groupSpace;
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

                Intent intent1= new Intent(this,MeniuStudenti.class);
                startActivity(intent1);
                break;
            case  R.id.menuLogOut:
                //aici trebuie sa pun clasa unde se face logarea sau cea de welcome
                Intent intent2= new Intent(this,MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.menuAbout:
                //nu stiu dc nu merge
                Intent intent= new Intent(this,About.class);
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
        setContentView(R.layout.activity_student_grafice);

        barWidth = 0.3f;
        barSpace = 0f;
        groupSpace = 0.4f;

        //setari chart
        barChart = (BarChart)findViewById(R.id.barChart);
        barChart.setDescription(null);
        barChart.setPinchZoom(true);
        barChart.setScaleEnabled(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(true);
        barChart.setTouchEnabled(true);
        barChart.setDoubleTapToZoomEnabled(true);

        //valori pt graf
        int groupCount = 6;

        ArrayList xVals = new ArrayList();

        xVals.add("Test 1");
        xVals.add("Test 2");
        xVals.add("Test 3");
        xVals.add("Test 4");
        xVals.add("Test 5");
        xVals.add("Test 6");

        ArrayList yVals1 = new ArrayList();
        ArrayList yVals2 = new ArrayList();

        yVals1.add(new BarEntry(1, (float) 12));
        yVals2.add(new BarEntry(1, (float) 15));
        yVals1.add(new BarEntry(2, (float) 13));
        yVals2.add(new BarEntry(2, (float) 20));
        yVals1.add(new BarEntry(3, (float) 18));
        yVals2.add(new BarEntry(3, (float) 20));
        yVals1.add(new BarEntry(4, (float) 20));
        yVals2.add(new BarEntry(4, (float) 20));
        yVals1.add(new BarEntry(5, (float) 23));
        yVals2.add(new BarEntry(5, (float) 25));
        yVals1.add(new BarEntry(6, (float) 19));
        yVals2.add(new BarEntry(6, (float) 20));


        //desenare graf
        BarDataSet set1, set2;
        set1 = new BarDataSet(yVals1, "Intrebari corecte");
        set1.setColor(R.color.Blue);
        set2 = new BarDataSet(yVals2, "Numar de intrebari");
        set2.setColor(R.color.Red);
        BarData data = new BarData(set1, set2);
        data.setValueFormatter(new LargeValueFormatter());
        barChart.setData(data);
        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.getData().setHighlightEnabled(false);
        barChart.invalidate();

        //legenda
        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setYOffset(20f);
        l.setXOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        //axele

        //X-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setCenterAxisLabels(true);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisMaximum(6);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        //Y-axis
        barChart.getAxisRight().setEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(true);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f);



    }
}
