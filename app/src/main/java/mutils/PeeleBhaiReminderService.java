package mutils;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by viper on 28/09/16.
 */
public class PeeleBhaiReminderService extends IntentService {

    public PeeleBhaiReminderService() {
        super("PeeleBhaiReminderService");
    }

    public PeeleBhaiReminderService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
