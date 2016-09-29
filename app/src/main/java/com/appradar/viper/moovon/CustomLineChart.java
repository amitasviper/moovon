package com.appradar.viper.moovon;

import android.app.Activity;
import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viper on 29/9/16.
 */
public class CustomLineChart {

    private Activity activity;
    private List<Entry> entries;
    private String sDescription;
    private ArrayList<String> listTimeFormatter;
    private LineChart chart;

    String sIntervalType;

    public CustomLineChart(Activity activity, List<Entry> entries, String sDescription, String sIntervalType, LineChart chart) {
        this.activity = activity;
        this.entries = entries;
        this.sDescription = sDescription;
        this.sIntervalType = sIntervalType;
        this.listTimeFormatter = new ArrayList<String >();
        this.chart = chart;
    }


    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    private void GetTimeLineFormatter()
    {
        if(sIntervalType.toLowerCase() == "hours")
        {
            for (int i=1; i<=24; i++)
            {
                int nHour = i;
                String sAMorPM = "AM";
                if(i > 12)
                {
                    nHour = i - 12;
                    sAMorPM = "PM";
                }
                listTimeFormatter.add("" + nHour + "" + sAMorPM);
            }

        }

    }


    public void createChart(){

        GetTimeLineFormatter();

        //LineChart chart = (LineChart) activity.findViewById(R.id.chart);

        LineDataSet dataSet = new LineDataSet(entries, "lable"); // add entries to dataset
        int myColor = activity.getResources().getColor(R.color.colorGreen);
        dataSet.setColor(myColor);
        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorAccent)); // styling, ...


        LineData lineData = new LineData(dataSet);
        chart.setDescription(sDescription);
        chart.setNoDataTextDescription("No activity found");
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);;
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        chart.setDrawGridBackground(true);

        chart.setData(lineData);
        chart.invalidate(); // refresh


        AxisValueFormatter formatter = new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return listTimeFormatter.get((int) value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        };


        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);

    }
}

//// x and y value for chart
//
//class ChartValues{
//
//    private  int x;
//    private float y;
//
//    public ChartValues(int x, float y) {
//        this.nHour = nHour;
//        this.fDistance = fDistance;
//    }
//
//    public int getnHour() {
//        return nHour;
//    }
//
//    public void setnHour(int nHour) {
//        this.nHour = nHour;
//    }
//
//    public float getfDistance() {
//        return fDistance;
//    }
//
//    public void setfDistance(float fDistance) {
//        this.fDistance = fDistance;
//    }
//}
