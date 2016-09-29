package mutils;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.appradar.viper.moovon.R;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by viper on 28/09/16.
 */
public class RecognizeMotionService extends IntentService {

    public static Map<Integer, String> ActivityNames = new HashMap<Integer, String>();

    public RecognizeMotionService() {
        super("RecognizeMotionService");
        ActivityNames.put(DetectedActivity.IN_VEHICLE, "In Vehicle");
        ActivityNames.put(DetectedActivity.ON_BICYCLE, "Bicycle");
        ActivityNames.put(DetectedActivity.ON_FOOT, "On Foot");
        ActivityNames.put(DetectedActivity.RUNNING, "Walking");
        ActivityNames.put(DetectedActivity.STILL, "Still");
        ActivityNames.put(DetectedActivity.WALKING, "Walking");
        ActivityNames.put(DetectedActivity.UNKNOWN, "Unknown");
    }

    public RecognizeMotionService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities( result.getProbableActivities() );

            DetectedActivity activity = result.getMostProbableActivity();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentText( ActivityNames.get(activity.getType()));
            builder.setSmallIcon( R.mipmap.ic_launcher );
            builder.setContentTitle( getString( R.string.app_name ) );
            NotificationManagerCompat.from(this).notify(0, builder.build());
        }
    }


    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for( DetectedActivity activity : probableActivities ) {
            switch( activity.getType() ) {
                case DetectedActivity.IN_VEHICLE: {
                    Log.e( "ActivityRecogition", "In Vehicle: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    Log.e( "ActivityRecogition", "On Bicycle: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.ON_FOOT: {
                    Log.e( "ActivityRecogition", "On Foot: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.RUNNING: {
                    Log.e( "ActivityRecogition", "Running: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.STILL: {
                    Log.e( "ActivityRecogition", "Still: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.TILTING: {
                    Log.e( "ActivityRecogition", "Tilting: " + activity.getConfidence() );
                    break;
                }
                case DetectedActivity.WALKING: {
                    Log.e( "ActivityRecogition", "Walking: " + activity.getConfidence() );
                    /*if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText( "Are you walking?" );
                        builder.setSmallIcon( R.mipmap.ic_launcher );
                        builder.setContentTitle( getString( R.string.app_name ) );
                        NotificationManagerCompat.from(this).notify(0, builder.build());
                    }*/
                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    Log.e( "ActivityRecogition", "Unknown: " + activity.getConfidence() );
                    break;
                }
            }
        }


    }
}
