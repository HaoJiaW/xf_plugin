package com.jw.tcdialogplugin.uni;

import android.app.Application;
import android.content.Context;


import com.jw.tcdialogplugin.PreferencesUtils;
import com.taobao.weex.BuildConfig;

import io.dcloud.weex.AppHookProxy;

//import com.coloros.mcssdk.PushManager;
//import com.coloros.mcssdk.callback.PushCallback;
//import com.coloros.mcssdk.mode.SubscribeResult;

/**
 * Created by wj-mac on 2019/10/27.
 */

public class DialogHookProxy implements AppHookProxy{


    private static final String TAG = "####IM###";

    @Override
    public  void onCreate(Application application)
    {
        mContext = application.getApplicationContext( );
        /*
         * 初始化
         * initialization
         * */
//        Toast.makeText(mContext,"MSHookProxy2",Toast.LENGTH_LONG).show();
        PreferencesUtils.init(mContext, BuildConfig.APPLICATION_ID);
    }


    private static Context mContext;

    public static Context getmContext() {
        return mContext;
    }


}
