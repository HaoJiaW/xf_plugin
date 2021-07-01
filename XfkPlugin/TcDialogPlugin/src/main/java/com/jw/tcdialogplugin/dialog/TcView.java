package com.jw.tcdialogplugin.dialog;

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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jw.tcdialogplugin.AutoScrollTextView;
import com.jw.tcdialogplugin.PreferencesUtils;
import com.jw.tcdialogplugin.R;
import com.jw.tcdialogplugin.VarManger;


public class TcView extends LinearLayout {

    public TcView(Context context) {
        this(context, null);
    }

    public TcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public AutoScrollTextView tvContent;
    private Context context;
    public ImageView bottomSetImageView;
    public OnBottomImageViewClick onBottomImageViewClick;
    public LinearLayout topDecorView;
    public LinearLayout iconLayout;
    public GradientDrawable topDecorShapeDrawable;
    public View mView;
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
    private TextView countTimeView;
    public DisplayMetrics displayMetrice;
    public RelativeLayout.LayoutParams lp;
    public WindowManager.LayoutParams lpW;
    public boolean startScroll = false;
    public boolean iconShow = true;
    public boolean horizontalScreen = false;
    public boolean rotateEnable = false;
    public int tempWidth = 0;
    public int tempX = 0;
    private PopupWindow popupWindow;

    public int lastX = 0;
    public int lastY = 0;
    public int lastRawX = 0;
    public int lastRawY = 0;
    public int dx = 0;
    public int dy = 0;
    public int oriWidth = 0;
    public int oriHeight = 0;

    private int beginWidth=0;
    private int beginHeight=0;
    private int beginX=0;
    private int beginY=0;


    public boolean isWindowMangerFlag = false;
    public WindowManager wm;
    public boolean isRightBottom = false;

    public void initView() {
        displayMetrice = context.getResources().getDisplayMetrics();

        mView = LayoutInflater.from(context).inflate(R.layout.dialog_app_text, this);

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
        bottomSetImageView = mView.findViewById(R.id.img_2);
        closeIcon = mView.findViewById(R.id.close_icon);
        oriChange = mView.findViewById(R.id.oriChange);
        moveView = mView.findViewById(R.id.moveView);
        countTimeView = mView.findViewById(R.id.countTimeView);
        tvContent = mView.findViewById(R.id.tcContentText);
        tvContent.setMovementMethod(new ScrollingMovementMethod());

//        tvContent.setTextSize(PreferencesUtils.getFloat("tcContentSize", 25f));
//        tvContent.setTextColor(context.getResources().getColorStateList(PreferencesUtils.getInt("textColor", R.color.white)));
//        setScrollSpeed(PreferencesUtils.getInt("speed", 30));

//        for (int i = 0; i < VarManger.tcList.size(); i++) {
//            TcBean tcBean = VarManger.tcList.get(i);
//            if (tcBean.getUseing()) {
//                tvContent.setText("\n\n" + tcBean.getContent()+"(完)");
//            }
//        }
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
                        lastRawX = (int) it.getRawX();
                        lastRawY = (int) it.getRawY();
                        beginWidth = VarManger.getDefWidthHeight(context)[0];
                        beginHeight = VarManger.getDefWidthHeight(context)[1];

                        beginX = VarManger.getDefXY(context)[0];
                        beginY = VarManger.getDefXY(context)[1];
                        break;
                    case MotionEvent.ACTION_MOVE:
                        dx = (int) it.getRawX() - lastRawX;
                        dy = (int) it.getRawY() - lastRawY;
                        beginX += dx;
                        if (beginX + beginWidth > displayMetrice.widthPixels) {
                            beginX = displayMetrice.widthPixels - beginWidth;
                        }
                        beginY += dy;
                        if (viewEvent!=null){
                            viewEvent.changeWH(beginWidth,beginHeight,beginX,beginY);
                        }
                        lastRawX = (int) it.getRawX();
                        lastRawY = (int) it.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
//                        VarManger.beyondLandSpaceViewLpw = lpW;
                        PreferencesUtils.setInt("dialogX", beginX);
                        PreferencesUtils.setInt("dialogY", beginY);
                        PreferencesUtils.setInt("dialogW", beginWidth);
                        PreferencesUtils.setInt("dialogH",beginHeight);
                        break;
                }
                return true;
            }
        });
        closeIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewEvent.closePopupWindow();
//                VarManger.showLandSpaceView = false;
//                VarManger.getTopActivity(context);
//                context.stopService(new Intent(context, BeyondService_2.class));
//                if (VarManger.settingServiceShow) {
//                    context.stopService(new Intent(context, SettingService.class));
//                }
//                if (!VarManger.isAppResume) {
//                    context.startService(new Intent(context, LogoService.class));
//                }
            }
        });
        bottomSetImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewEvent.changeScreenOri(true);
