package mutils;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.appradar.viper.moovon.R;
import com.appradar.viper.moovon.SettingsActivity;

/**
 * Created by viper on 29/09/16.
 */
public class DrinkWaterService extends IntentService {

    private int NotificationId = 1;

    public DrinkWaterService() {
        super("DrinkWaterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Do the task here
        int alert_type = intent.getIntExtra(SettingsActivity.ALERT_TYPE, 1);
        Log.i("DrinkWaterService", "Service called for alert type : " + alert_type);

        Intent actintent = new Intent(this, SettingsActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 1,
                actintent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        String alertText = "Drinking water alert";
        builder.setContentText(alertText);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setSmallIcon( R.mipmap.ic_launcher );
        builder.setContentTitle( getString( R.string.app_name ) );
        builder.setContentIntent(pIntent);
        NotificationManagerCompat.from(this).notify(1, builder.build());
    }
}