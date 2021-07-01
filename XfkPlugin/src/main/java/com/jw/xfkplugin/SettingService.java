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

public class SettingService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showBeyondView();
        VarManger.settingServiceShow = true;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(PopupManager.getInstance().bottomPopupView);
        VarManger.settingServiceShow = false;
    }

    private WindowManager windowManager;

    private void showBeyondView() {
        PopupManager.getInstance().initBottomSetting(this);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            lp.type = WindowManager.LayoutParams.TYPE_PHONE;
        }

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int) (displayMetrics.heightPixels/2-VarManger.dip2px(this,10f));
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.x = 0;
        lp.y = 0;
        lp.format = PixelFormat.RGBA_8888;
        lp.gravity = Gravity.BOTTOM;
        windowManager.addView(PopupManager.getInstance().bottomPopupView, lp);
    }


}