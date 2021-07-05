package com.jw.tcdsappmaster;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.jw.tcdialogplugin.VarManger;
import com.jw.tcdialogplugin.dialog.DialogManager;
import com.jw.xfkplugin.BeyondService_2;
import com.jw.xfkplugin.LogoService;
import com.jw.xfkplugin.TcBean;
import com.jw.xfkplugin.mode.AppBean;
//import com.jw.xfkplugin.BeyondService_2;
//import com.jw.xfkplugin.LogoService;
//import com.jw.xfkplugin.PopupManager;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private int dialogType=0;

    private ImageView ivTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivTest = findViewById(R.id.iv_test);
//        Glide.with(this).load("https://android-artworks.25pp.com/fs08/2021/07/02/0/110_a1367bfffa940c82f5c035661c96102e_con_130x130.png").into(ivTest);

        Button floatWindowBtn = findViewById(R.id.floatWindowBtn);
        floatWindowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (!Settings.canDrawOverlays(MainActivity.this)) {
                        startActivityForResult(new Intent(
                                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                        Uri.parse("package:" + getPackageName())
                                ), 102
                        );
                    } else {
                        showFloatWindow();
                    }
                } else {
                    showFloatWindow();
                }
            }
        });

        Button dialogBtn = findViewById(R.id.dialogBtn);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                VarManger.tcList = Arrays.asList(new TcBean("台本1",getResources().getString(R.string.top_content_long),false),
//                        new TcBean("台本2",getResources().getString(R.string.top_content_long2),true));
//
//                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//                PopupWindow pw = new PopupWindow(new TcView(MainActivity.this),(int) (displayMetrics.widthPixels * 0.8),(int) (displayMetrics.heightPixels * 0.4));
//                pw.setFocusable(true);
//                pw.setOutsideTouchable(true);
//                pw.showAsDropDown(dialogBtn);
                DialogManager.getInstance().showPopupWindow(MainActivity.this,dialogType);
            }
        });


        Button close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                VarManger.tcList = Arrays.asList(new TcBean("台本1",getResources().getString(R.string.top_content_long),false),
//                        new TcBean("台本2",getResources().getString(R.string.top_content_long2),true));
//
//                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//                PopupWindow pw = new PopupWindow(new TcView(MainActivity.this),(int) (displayMetrics.widthPixels * 0.8),(int) (displayMetrics.heightPixels * 0.4));
//                pw.setFocusable(true);
//                pw.setOutsideTouchable(true);
//                pw.showAsDropDown(dialogBtn);
                DialogManager.getInstance().closeTcDialog();
                dialogType= dialogType==0?1:0;
            }
        });



        Button startScroll = findViewById(R.id.startScroll);
        startScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startScroll();
//                DialogManager.getInstance().startScroll(MainActivity.this);
//            DialogManager.getInstance().setLandSpace(true);
                DialogManager.getInstance().setTvContent(getResources().getString(R.string.top_content_long));
            }
        });
        Button stopScroll = findViewById(R.id.stopScroll);
        stopScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                stopScroll();
//                DialogManager.getInstance().stopScroll(MainActivity.this);
                DialogManager.getInstance().setLandSpace(true,true);
            }
        });

        Button preTc = findViewById(R.id.preTc);
        preTc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setPreTc();
//            DialogManager.getInstance().preScreen(MainActivity.this);
                DialogManager.getInstance().setBaselineColor("#fff090");
            }
        });
        Button nextTc = findViewById(R.id.nextTc);
        nextTc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setNextTc();
