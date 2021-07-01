package com.jw.tcdsappmaster;

import android.app.Application;

import com.jw.tcdialogplugin.PreferencesUtils;

//import com.jw.xfkplugin.PreferencesUtils;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesUtils.init(this,BuildConfig.APPLICATION_ID);
        com.jw.xfkplugin.PreferencesUtils.init(this,BuildConfig.APPLICATION_ID);
    }
}
