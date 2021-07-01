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

public class BeyondService_2 extends Service {

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
        if (PopupManager.getInstance().beyondView == null) {
            PopupManager.getInstance().beyondView = new BeyondView(this);
            int bgColor = PreferencesUtils.getInt("bgColor", R.color.black);
            PopupManager.getInstance().beyondView.setBgColor(this.getResources().getColor(bgColor));
            int alpahVal = PreferencesUtils.getInt("alpahVal", 70);
            PopupManager.getInstance().beyondView.setBgAlpha((int) ((alpahVal / 100f) * 255));
        } else {
            PopupManager.getInstance().beyondView.initTcList();
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

//        lp.width = VarManger.beyonViewLpw != null ? VarManger.beyonViewLpw.width : displayMetrics.widthPixels;
//        lp.height = VarManger.beyonViewLpw != null ? VarManger.beyonViewLpw.height : (int) (displayMetrics.heightPixels * 0.4);
//        lp.x = VarManger.beyonViewLpw != null ? VarManger.beyonViewLpw.x : 0;
//        lp.y = VarManger.beyonViewLpw != null ? VarManger.beyonViewLpw.y : VarManger.getSystemBarHeight(this);
        lp.width = PreferencesUtils.getInt("beyondViewW", (int) (displayMetrics.widthPixels * 0.8));
        lp.height = PreferencesUtils.getInt("beyondViewH", (int) (displayMetrics.heightPixels * 0.4));
        lp.x = PreferencesUtils.getInt("beyondViewX", 0);
        lp.y = PreferencesUtils.getInt("beyondViewY", VarManger.getSystemBarHeight(this));
        lp.format = PixelFormat.RGBA_8888;
        lp.gravity = Gravity.TOP;
//        lp.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        PopupManager.getInstance().beyondView.isWindowMangerFlag = true;
        PopupManager.getInstance().beyondView.wm = windowManager;
        windowManager.addView(PopupManager.getInstance().beyondView, lp);
        VarManger.isXfkShow = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(PopupManager.getInstance().beyondView);
        VarManger.isXfkShow = false;
//        PopupManager.addView()
    }

}
