package com.wecan.xhin.studio.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by xhinliang on 15-12-21.
 * xhinliang@gmail.com
 */
public class TestActivity extends Activity {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPath();
        showMetaData();
    }

    private void showMetaData() {
        try {
            Log.d(TAG, getPackageManager()
                    .getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA)
                    .metaData.getString("UMENG_APPKEY"));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showPath() {
        Log.d(TAG, getFilesDir().getAbsolutePath());
        Log.d(TAG, getCacheDir().getAbsolutePath());
    }
}