//                if (isWindowMangerFlag) {
//                    if (iconShow) {
//                        iconLayout.setVisibility(View.GONE);
//                        if (lpW != null) {
//                            lpW.height += -VarManger.dip2px(context, 50f);
//                            wm.updateViewLayout(TcView.this, lpW);
//                        }
//                        iconShow = false;
//                    } else {
//                        iconLayout.setVisibility(View.VISIBLE);
//                        if (lpW != null) {
//                            lpW.height += VarManger.dip2px(context, 50f);
//                            wm.updateViewLayout(TcView.this, lpW);
//                        }
//                        iconShow = true;
//                    }
//                }
            }
        });

//        settingBtn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (VarManger.settingServiceShow) {
//                    return;
//                }
//                VarManger.showTcSet = false;
//                context.startService(new Intent(context, SettingService.class));
//            }
//        });


//        contentSet.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (VarManger.settingServiceShow) {
//                    return;
//                }
//                VarManger.showTcSet = true;
//                context.startService(new Intent(context, SettingService.class));
//            }
//        });

        douyinIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通过包名获取要bai跳转du的app，创建intent对象
                VarManger.redictAct(context,"com.ss.android.ugc.aweme","您的手机未安装抖音APP");

//                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.ss.android.ugc.aweme");
//                if (intent != null) {
//                    context.startActivity(intent);
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
                viewEvent.changeScreenOri(true);
//                context.stopService(new Intent(context, BeyondService_2.class));
//                context.startService(new Intent(context, BeyondServiceLandSpace.class));
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

    /**
     * 设置滚动速度
     * @param speed
     */
    public void setScrollSpeed(int speed) {
        tvContent.speed = speed;
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


    @Override
    public boolean onTouchEvent(MotionEvent it) {
        switch (it.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isRightBottom = isRightBottom(this, (int) it.getX(), (int) it.getY());
                lastRawX = (int) it.getRawX();
                lastRawY = (int) it.getRawY();
                System.out.println("MotionEvent.ACTION_DOWN,isRightBottom:" + isRightBottom);
                //初始化数据
                beginWidth = VarManger.getDefWidthHeight(context)[0];
                beginHeight = VarManger.getDefWidthHeight(context)[1];
                beginX = VarManger.getDefXY(context)[0];
                beginY = VarManger.getDefXY(context)[1];
                break;
            case MotionEvent.ACTION_MOVE:
                dx = (int) it.getRawX() - lastRawX;
                dy = (int) it.getRawY() - lastRawY;
                if (isRightBottom) {
                    beginWidth += dx;
                    beginHeight += dy;
                    if (beginWidth < VarManger.dip2px(context, 220f)) {
                        beginWidth = VarManger.dip2px(context, 220f);
                    }
                    if (beginWidth > displayMetrice.widthPixels) {
                        beginWidth = displayMetrice.widthPixels;
                    }
                    if (beginHeight < VarManger.dip2px(context, 220f)) {
                        beginHeight = VarManger.dip2px(context, 220f);
                    }
                    if (beginHeight > (int) (displayMetrice.heightPixels * 0.75)) {
                        beginHeight = (int) (displayMetrice.heightPixels * 0.75);
                    }
                } else {
                    beginX += dx;
                    if (beginX + beginWidth > displayMetrice.widthPixels) {
                        beginX = displayMetrice.widthPixels - beginWidth;
                    }
                    beginY += dy;
                }
                if (viewEvent!=null){
                    viewEvent.changeWH(beginWidth,beginHeight,beginX,beginY);
                }
                lastRawX = (int) it.getRawX();
                lastRawY = (int) it.getRawY();
                System.out.println("MotionEvent.ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                PreferencesUtils.setInt("dialogX", beginX);
                PreferencesUtils.setInt("dialogY", beginY);
                PreferencesUtils.setInt("dialogW", beginWidth);
                PreferencesUtils.setInt("dialogH",beginHeight);
                System.out.println("MotionEvent.ACTION_UP");
                break;
        }
        return true;
    }

    public void initTcList(){
        for (int i = 0; i < VarManger.tcList.size(); i++) {
            TcBean tcBean = VarManger.tcList.get(i);
            if (tcBean.getUseing()) {
                tvContent.setText("\n\n" + tcBean.getContent()+"(完)");
            }
        }
    }

    private ViewEvent viewEvent;
    public void setViewEvent(ViewEvent viewEvent){
        this.viewEvent = viewEvent;
    }

    public void scrollToY(boolean nextScreen){
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int xfkHei;
        if (lpW==null){
            xfkHei = nextScreen?(int) (displayMetrics.heightPixels * 0.4):-(int) (displayMetrics.heightPixels * 0.4);
        }else {
            xfkHei = nextScreen?lpW.height:-lpW.height;
        }
        tvContent.scrollToY(xfkHei);
    }

    public Boolean isRightBottom(View v, int x, int y) {
        if (v.getRight() - v.getLeft() - x < VarManger.dip2px(context, 35f) && v.getBottom() - v.getTop() - y < VarManger.dip2px(context, 35f)) {
            return true;
        }
        return false;
    }

    public void setY(int y){
        tvContent.y=y;
        tvContent.postInvalidate();
    }

}
