package com.appradar.viper.moovon;

import android.app.Application;
import android.os.Handler;

/**
 * Created by viper on 18/09/16.
 */
public class GlobalApplication extends Application {

    private static GlobalApplication mInstance;
    public static volatile Handler applicationHandler = null;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

    }

    public static synchronized GlobalApplication getInstance()
    {
        return mInstance;
    }
}
