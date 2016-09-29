package com.appradar.viper.moovon;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mutils.GPSTracker;

public class GpsTrackerActivity extends AppCompatActivity {

    Button btnShowLocation;

    // GPSTracker class

    TextView textViewLocation;
    Location location1;
    Button buttonStart,buttonStop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_tracker);
        textViewLocation = (TextView) findViewById(R.id.textView_timer);


        buttonStop=(Button)findViewById(R.id.button2);
        buttonStart=(Button)findViewById(R.id.button);
        buttonStop.setEnabled(false);

        // show location button click event
        buttonStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                try {
                    GPSTracker gps = new GPSTracker(GpsTrackerActivity.this);

                    location1 = new Location("");
                    location1.setLatitude(gps.getLatitude());
                    location1.setLongitude(gps.getLongitude());
                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);
                }

                catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                GPSTracker gps = new GPSTracker(GpsTrackerActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){
                    Location location2 = new Location("");
                    location2.setLatitude(gps.getLatitude());
                    location2.setLongitude(gps.getLongitude());

                    if(location2.getAccuracy()<10)
                    {
                        double distanceInMeters = location1.distanceTo(location2);
                        String text = String.valueOf(distanceInMeters);
                        textViewLocation.setText(text);

                    }

                }
                else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
            }
        });
    }
}
