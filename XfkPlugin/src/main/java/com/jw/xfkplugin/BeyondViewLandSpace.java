package com.jw.xfkplugin;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jw.xfkplugin.widget.BaseView;

public class BeyondViewLandSpace extends BaseView {

    public BeyondViewLandSpace(Context context) {
        this(context, null);
    }

    public BeyondViewLandSpace(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BeyondViewLandSpace(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.pop_app_text_landspace2;
    }

    public AutoScrollTextView tvContent;
    public OnBottomImageViewClick onBottomImageViewClick;
    public LinearLayout topDecorView;
    public LinearLayout iconLayout;
    public GradientDrawable topDecorShapeDrawable;
    //    var leftTranslation: ImageView
    public ImageView settingBtn;
    public ImageView contentSet;
    public ImageView rightScale;
    public ImageView scrollControl;
    public ImageView cameraIcon;
    public ImageView homeIcon;
    public ImageView mtxxIcon;
    public ImageView qyxjIcon;
    public ImageView wutaIcon;
    public ImageView douyinIcon;
    public ImageView closeIcon;
    public ImageView oriChange;
    public View moveView;
    public TextView countTimeView;
    public DisplayMetrics displayMetrice;
    public RelativeLayout.LayoutParams lp;
    public boolean startScroll = false;
    public boolean horizontalScreen = false;
    public boolean rotateEnable = false;
    public int tempWidth = 0;
    public int tempX = 0;

    public int lastX = 0;
    public int lastY = 0;
    public int lastRawX = 0;
    public int lastRawY = 0;
    public int dx = 0;
    public int dy = 0;
    public int oriWidth = 0;
    public int oriHeight = 0;
    public boolean isLeftBottom = false;

    public void initView() {
        displayMetrice = context.getResources().getDisplayMetrics();
        topDecorView = mView.findViewById(R.id.topDecorView);
        iconLayout = mView.findViewById(R.id.iconLayout);
        cameraIcon = mView.findViewById(R.id.cameraIcon);
        homeIcon = mView.findViewById(R.id.homeIcon);
        mtxxIcon = mView.findViewById(R.id.mtxxIcon);
        qyxjIcon = mView.findViewById(R.id.qyxjIcon);
        wutaIcon = mView.findViewById(R.id.wutaIcon);
        douyinIcon = mView.findViewById(R.id.douyinIcon);
        settingBtn = mView.findViewById(R.id.settingBtn);
        contentSet = mView.findViewById(R.id.contentSet);
        closeIcon = mView.findViewById(R.id.close_icon);
        oriChange = mView.findViewById(R.id.oriChange);
        moveView = mView.findViewById(R.id.moveView);
        tvContent = mView.findViewById(R.id.tcContentText);
        countTimeView = mView.findViewById(R.id.countTimeView);
        tvContent.setTextSize(PreferencesUtils.getFloat("tcContentSize", 25f));
        tvContent.setTextColor(context.getResources().getColorStateList(PreferencesUtils.getInt("textColor", R.color.white)));
        tvContent.setMovementMethod(new ScrollingMovementMethod());
        setScrollSpeed(PreferencesUtils.getInt("speed", 30));
        for (int i = 0; i < VarManger.tcList.size(); i++) {
            TcBean tcBean = VarManger.tcList.get(i);
            if (tcBean.getUseing()) {
                tvContent.setText("\n\n" + tcBean.getContent()+"(完)");
            }
        }
//        leftTranslation = mView.findViewById(R.id.leftTranslation);
        rightScale = mView.findViewById(R.id.rightScale);
        scrollControl = mView.findViewById(R.id.scrollControl);
        scrollControl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTvStatus();
            }
        });

        moveView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View p0, MotionEvent it) {
                switch (it.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) it.getX();
                        lastY = (int) it.getY();
                        lastRawX = (int) it.getRawX();
                        lastRawY = (int) it.getRawY();
                        oriWidth = getRight() - getLeft();
                        oriHeight = getBottom() - getTop();
                        if (!isWindowMangerFlag) {
                            lp = (RelativeLayout.LayoutParams) getLayoutParams();
                        } else {
                            lpW = (WindowManager.LayoutParams) getLayoutParams();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (isWindowMangerFlag) {
                            if (lpW != null) {
                                dx = (int) it.getRawX() - lastRawX;
                                dy = (int) it.getRawY() - lastRawY;
                                lpW.x += dx;
                                if (lpW.x + lpW.width > displayMetrice.widthPixels) {
                                    lpW.x = displayMetrice.widthPixels - lpW.width;
                                }
                                lpW.y += dy;
                                if (wm != null) {
                                    wm.updateViewLayout(getRootView(), lpW);
                                }
                                lastRawX = (int) it.getRawX();
                                lastRawY = (int) it.getRawY();
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
//                        VarManger.beyondLandSpaceViewLpw = lpW;
                        PreferencesUtils.setInt("beyondViewLandSpaceX", lpW.x);
                        PreferencesUtils.setInt("beyondViewLandSpaceY", lpW.y);
                        PreferencesUtils.setInt("beyondViewLandSpaceW", lpW.width);
                        PreferencesUtils.setInt("beyondViewLandSpaceH", lpW.height);
                        break;
                }
                return true;
            }
        });

        closeIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                VarManger.showLandSpaceView = true;
                VarManger.getTopActivity(context);
                context.stopService(new Intent(context, BeyondServiceLandSpace.class));
                if (VarManger.settingServiceShow) {
                    context.stopService(new Intent(context, SettingServiceLandSpace.class));
                }
                if (!VarManger.isAppResume) {
                    context.startService(new Intent(context, LogoService.class));
                }
            }
        });

        settingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VarManger.settingServiceShow) {
                    return;
                }
                VarManger.showTcSet = false;
                context.startService(new Intent(context, SettingServiceLandSpace.class));
            }
        });

        contentSet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VarManger.settingServiceShow) {
                    return;
                }
                VarManger.showTcSet = true;
                context.startService(new Intent(context, SettingServiceLandSpace.class));
            }
        });

        douyinIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通过包名获取要bai跳转du的app，创建intent对象
                VarManger.redictAct(context,"com.ss.android.ugc.aweme","您的手机未安装抖音APP");

