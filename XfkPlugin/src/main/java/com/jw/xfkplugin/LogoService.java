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

public class LogoService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showView();
        return super.onStartCommand(intent, flags, startId);
    }

    public WindowManager windowManager;

    private LogoView logoView;

    private void showView() {
        if (logoView == null) {
            logoView = new LogoView(this);
        }
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            lp.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        lp.width = VarManger.dip2px(this, 40f);
        lp.height = VarManger.dip2px(this, 40f);
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        PreferencesUtils.getInt("logoViewX",displayMetrics.widthPixels - lp.width );
//        PreferencesUtils.getInt("logoViewY",(int)( displayMetrics.heightPixels*0.5f));
        lp.x =PreferencesUtils.getInt("logoViewX",displayMetrics.widthPixels - lp.width );
        lp.y =   PreferencesUtils.getInt("logoViewY",(int)( displayMetrics.heightPixels*0.5f));
//        lp.x = VarManger.logoViewLpw == null ? displayMetrics.widthPixels - lp.width : VarManger.logoViewLpw.x;
//        lp.y = VarManger.logoViewLpw == null ? (int)( displayMetrics.heightPixels*0.5f) : VarManger.logoViewLpw.y;
        lp.format = PixelFormat.RGBA_8888;
        lp.gravity = Gravity.TOP | Gravity.START;
//        logoView?.isWindowMangerFlag = true
        logoView.wm = windowManager;
        windowManager.addView(logoView, lp);
        VarManger.floatLogoViewShow = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(logoView);
        VarManger.floatLogoViewShow = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
