package com.appradar.viper.moovon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mutils.AppSharedPreferences;
import mutils.DrinkWaterAlarmReceiver;

public class SettingsActivity extends AppCompatActivity {

    EditText et_gender, et_age, et_water_freq, et_movement_freq;

    Button btn_save_settings;

    public static int ALERT_WATER = 1;
    public static int ALERT_MOVE = 2;

    public static String ALERT_TYPE = "ALERT_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //To make views move up when soft keyboard is opened
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        initViews();
    }

    public void cancelAlarm(int alertType) {
        Intent intent = new Intent(getApplicationContext(), DrinkWaterAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this,alertType,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

    public void scheduleAlarm(int alertType, int seconds) {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), DrinkWaterAlarmReceiver.class);
        intent.putExtra(ALERT_TYPE, alertType);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, alertType,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        Log.e("scheduleAlarm", "Time in secs is : " + seconds);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                1000 * seconds, pIntent);

    }

    private void initViews()
    {
        et_gender = (EditText) findViewById(R.id.et_setting_gender);
        et_age = (EditText) findViewById(R.id.et_setting_age);
        et_water_freq = (EditText) findViewById(R.id.et_setting_water_freq);
        et_movement_freq = (EditText) findViewById(R.id.et_setting_movement_freq);

        btn_save_settings = (Button) findViewById(R.id.btn_setting_save);

        final AppSharedPreferences mAppSharedPreferences = AppSharedPreferences.getInstance();

        et_gender.setText("" +mAppSharedPreferences.getPropString(AppSharedPreferences.SETTING_GENDER));
        et_age.setText("" + mAppSharedPreferences.getPropInteger(AppSharedPreferences.SETTING_AGE));
        et_water_freq.setText("" + mAppSharedPreferences.getPropInteger(AppSharedPreferences.SETTING_WATER_FREQ));
        et_movement_freq.setText("" + mAppSharedPreferences.getPropInteger(AppSharedPreferences.SETTING_MOVEMENT_FREQ));

        btn_save_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String gender = et_gender.getText().toString();
                    int age = Integer.parseInt(et_age.getText().toString());
                    int movement = Integer.parseInt(et_movement_freq.getText().toString());
                    int water = Integer.parseInt(et_water_freq.getText().toString());

                    if (gender.isEmpty() || age < 1 || movement < 1 || water < 1)
                    {
                        Toast.makeText(SettingsActivity.this, "Please set all fields correctly", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mAppSharedPreferences.setPropInteger(AppSharedPreferences.SETTING_AGE, age);
                    mAppSharedPreferences.setPropInteger(AppSharedPreferences.SETTING_MOVEMENT_FREQ, movement);
                    mAppSharedPreferences.setPropInteger(AppSharedPreferences.SETTING_WATER_FREQ, water);
                    mAppSharedPreferences.setPropString(AppSharedPreferences.SETTING_GENDER, gender);

                    cancelAlarm(ALERT_WATER);
                    scheduleAlarm(ALERT_WATER, water);

                    cancelAlarm(ALERT_MOVE);
                    scheduleAlarm(ALERT_MOVE, movement);

                    Toast.makeText(SettingsActivity.this, "Settings saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                catch (Exception ex)
                {
                    Toast.makeText(SettingsActivity.this, "Please set all fields correctly", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }



}