//                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.ss.android.ugc.aweme");
//                if (intent != null) {
//                    context.startActivity(intent);å
//                } else {
//                    // 未安装应用
//                    Toast.makeText(context, "您的手机未安装抖音APP", Toast.LENGTH_LONG).show();
//                }
            }
        });

        cameraIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通过包名获取要bai跳转du的app，创建intent对象
                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.android.camera");
                if (intent == null) {
                    intent = context.getPackageManager().getLaunchIntentForPackage("com.huawei.camera");
                }
                if (intent != null) {
                    VarManger.isStartOtherAct = true;
                    context.startActivity(intent);
                } else {
                    // 未安装应用
                    Toast.makeText(context, "打开相机失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        mtxxIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通过包名获取要bai跳转du的app，创建intent对象
                VarManger.redictAct(context,"com.smile.gifmaker","您的手机未安装快手App");


//                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.smile.gifmaker");
//                if (intent != null) {
//                    context.startActivity(intent);
//                } else {
//                    // 未安装应用
//                    Toast.makeText(context, "您的手机未安装快手App", Toast.LENGTH_LONG).show();
//                }
            }
        });

        qyxjIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通过包名获取要bai跳转du的app，创建intent对象
                VarManger.redictAct(context,"com.gorgeous.lite","您的手机未安装轻颜相机App");


//                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.gorgeous.lite");
//                if (intent != null) {
//                    context.startActivity(intent);
//                } else {
//                    // 未安装应用
//                    Toast.makeText(context, "您的手机未安装轻颜相机App", Toast.LENGTH_LONG).show();
//                }
            }
        });

        wutaIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通过包名获取要bai跳转du的app，创建intent对象
                VarManger.redictAct(context,"com.mt.mtxx.mtxx","您的手机未安装美图秀秀App");


//                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.mt.mtxx.mtxx");
//                if (intent != null) {
//                    context.startActivity(intent);
//                } else {
//                    // 未安装应用
//                    Toast.makeText(context, "您的手机未安装美图秀秀App", Toast.LENGTH_LONG).show();
//                }
            }
        });
//        douyinIcon.setOnClickListener {
//            val url ="snssdk1128://aweme/detail/6824072228976594180"
//            if (!VarManger.startWithUrl(context,url)){
//                Toast.makeText(context,"您的手机未安装抖音",Toast.LENGTH_LONG).show()
//            }
//        }

        homeIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通过包名获取要bai跳转du的app，创建intent对象
                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.hdn.tici");
                if (intent != null) {
                    VarManger.isStartOtherAct = false;
                    context.startActivity(intent);
                } else {
                    // 未安装应用
                    Toast.makeText(context, "调用失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        oriChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                context.stopService(new Intent(context, BeyondServiceLandSpace.class));
                context.startService(new Intent(context, BeyondService_2.class));
            }
        });

        if (topDecorShapeDrawable == null) {
            topDecorShapeDrawable = (GradientDrawable) topDecorView.getBackground();
            topDecorShapeDrawable.setAlpha((int) (0.7 * 255));
        }
    }
