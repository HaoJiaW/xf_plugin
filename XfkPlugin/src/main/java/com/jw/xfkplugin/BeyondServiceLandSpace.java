package com.jw.xfkplugin;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

public class BeyondServiceLandSpace extends Service {

    private WindowManager windowManager;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showBeyondView();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void showBeyondView() {
        if (PopupManager.getInstance().beyondViewLandSpace == null) {
            PopupManager.getInstance().beyondViewLandSpace = new BeyondViewLandSpace(this);
            int bgColor = PreferencesUtils.getInt("bgColor", R.color.black);
            PopupManager.getInstance().beyondViewLandSpace.setBgColor(this.getResources().getColor(bgColor));
            int alpahVal = PreferencesUtils.getInt("alpahVal", 70);
            PopupManager.getInstance().beyondViewLandSpace.setBgAlpha((int) ((alpahVal / 100f) * 255));
        } else {
            PopupManager.getInstance().beyondViewLandSpace.initTcList();
        }
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            lp.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        lp.height = VarManger.beyondLandSpaceViewLpw != null ? VarManger.beyondLandSpaceViewLpw.height : (int) (displayMetrics.widthPixels );
//        lp.width = VarManger.beyondLandSpaceViewLpw != null ? VarManger.beyondLandSpaceViewLpw.width : (int)(displayMetrics.heightPixels*0.4) ;
//        lp.x = VarManger.beyondLandSpaceViewLpw != null ? VarManger.beyondLandSpaceViewLpw.x : VarManger.getSystemBarHeight(this);
//        lp.y = VarManger.beyondLandSpaceViewLpw != null ? VarManger.beyondLandSpaceViewLpw.y : 0;
        lp.height = PreferencesUtils.getInt("beyondViewLandSpaceH", (int) (displayMetrics.widthPixels * 0.8));
        lp.width = PreferencesUtils.getInt("beyondViewLandSpaceW", (int) (displayMetrics.heightPixels * 0.4));
        lp.x = PreferencesUtils.getInt("beyondViewLandSpaceX", VarManger.getSystemBarHeight(this));
        lp.y = PreferencesUtils.getInt("beyondViewLandSpaceY", 0);
//        PreferencesUtils.getInt("beyondViewLandSpaceX",VarManger.getSystemBarHeight(this));
//        PreferencesUtils.getInt("beyondViewLandSpaceY",0);
//        PreferencesUtils.getInt("beyondViewLandSpaceW",(int)(displayMetrics.heightPixels*0.4) );
//        PreferencesUtils.getInt("beyondViewLandSpaceH",(int) (displayMetrics.widthPixels ));
        lp.format = PixelFormat.RGBA_8888;
        lp.gravity = Gravity.TOP;
//        lp.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        PopupManager.getInstance().beyondViewLandSpace.isWindowMangerFlag = true;
        PopupManager.getInstance().beyondViewLandSpace.wm = windowManager;
        windowManager.addView(PopupManager.getInstance().beyondViewLandSpace, lp);
        VarManger.isXfkShow = true;
        VarManger.isXfkLandSpaceShow = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(PopupManager.getInstance().beyondViewLandSpace);
        VarManger.isXfkShow = false;
        VarManger.isXfkLandSpaceShow = false;
//        PopupManager.addView()
    }

}
