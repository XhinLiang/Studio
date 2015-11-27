package com.wecan.xhin.baselib.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.wecan.xhin.baselib.R;


/**
 * Created by Xhin on 2015/7/24.
 * XhinLiang@gmail.com
 * SharePreference的帮助类，使用单例模式
 */
public class PreferenceHelper {


    private static PreferenceHelper sPreferenceHelper;
    private SharedPreferences mySharedPreferences;


    //使用单例模式
    public static PreferenceHelper getInstance(Context context) {
        if (sPreferenceHelper == null) {
            sPreferenceHelper = new PreferenceHelper();
            sPreferenceHelper.mySharedPreferences = context.getApplicationContext()
                    .getSharedPreferences(context.getString(R.string.app_name), Activity.MODE_PRIVATE);
        }
        return sPreferenceHelper;
    }

    public void saveParam(String key, String value) {
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveParam(String key, boolean value) {
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean(key, value).apply();
    }

    public void saveParam(String key, int value) {
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putInt(key, value).apply();
    }

    public String getString(String key, String defaultString) {
        return mySharedPreferences.getString(key, defaultString);
    }

    public boolean getBoolean(String key, boolean defaultBool) {
        return mySharedPreferences.getBoolean(key, defaultBool);
    }

    public int getInt(String key, int defaultInt) {
        return mySharedPreferences.getInt(key, defaultInt);
    }


}
