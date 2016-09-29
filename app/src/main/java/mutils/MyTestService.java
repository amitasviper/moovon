package mutils;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.appradar.viper.moovon.R;

/**
 * Created by viper on 29/09/16.
 */
public class MyTestService extends IntentService {

    private int NotificationId = 1;

    public MyTestService() {
        super("MyTestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Do the task here
        Log.i("MyTestService", "Service running");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentText("Drink water alert");
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setSmallIcon( R.mipmap.ic_launcher );
        builder.setContentTitle( getString( R.string.app_name ) );
        NotificationManagerCompat.from(this).notify(NotificationId++, builder.build());
    }
}