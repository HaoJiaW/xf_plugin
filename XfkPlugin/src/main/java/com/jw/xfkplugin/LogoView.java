package com.jw.xfkplugin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

class LogoView extends LinearLayout {
    private View mView;
    private ImageView logoImageView;
    private WindowManager.LayoutParams lpW;
    private DisplayMetrics displayMetrice;

    private int lastX = 0;
    public int lastY = 0;
    public int dx = 0;
    public int dy = 0;
    public int oriWidth = 0;
    public int oriHeight = 0;

    public WindowManager wm;
    boolean clickEvent = false;

    public LogoView(Context context) {
        this(context, null);
    }

    public LogoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private Context context;

    @Override
    public boolean onTouchEvent(MotionEvent it) {
        switch (it.getAction()) {
            case MotionEvent.ACTION_DOWN:
                clickEvent = true;
                lastX = (int) it.getRawX();
                lastY = (int) it.getRawY();
                oriHeight = getBottom() - getTop();
                oriWidth = getRight() - getLeft();
                lpW = (WindowManager.LayoutParams) getLayoutParams();
                System.out.println("MotionEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MotionEvent.ACTION_MOVE");
                if (lpW != null) {
                    dx = (int) it.getRawX() - lastX;
                    dy = (int) it.getRawY() - lastY;
                    lpW.x += dx;
                    if (lpW.x + lpW.width > displayMetrice.widthPixels) {
                        lpW.x = displayMetrice.widthPixels - lpW.width;
                    }
                    lpW.y += dy;
                    wm.updateViewLayout(this, lpW);
                    lastX = (int) it.getRawX();
                    lastY = (int) it.getRawY();
                    if (Math.abs(dx) > 1 && Math.abs(dy) > 1) {
                        clickEvent = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("MotionEvent.ACTION_UP");
                if (clickEvent) {
                    System.out.println("MotionEvent.ACTION_UP->" + clickEvent);
                    context.stopService(new Intent(context, LogoService.class));
                    if (VarManger.showLandSpaceView){
                        context.startService(new Intent(context, BeyondServiceLandSpace.class));
                    }else {
                        context.startService(new Intent(context, BeyondService_2.class));
                    }
                }
                if (lpW != null) {
                    if (lpW.x >= ((displayMetrice.widthPixels - lpW.width) / 2)) {
                        lpW.x = displayMetrice.widthPixels - lpW.width;
                    } else {
                        lpW.x = 0;
                    }
                    wm.updateViewLayout(this, lpW);
                    VarManger.logoViewLpw = lpW;
                    PreferencesUtils.setInt("logoViewX",lpW.x);
                    PreferencesUtils.setInt("logoViewY",lpW.y);
                }
        }
        return true;
    }


    private void initView() {
        mView = LayoutInflater.from(context).inflate(R.layout.view_logo, this);
        logoImageView = mView.findViewById(R.id.logoImageView);
        displayMetrice = context.getResources().getDisplayMetrics();
    }

}