//                DialogManager.getInstance().nextScreen(MainActivity.this);
                DialogManager.getInstance().setBaselineVisible(false);
            }
        });

        Button preScreenTc = findViewById(R.id.preScreenTc);
        preScreenTc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                preScreen();
                DialogManager.getInstance().preScreen(MainActivity.this);
            }
        });
        Button nextScreenTc = findViewById(R.id.nextScreenTc);
        nextScreenTc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                nextScreen();
                DialogManager.getInstance().nextScreen(MainActivity.this);
            }
        });
        Button changeTvContent = findViewById(R.id.change_content);
        changeTvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                nextScreen();
                DialogManager.getInstance().setTvContent(
                        "当设置popupWindow的setFocusable( false )，那么设置setOutsideTouchable ( true ) 时，点击区域外会dismiss，setOutsideTouchable 终于起作用了。也就是mOutsideTouchable只有在mFocusable为false的时候才有作用。\n" +
                                "\n" +
                                "当设置popupWindow的setFocusable( false )，设置setOutsideTouchable ( false ) 时会有什么效果呢？答案是popupWindow是不会dismiss的，但是区域外的点击事件已经传递到popupwindow下面的activity了，点击activity下面的控件会有作用的。这就坑爹了，点击区域外不消失的效果是做到了，可是点击区域外，也就是popupWindow下面的activity的控件例如button时会响应onClickListener的。那该怎么使activity不分发popupWindow区域外的点击事件呢？"
                );
            }
        });

        Button changeTvSize = findViewById(R.id.change_size);
        changeTvSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                nextScreen();
                DialogManager.getInstance().setTvSize(50);
            }
        });

        Button changeTvSpeed = findViewById(R.id.change_speed);
        changeTvSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                nextScreen();
                DialogManager.getInstance().setScrollSpeed(50);
            }
        });

        Button changeColor = findViewById(R.id.change_color);
        changeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                nextScreen();
                DialogManager.getInstance().setTvColor("#8FA9EF");
            }
        });

        Button changeAlpha = findViewById(R.id.change_alpha);
        changeAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                nextScreen();
                DialogManager.getInstance().setViewAlpha(0);
            }
        });


        Button changeWh = findViewById(R.id.change_wh);
        changeWh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                DialogManager.getInstance().setWidhtOrHeight((int) (displayMetrics.widthPixels * 0.3),(int) (displayMetrics.heightPixels * 0.3));
            }
        });

        Button mirror = findViewById(R.id.mirror);
        mirror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().setTextViewMirror(-1);
            }
        });

        Button mirror2 = findViewById(R.id.mirror2);
        mirror2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.getInstance().setTextViewMirror(1);
            }
        });

//        Button change = findViewById(R.id.change_speed);
//        changeTvSpeed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                nextScreen();
//                DialogManager.getInstance().setScrollSpeed(50);
//            }
//        });
    }

    /*
    *
    * 打开悬浮框
    *
    * */
    private void showFloatWindow() {
        com.jw.xfkplugin.VarManger.tcList = Arrays.asList(new com.jw.xfkplugin.TcBean("台本1",this.getResources().getString(R.string.top_content_long),false),
                new TcBean("台本2",this.getResources().getString(R.string.top_content_long2),true));
        com.jw.xfkplugin.VarManger.appBeanList = Arrays.asList(new AppBean("com.ss.android.ugc.aweme","抖音","https://android-artworks.25pp.com/fs08/2021/06/22/5/110_4f97f2f02156aea9c4765d438875ca54_con_130x130.png"),
                new AppBean("com.smile.gifmaker","快手","https://android-artworks.25pp.com/fs08/2021/07/02/0/110_a1367bfffa940c82f5c035661c96102e_con_130x130.png"),new AppBean("com.android.camera","相机","https://android-artworks.25pp.com/fs08/2021/07/02/0/110_a1367bfffa940c82f5c035661c96102e_con_130x130.png")
        ,new AppBean("com.gorgeous.lite","轻颜相机","https://android-artworks.25pp.com/fs08/2021/07/02/0/110_a1367bfffa940c82f5c035661c96102e_con_130x130.png"),new AppBean("com.mt.mtxx.mtxx","美图秀秀","https://android-artworks.25pp.com/fs08/2021/07/02/0/110_a1367bfffa940c82f5c035661c96102e_con_130x130.png"));
        //APP是否在前台
        VarManger.isAppResume = false;
        if (VarManger.floatLogoViewShow) {
            stopService(new Intent(this, LogoService.class));
        }
        startService(new Intent(this, BeyondService_2.class));
    }
