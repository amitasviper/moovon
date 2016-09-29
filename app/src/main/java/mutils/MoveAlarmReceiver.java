package mutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.appradar.viper.moovon.SettingsActivity;

/**
 * Created by viper on 29/09/16.
 */
public class MoveAlarmReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.appradar.viper.moovon.alarm.move";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MyTestService.class);
        i.putExtra(SettingsActivity.ALERT_TYPE, 2);
        Log.e("MoveAlarmReceiver", "Raising alarm : " + 2);
        context.startService(i);
    }
}