package com.example.athree_MeisheSdk;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

//import com.coloros.mcssdk.PushManager;
//import com.coloros.mcssdk.callback.PushCallback;
//import com.coloros.mcssdk.mode.SubscribeResult;
import com.example.athree_MeisheSdk.utils.asset.NvAssetManager;
import com.jw.xfkplugin.BuildConfig;
import com.jw.xfkplugin.PreferencesUtils;
import com.meicam.sdk.NvsStreamingContext;

import java.util.List;

import io.dcloud.weex.AppHookProxy;
/**
 * Created by wj-mac on 2019/10/27.
 */

public class MSHookProxy implements AppHookProxy{


    private static final String TAG = "####IM###";

    @Override
    public  void onCreate(Application application)
    {

        mContext = application.getApplicationContext( );


        /*
         * 初始化
         * initialization
         * */


        String licensePath = "assets:/ticiSDK3.lic";
        NvsStreamingContext.init(mContext, licensePath,0);
        NvAssetManager.init(mContext);
//        Toast.makeText(mContext,"MSHookProxy2",Toast.LENGTH_LONG).show();

        PreferencesUtils.init(mContext, BuildConfig.APPLICATION_ID);

    }


    private static Context mContext;

    public static Context getmContext() {
        return mContext;
    }


}
