package com.appradar.viper.moovon;

import android.app.Activity;
import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viper on 29/9/16.
 */
public class CustomLineChart {

    private Activity activity;
    private List<Entry> entries;

    public CustomLineChart(Activity activity,List<Entry> entries) {
        this.activity = activity;
        this.entries = entries;
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



    public void createChart(){

        LineChart chart = (LineChart) activity.findViewById(R.id.chart);

        ArrayList<WalkTimeInfo> timeLine = new ArrayList<WalkTimeInfo>();

        timeLine.add(new WalkTimeInfo(9,1f));
        timeLine.add(new WalkTimeInfo(11,2f));
        timeLine.add(new WalkTimeInfo(13,3f));


        List<Entry> entries = new ArrayList<Entry>();

        for (WalkTimeInfo data : timeLine) {

            // turn your data into Entry objects
            entries.add(new Entry(data.getnHour(), data.getfDistance()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Walk"); // add entries to dataset
        int myColor = activity.getResources().getColor(R.color.colorGreen);
        dataSet.setColor(myColor);
        dataSet.setValueTextColor(activity.getResources().getColor(R.color.colorAccent)); // styling, ...


        LineData lineData = new LineData(dataSet);
        chart.setDescription("Walk Progres");
        chart.setNoDataTextDescription("No activity found");
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);;
        chart.setScaleEnabled(true);
        chart.setPinchZoom(true);
        chart.setDrawGridBackground(true);



        chart.setData(lineData);
        chart.invalidate(); // refresh


        final String[] hourTimeLine = new String[24];

        for (int i=1; i<=24; i++) {

            int nHour = i;
            String sAMORPM = "AM";
            if(i > 12)
            {
                nHour = i - 12;
                sAMORPM = "PM";
            }

            hourTimeLine[i - 1] =  "" + nHour + "" + sAMORPM;
        }

        AxisValueFormatter formatter = new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return hourTimeLine[(int) value];
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

// x and y value for chart

class ChartValues{

    private  int x;
    private float y;

    public ChartValues(int x, float y) {
        this.nHour = nHour;
        this.fDistance = fDistance;
    }

    public int getnHour() {
        return nHour;
    }

    public void setnHour(int nHour) {
        this.nHour = nHour;
    }

    public float getfDistance() {
        return fDistance;
    }

    public void setfDistance(float fDistance) {
        this.fDistance = fDistance;
    }
}
