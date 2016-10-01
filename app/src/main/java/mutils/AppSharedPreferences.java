package mutils;

import android.content.Context;
import android.content.SharedPreferences;

import com.appradar.viper.moovon.GlobalApplication;

/**
 * Created by viper on 29/09/16.
 */
public class AppSharedPreferences {
    SharedPreferences mSharedPref;
    SharedPreferences.Editor mEditor;

    public static String SETTING_AGE = "SettingAge";
    public static String SETTING_GENDER = "SettingGender";
    public static String SETTING_TARGET_MOVE = "Setting_targe_move";
    public static String SETTING_TARGET_DRINK = "Setting_target_drink";

    public static String SETTING_WATER_FREQ = "SettingWater";
    public static String SETTING_MOVEMENT_FREQ = "SettingMovement";
    public static String APP_LAUNCH_FLAG = "launhFlag";


    String SHARED_PREF_NAME = "SETTINGS";

    private static AppSharedPreferences mInstance;


    private AppSharedPreferences()
    {
        mSharedPref = GlobalApplication.getInstance().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPref.edit();
    }

    public static AppSharedPreferences getInstance(){
        if (mInstance == null) mInstance = new AppSharedPreferences();
        return mInstance;
    }


    public int getPropInteger(String keyName)
    {
        return mSharedPref.getInt(keyName, 0);
    }

    public String getPropString(String keyName)
    {
        return mSharedPref.getString(keyName, "None");
    }

    public void setPropInteger(String keyName, int value)
    {
        mEditor.putInt(keyName, value);
        mEditor.commit();
    }

    public void setPropString(String keyName, String value)
    {
        mEditor.putString(keyName, value);
        mEditor.commit();
    }

}
