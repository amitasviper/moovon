package com.appradar.viper.moovon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        // create walk info chart
        CreateWalkProgressChart();

        // create water drink progress chart
        CreateWaterDrinkProgressChart();


    }

    public void CreateWaterDrinkProgressChart(){

        ArrayList<WaterDrinkInfo> timeLine = new ArrayList<WaterDrinkInfo>();

        timeLine.add(new WaterDrinkInfo(9,100f));
        timeLine.add(new WaterDrinkInfo(11,200f));
        timeLine.add(new WaterDrinkInfo(13,100f));

        List<Entry> entries = new ArrayList<Entry>();

        for (WaterDrinkInfo data : timeLine) {

            // turn your data into Entry objects
            entries.add(new Entry(data.getnHour(), data.getfWaterVolumeInML()));
        }

        //Activity activity, List<Entry> entries, String sDescription, String sIntervalType)
        CustomLineChart customLineChart = new CustomLineChart(this, entries, "Water drink progress in milliliter", "hours", (LineChart) findViewById(R.id.chartDrinkWaterProgress));
        customLineChart.createChart();

    }

    public void CreateWalkProgressChart(){

        ArrayList<WalkTimeInfo> timeLine = new ArrayList<WalkTimeInfo>();

        timeLine.add(new WalkTimeInfo(9,1f));
        timeLine.add(new WalkTimeInfo(11,2f));
        timeLine.add(new WalkTimeInfo(13,3f));

        List<Entry> entries = new ArrayList<Entry>();

        for (WalkTimeInfo data : timeLine) {

            // turn your data into Entry objects
            entries.add(new Entry(data.getnHour(), data.getfDistance()));
        }

        //Activity activity, List<Entry> entries, String sDescription, String sIntervalType)
        CustomLineChart customLineChart = new CustomLineChart(this, entries, "Walk progress in kilometer", "hours", (LineChart) findViewById(R.id.chartWalkProgress));
        customLineChart.createChart();

    }

}


class WaterDrinkInfo{

    private  int nHour;
    private float fWaterVolumeInML;

    public WaterDrinkInfo(int nHour, float fWaterVolumeInML) {
        this.nHour = nHour;
        this.fWaterVolumeInML = fWaterVolumeInML;
    }

    public int getnHour() {
        return nHour;
    }

    public void setnHour(int nHour) {
        this.nHour = nHour;
    }

    public float getfWaterVolumeInML() {
        return fWaterVolumeInML;
    }

    public void setfWaterVolumeInML(float fWaterVolumeInML) {
        this.fWaterVolumeInML = fWaterVolumeInML;
    }
}





class WalkTimeInfo{

    private  int nHour;
    private float fDistance;

    public WalkTimeInfo(int nHour, float fDistance) {
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