//        private oriChangeFun(){
//        lpW?.let {
//        System.out.println("old:height:"+it.height+",width:"+it.width)
//        tempWidth = it.width
////            tempX= it.x
////            it.x = it.y
////            it.y = tempX
//        it.width = it.height
//        if (it.width>displayMetrice.widthPixels){
//        it.width = displayMetrice.widthPixels
//        }
//        it.height = tempWidth
//        if (it.height>displayMetrice.heightPixels){
//        it.height = displayMetrice.heightPixels
//        }
//        System.out.println("new:height:"+it.height+",width:"+it.width)
//        wm?.updateViewLayout(this,it)
//        }
//        }

    public void setContentTextSize(Float size) {
        tvContent.setTextSize(size);
    }

    public void setContentTextColor(ColorStateList color) {
        tvContent.setTextColor(color);
    }

    public void setBgAlpha(int alpha) {
        topDecorShapeDrawable.setAlpha(alpha);
    }

    public void setBgColor(int bgColor) {
        topDecorShapeDrawable.setColor(bgColor);
    }

    public interface OnBottomImageViewClick {
        void onClick();
    }

    public void changeTvStatus(){
        if (!startScroll) {
            if (VarManger.fristStartTc){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 4; i++) {
                            if (i == 0) {
                                countTimeView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        countTimeView.setText("3");
                                        countTimeView.setVisibility(VISIBLE);
                                    }
                                });
                            } else if (i == 1) {
                                countTimeView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        countTimeView.setText("2");
                                    }
                                });
                            } else if (i == 2) {
                                countTimeView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        countTimeView.setText("1");
                                    }
                                });
                            } else if (i == 3) {
                                countTimeView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        countTimeView.setVisibility(GONE);
                                        tvContent.startScroll = true;
                                        scrollControl.setImageDrawable(context.getResources().getDrawable(R.drawable.scroll_pause));
                                        startScroll = true;
                                        VarManger.fristStartTc = false;
                                    }
                                });
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }else {
                countTimeView.setVisibility(GONE);
                tvContent.startScroll = true;
                scrollControl.setImageDrawable(context.getResources().getDrawable(R.drawable.scroll_pause));
                startScroll = true;
            }
        } else {
            tvContent.startScroll = false;
            scrollControl.setImageDrawable(context.getResources().getDrawable(R.drawable.scroll_start));
            startScroll = false;
        }
    }


    public void setScrollSpeed(int speed) {
        tvContent.speed = speed;
    }

    @Override
    public boolean onTouchEvent(MotionEvent it) {
        switch (it.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isLeftBottom = isLeftBottom(this, (int) it.getX(), (int) it.getY());
                lastX = (int) it.getX();
                lastY = (int) it.getY();
                lastRawX = (int) it.getRawX();
                lastRawY = (int) it.getRawY();
                System.out.println("MotionEvent.ACTION_DOWN,isLeftBottom:" + isLeftBottom);
                oriWidth = getRight() - getLeft();
                oriHeight = getBottom() - getTop();
                if (!isWindowMangerFlag) {
                    lp = (RelativeLayout.LayoutParams) getLayoutParams();
                } else {
                    lpW = (WindowManager.LayoutParams) getLayoutParams();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isWindowMangerFlag) {
                    if (lpW != null) {
                        dx = (int) it.getRawX() - lastRawX;
                        dy = (int) it.getRawY() - lastRawY;
                        if (isLeftBottom) {
                            lpW.width -= dx;
                            lpW.height += dy;
                            if (lpW.width < VarManger.dip2px(context, 220f)) {
                                lpW.width = VarManger.dip2px(context, 220f);
                            }
                            if (lpW.width > displayMetrice.widthPixels) {
                                lpW.width = displayMetrice.widthPixels;
                            }
                            if (lpW.height < VarManger.dip2px(context, 220f)) {
                                lpW.height = VarManger.dip2px(context, 220f);
                            }
                            if (lpW.height > (int) (displayMetrice.heightPixels*0.75)) {
                                lpW.height = (int) (displayMetrice.heightPixels*0.75);
                            }
                            wm.updateViewLayout(this, lpW);
                        } else {
                            lpW.x += dx;
                            if (lpW.x + lpW.width > displayMetrice.widthPixels) {
                                lpW.x = displayMetrice.widthPixels - lpW.width;
                            }
                            lpW.y += dy;
                            wm.updateViewLayout(this, lpW);
                        }
                        lastRawX = (int) it.getRawX();
                        lastRawY = (int) it.getRawY();
                    }
                } else {
                    dx = (int) it.getX() - lastX;
                    dy = (int) it.getY() - lastY;
                    int l = getLeft() + dx;
                    int t = getTop() + dy;
                    int r = getRight() + dx;
                    int b = getBottom() + dy;
                    if (l < 0) {
                        l = 0;
                        r = l + oriWidth;
                    }
                    if (r > displayMetrice.widthPixels) {
                        r = displayMetrice.widthPixels;
                        l = r - oriWidth;
                    }
                    if (t < 0) {
                        t = 0;
                        b = t + oriHeight;
                    }
                    if (b > displayMetrice.heightPixels) {
                        b = displayMetrice.heightPixels;
                        t = b - oriHeight;
                    }

                    //是右下区域的话，可拉伸
                    if (isLeftBottom) {
                        lp.width = lp.width + dx;
                        lp.height = lp.height + dy;
                        if (lp.width < VarManger.dip2px(context, 220f)) {
                            lp.width = VarManger.dip2px(context, 220f);
                        }
                        if (lp.height < VarManger.dip2px(context, 220f)) {
                            lp.height = VarManger.dip2px(context, 220f);
                        }
                        lastX = (int) it.getX();
                        lastY = (int) it.getY();
                        this.setLayoutParams(lp);
                        layout(l, t, l + lp.width, t + lp.height);
                    } else {
                        layout(l, t, r, b);
                    }
                    System.out.println("MotionEvent.ACTION_MOVE");
                }
                break;
            case MotionEvent.ACTION_UP:
                lastX = (int) it.getX();
                lastY = (int) it.getY();
                //将位置固定好，不会被页面刷新复位
                if (!isWindowMangerFlag) {
                    lp.topMargin = getTop();
                    lp.leftMargin = getLeft();
                    lp.setMargins(getLeft(), getTop(), 0, 0);
                    this.setLayoutParams(lp);
                } else {
                    VarManger.beyondLandSpaceViewLpw = lpW;
                    PreferencesUtils.setInt("beyondViewLandSpaceX", lpW.x);
                    PreferencesUtils.setInt("beyondViewLandSpaceY", lpW.y);
                    PreferencesUtils.setInt("beyondViewLandSpaceW", lpW.width);
                    PreferencesUtils.setInt("beyondViewLandSpaceH", lpW.height);
//                    if (isLeftBottom){
//                        context.stopService(new Intent(context,BeyondServiceLandSpace.class));
//                        context.startService(new Intent(context,BeyondServiceLandSpace.class));
//                    }
                }
                System.out.println("MotionEvent.ACTION_UP");
                break;
        }
        return true;
    }

    public Boolean isRightBottom(View v, int x, int y) {
        if (v.getRight() - v.getLeft() - x < VarManger.dip2px(context, 35f) && v.getBottom() - v.getTop() - y < VarManger.dip2px(context, 35f)) {
            return true;
        }
        return false;
    }

    public Boolean isLeftBottom(View v, int x, int y) {
        if (x < VarManger.dip2px(context, 35f) && v.getBottom() - v.getTop() - y < VarManger.dip2px(context, 35f)) {
            return true;
        }
        return false;
    }

    public void initTcList(){
        for (int i = 0; i < VarManger.tcList.size(); i++) {
            TcBean tcBean = VarManger.tcList.get(i);
            if (tcBean.getUseing()) {
                tvContent.setText("\n\n" + tcBean.getContent()+"(完)");
            }
        }
    }

    public void scrollToY(boolean nextScreen){
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int xfkHei;
        if (lpW==null){
            xfkHei = nextScreen?(int) (displayMetrics.heightPixels * 0.4):-(int) (displayMetrics.heightPixels * 0.4);
        }else {
            xfkHei = nextScreen?lpW.width:-lpW.width;
        }
        tvContent.scrollToY(xfkHei);
    }

    public void setY(int y){
        tvContent.y=y;
        tvContent.postInvalidate();
    }

}