//
//
//    /*
//    *
//    * 获取当前是否滚动的状态
//    * 0:悬浮框未打开
//    * 1:悬浮框打开，没有滚动
//    * 2:悬浮框打开，滚动中
//    * */
//    private int getScrollStatus(){
//        if (VarManger.isXfkShow){
//            if (VarManger.isXfkLandSpaceShow){
//                return PopupManager.getInstance().beyondViewLandSpace.startScroll?2:1;
//            }else {
//                return PopupManager.getInstance().beyondView.startScroll?2:1;
//            }
//        }else {
//            return 0;
//        }
//    }
//
//    /*
//    *
//    * 启动文字滚动
//    *
//    * */
//    private void startScroll(){
//        int type = getScrollStatus();
//        if (type!=0){
//            if (type==1){
//                if (VarManger.isXfkLandSpaceShow){
//                    PopupManager.getInstance().beyondViewLandSpace.changeTvStatus();
//                }else {
//                    PopupManager.getInstance().beyondView.changeTvStatus();
//                }
//            }else {
//               Toast.makeText(MainActivity.this,"当前处于启动状态",Toast.LENGTH_LONG).show();
//            }
//        }else {
//            Toast.makeText(MainActivity.this,"悬浮框未打开，请先打开",Toast.LENGTH_LONG).show();
//        }
//    }
//
//    /*
//     *
//     * 暂停文字滚动
//     *
//     * */
//    private void stopScroll(){
//        int type = getScrollStatus();
//        if (type!=0){
//            if (type==1){
//                Toast.makeText(MainActivity.this,"当前处于停止状态",Toast.LENGTH_LONG).show();
//            }else {
//                if (VarManger.isXfkLandSpaceShow){
//                    PopupManager.getInstance().beyondViewLandSpace.changeTvStatus();
//                }else {
//                    PopupManager.getInstance().beyondView.changeTvStatus();
//                }
//            }
//        }else {
//            Toast.makeText(MainActivity.this,"悬浮框未打开，请先打开",Toast.LENGTH_LONG).show();
//        }
//    }
//
//   /*
//   *
//   * 设置显示上一个台本
//   *
//   * */
//    private void setPreTc(){
//        if (VarManger.isXfkShow){
//            int position = 0;
//            boolean isIn = false;
//            for (int i=0;i<VarManger.tcList.size();i++){
//                if (VarManger.tcList.get(i).getUseing()){
//                    position = i;
//                    isIn= true;
//                    VarManger.tcList.get(i).setUseing(false);
//                    break;
//                }
//            }
//            if (isIn){
//                int newPos = 0;
//                if (position==0){
//                    newPos = VarManger.tcList.size()-1;
//                }else {
//                    newPos = position-1;
//                }
//                VarManger.tcList.get(newPos).setUseing(true);
//            }
//            if (VarManger.isXfkLandSpaceShow){
//                PopupManager.getInstance().beyondViewLandSpace.initTcList();
//            }else {
//                PopupManager.getInstance().beyondView.initTcList();
//            }
//        }else {
//            Toast.makeText(MainActivity.this,"悬浮框未打开，请先打开",Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//    /*
//     *
//     * 设置显示下一个台本
//     *
//     * */
//    private void setNextTc(){
//        if (VarManger.isXfkShow){
//            int position = 0;
//            boolean isIn = false;
//            for (int i=0;i<VarManger.tcList.size();i++){
//                if (VarManger.tcList.get(i).getUseing()){
//                    position = i;
//                    isIn= true;
//                    VarManger.tcList.get(i).setUseing(false);
//                    break;
//                }
//            }
//            if (isIn){
//                int newPos = 0;
//                if (position==VarManger.tcList.size()-1){
//                    newPos = 0;
//                }else {
//                    newPos = position+1;
//                }
//                VarManger.tcList.get(newPos).setUseing(true);
//            }
//            if (VarManger.isXfkLandSpaceShow){
//                PopupManager.getInstance().beyondViewLandSpace.initTcList();
//            }else {
//                PopupManager.getInstance().beyondView.initTcList();
//            }
//        }else {
//            Toast.makeText(MainActivity.this,"悬浮框未打开，请先打开",Toast.LENGTH_LONG).show();
//        }
//    }
//
//    /*
//    *
//    *
//    * 设置显示上一屏幕台词
//    *
//    * */
//    private void preScreen(){
//        if (VarManger.isXfkShow){
//            if (VarManger.isXfkLandSpaceShow){
//                PopupManager.getInstance().beyondViewLandSpace.scrollToY(false);
//            }else {
//                PopupManager.getInstance().beyondView.scrollToY(false);
//            }
//        }else {
//            Toast.makeText(MainActivity.this,"悬浮框未打开，请先打开",Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//    /*
//     *
//     *
//     * 设置显示下一屏幕台词
//     *
//     * */
//    private void nextScreen(){
//        if (VarManger.isXfkShow){
//            if (VarManger.isXfkLandSpaceShow){
//                PopupManager.getInstance().beyondViewLandSpace.scrollToY(true);
//            }else {
//                PopupManager.getInstance().beyondView.scrollToY(true);
//            }
//        }else {
//            Toast.makeText(MainActivity.this,"悬浮框未打开，请先打开",Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 102:
                if (grantResults!=null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "权限已给！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "请先授予权限！", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


}