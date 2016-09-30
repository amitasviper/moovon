package com.appradar.viper.moovon;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import mutils.UserProgress;

public class DashBoard extends AppCompatActivity {

    private  ArrayList<WaterDrinkInfo> listWaterDrinkInfo = new ArrayList<WaterDrinkInfo>();
    private ArrayList<WalkTimeInfo> listWalkIimeInfo =  new ArrayList<WalkTimeInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

//        listWaterDrinkInfo = new ArrayList<WaterDrinkInfo>();
//        listWalkIimeInfo = new ArrayList<WalkTimeInfo>();

        final RadioGroup rgbDrinkWaterProgress = (RadioGroup) findViewById(R.id.rgbDrinkWaterProgress);

        rgbDrinkWaterProgress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                // Method 1 For Getting Index of RadioButton
               int pos=rgbDrinkWaterProgress.indexOfChild(findViewById(checkedId));
                switch (pos)
                {
                    case 0 :

                        break;
                    case 1 :

                        break;
                    case 2 :

                        break;
                    default :
                        //The default selection is RadioButton 1

                        break;
                }
            }
        });

        // create walk info chart
        //CreateWalkProgressChart();

        getDailyWalkReport();

        // create water drink progress chart
        //CreateWaterDrinkProgressChart();
        getDailyWaterReport();


    }


//    public void getWaterReport(int nDays){
//
//        // to do - days diff is not correctly handled
//        // it
//
//        final UserProgress up = UserProgress.getInstance();
//        String year = String.valueOf(up.getCurrentYear());
//        String month = String.valueOf(up.getCurrentMonth());
//
//        Calendar c = Calendar.getInstance();
//
//        int hours = c.get(Calendar.HOUR);
//        int minutes = c.get(Calendar.MINUTE);
//
//
//        for(int day=1; day <= nDays; day++)
//        {
//            final int nDate = day;
//            up.getMonthlyProgressReference(year, month, up.rootChal).getRef().child(String.valueOf(day)).child(up.childCount).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if(dataSnapshot.exists() )
//                    {
//                        long consumptionCount = dataSnapshot.getValue(long.class);
//                        // process data here
//
//                        listWaterDrinkInfo.add(new WaterDrinkInfo((float)nDate, consumptionCount));
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//
//
//            });
//        }
//        CreateWaterDrinkProgressChart();
//    }

    public void getDailyWalkReport(){

        final UserProgress up = UserProgress.getInstance();
        String year = String.valueOf(up.getCurrentYear());
        String month = String.valueOf(up.getCurrentMonth());
        String day = String.valueOf(up.getCurrentDay());



        UserProgress.getDailyProgressReference(year, month, day, up.rootChal).getRef().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if( dataSnapshot.getKey().equals(up.childCount))
                {

                }
                else
                {
                    String timeKey = dataSnapshot.getKey();
                    String hours = up.getHoursFromKey(timeKey);
                    String minutes = up.getMinutesFromKey(timeKey);
                    long countOnTimeKey = dataSnapshot.getValue(long.class);

                    float fMinuteInPercentile = (float) (Float.parseFloat(minutes) / 60.0);
                    float fLastMinuteCheck = Integer.parseInt(hours) + fMinuteInPercentile;

                    listWalkIimeInfo.add(new WalkTimeInfo(fLastMinuteCheck, countOnTimeKey));

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        UserProgress.getDailyProgressReference(year, month, day, UserProgress.getInstance().rootChal).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CreateWalkProgressChart();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void getDailyWaterReport()
    {
        final UserProgress up = UserProgress.getInstance();
        String year = String.valueOf(up.getCurrentYear());
        String month = String.valueOf(up.getCurrentMonth());
        String day = String.valueOf(up.getCurrentDay());


        UserProgress.getDailyProgressReference(year, month, day, up.rootPaani).getRef().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if( dataSnapshot.getKey().equals(up.childCount))
                {

                }
                else
                {
                    String timeKey = dataSnapshot.getKey();
                    String hours = up.getHoursFromKey(timeKey);
                    String minutes = up.getMinutesFromKey(timeKey);
                    long countOnTimeKey = dataSnapshot.getValue(long.class);

                    float fMinuteInPercentile = (float) (Float.parseFloat(minutes) / 60.0);
                    float fLastMinuteCheck = Integer.parseInt(hours) + fMinuteInPercentile;

                    listWaterDrinkInfo.add(new WaterDrinkInfo(fLastMinuteCheck, countOnTimeKey));

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        UserProgress.getDailyProgressReference(year, month, day, UserProgress.getInstance().rootPaani).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CreateWaterDrinkProgressChart();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void CreateWaterDrinkProgressChart(){


        List<Entry> entries = new ArrayList<Entry>();


        for (WaterDrinkInfo data : listWaterDrinkInfo) {

            // turn your data into Entry objects
            entries.add(new Entry(data.getnHour(), data.getfWaterVolumeInML()));
        }

        //Activity activity, List<Entry> entries, String sDescription, String sIntervalType)
        CustomLineChart customLineChart = new CustomLineChart(this, entries, "Water drink progress in milliliter", "hours", (LineChart) findViewById(R.id.chartDrinkWaterProgress));
        customLineChart.createChart();

    }

    public void CreateWalkProgressChart(){


        List<Entry> entries = new ArrayList<Entry>();

        for (WalkTimeInfo data : listWalkIimeInfo) {

            // turn your data into Entry objects
            entries.add(new Entry(data.getnHour(), data.getfDistance()));
        }

        //Activity activity, List<Entry> entries, String sDescription, String sIntervalType)
        CustomLineChart customLineChart = new CustomLineChart(this, entries, "Walk progress in meter", "hours", (LineChart) findViewById(R.id.chartWalkProgress));
        customLineChart.createChart();

    }

}


class WaterDrinkInfo{

    private  float ftime;
    private long lWaterVolumeInML;

    public WaterDrinkInfo(float ftime, long lWaterVolumeInML) {
        this.ftime = ftime;
        this.lWaterVolumeInML = lWaterVolumeInML;
    }

    public float getnHour() {
        return ftime;
    }

    public void setnHour(int nHour) {
        this.ftime = nHour;
    }

    public long getfWaterVolumeInML() {
        return lWaterVolumeInML;
    }

    public void setfWaterVolumeInML(float fWaterVolumeInML) {
        this.lWaterVolumeInML = lWaterVolumeInML;
    }
}





class WalkTimeInfo{

    private  float fTime;
    private long nDistance;

    public WalkTimeInfo(float fHour, long nDistance) {
        this.fTime = fHour;
        this.nDistance = nDistance;
    }

    public float getnHour() {
        return fTime;
    }

    public void setnHour(float nHour) {
        this.fTime = nHour;
    }

    public long getfDistance() {
        return nDistance;
    }

    public void setfDistance(long fDistance) {
        this.nDistance = fDistance;
    }
}
