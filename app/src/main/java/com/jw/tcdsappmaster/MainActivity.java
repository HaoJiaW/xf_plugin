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


import com.jw.xfkplugin.BeyondService_2;
import com.jw.xfkplugin.LogoService;
import com.jw.xfkplugin.TcBean;
import com.jw.xfkplugin.VarManger;
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

//        Button dialogBtn = findViewById(R.id.dialogBtn);
//        dialogBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                VarManger.tcList = Arrays.asList(new TcBean("??????1",getResources().getString(R.string.top_content_long),false),
////                        new TcBean("??????2",getResources().getString(R.string.top_content_long2),true));
////
////                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
////                PopupWindow pw = new PopupWindow(new TcView(MainActivity.this),(int) (displayMetrics.widthPixels * 0.8),(int) (displayMetrics.heightPixels * 0.4));
////                pw.setFocusable(true);
////                pw.setOutsideTouchable(true);
////                pw.showAsDropDown(dialogBtn);
//                DialogManager.getInstance().showPopupWindow(MainActivity.this,dialogType);
//            }
//        });
//
//
//        Button close = findViewById(R.id.close);
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                VarManger.tcList = Arrays.asList(new TcBean("??????1",getResources().getString(R.string.top_content_long),false),
////                        new TcBean("??????2",getResources().getString(R.string.top_content_long2),true));
////
////                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
////                PopupWindow pw = new PopupWindow(new TcView(MainActivity.this),(int) (displayMetrics.widthPixels * 0.8),(int) (displayMetrics.heightPixels * 0.4));
////                pw.setFocusable(true);
////                pw.setOutsideTouchable(true);
////                pw.showAsDropDown(dialogBtn);
//                DialogManager.getInstance().closeTcDialog();
//                dialogType= dialogType==0?1:0;
//            }
//        });
//
//
//
//        Button startScroll = findViewById(R.id.startScroll);
//        startScroll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                startScroll();
////                DialogManager.getInstance().startScroll(MainActivity.this);
////            DialogManager.getInstance().setLandSpace(true);
//                DialogManager.getInstance().setTvContent(getResources().getString(R.string.top_content_long));
//            }
//        });
//        Button stopScroll = findViewById(R.id.stopScroll);
//        stopScroll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                stopScroll();
////                DialogManager.getInstance().stopScroll(MainActivity.this);
//                DialogManager.getInstance().setLandSpace(true,true);
//            }
//        });
//
//        Button preTc = findViewById(R.id.preTc);
//        preTc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                setPreTc();
////            DialogManager.getInstance().preScreen(MainActivity.this);
//                DialogManager.getInstance().setBaselineColor("#fff090");
//            }
//        });
//        Button nextTc = findViewById(R.id.nextTc);
//        nextTc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                setNextTc();
////                DialogManager.getInstance().nextScreen(MainActivity.this);
//                DialogManager.getInstance().setBaselineVisible(false);
//            }
//        });
//
//        Button preScreenTc = findViewById(R.id.preScreenTc);
//        preScreenTc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                preScreen();
//                DialogManager.getInstance().preScreen(MainActivity.this);
//            }
//        });
//        Button nextScreenTc = findViewById(R.id.nextScreenTc);
//        nextScreenTc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                nextScreen();
//                DialogManager.getInstance().nextScreen(MainActivity.this);
//            }
//        });
//        Button changeTvContent = findViewById(R.id.change_content);
//        changeTvContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                nextScreen();
//                DialogManager.getInstance().setTvContent(
//                        "?????????popupWindow???setFocusable( false )???????????????setOutsideTouchable ( true ) ????????????????????????dismiss???setOutsideTouchable ??????????????????????????????mOutsideTouchable?????????mFocusable???false????????????????????????\n" +
//                                "\n" +
//                                "?????????popupWindow???setFocusable( false )?????????setOutsideTouchable ( false ) ????????????????????????????????????popupWindow?????????dismiss???????????????????????????????????????????????????popupwindow?????????activity????????????activity????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????popupWindow?????????activity???????????????button????????????onClickListener?????????????????????activity?????????popupWindow??????????????????????????????"
//                );
//            }
//        });
//
//        Button changeTvSize = findViewById(R.id.change_size);
//        changeTvSize.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                nextScreen();
//                DialogManager.getInstance().setTvSize(50);
//            }
//        });
//
//        Button changeTvSpeed = findViewById(R.id.change_speed);
//        changeTvSpeed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                nextScreen();
//                DialogManager.getInstance().setScrollSpeed(50);
//            }
//        });
//
//        Button changeColor = findViewById(R.id.change_color);
//        changeColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                nextScreen();
//                DialogManager.getInstance().setTvColor("#8FA9EF");
//            }
//        });
//
//        Button changeAlpha = findViewById(R.id.change_alpha);
//        changeAlpha.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                nextScreen();
//                DialogManager.getInstance().setViewAlpha(0);
//            }
//        });
//
//
//        Button changeWh = findViewById(R.id.change_wh);
//        changeWh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//                DialogManager.getInstance().setWidhtOrHeight((int) (displayMetrics.widthPixels * 0.3),(int) (displayMetrics.heightPixels * 0.3));
//            }
//        });
//
//        Button mirror = findViewById(R.id.mirror);
//        mirror.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogManager.getInstance().setTextViewMirror(-1);
//            }
//        });
//
//        Button mirror2 = findViewById(R.id.mirror2);
//        mirror2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogManager.getInstance().setTextViewMirror(1);
//            }
//        });

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
    * ???????????????
    *
    * */
    private void showFloatWindow() {
        com.jw.xfkplugin.VarManger.tcList = Arrays.asList(new com.jw.xfkplugin.TcBean("??????1",this.getResources().getString(R.string.top_content_long),false),
                new TcBean("??????2",this.getResources().getString(R.string.top_content_long2),true));
//        com.jw.xfkplugin.VarManger.appBeanList = Arrays.asList(new AppBean("com.ss.android.ugc.aweme","??????","https://android-artworks.25pp.com/fs08/2021/06/22/5/110_4f97f2f02156aea9c4765d438875ca54_con_130x130.png"),
//                new AppBean("com.smile.gifmaker","??????","https://android-artworks.25pp.com/fs08/2021/07/02/0/110_a1367bfffa940c82f5c035661c96102e_con_130x130.png"),new AppBean("com.android.camera","??????","https://android-artworks.25pp.com/fs08/2021/07/02/0/110_a1367bfffa940c82f5c035661c96102e_con_130x130.png")
//        ,new AppBean("com.gorgeous.lite","????????????","https://android-artworks.25pp.com/fs08/2021/07/02/0/110_a1367bfffa940c82f5c035661c96102e_con_130x130.png"),new AppBean("com.mt.mtxx.mtxx","????????????","https://android-artworks.25pp.com/fs08/2021/07/02/0/110_a1367bfffa940c82f5c035661c96102e_con_130x130.png"));
        //APP???????????????
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
//    * ?????????????????????????????????
//    * 0:??????????????????
//    * 1:??????????????????????????????
//    * 2:???????????????????????????
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
//    * ??????????????????
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
//               Toast.makeText(MainActivity.this,"????????????????????????",Toast.LENGTH_LONG).show();
//            }
//        }else {
//            Toast.makeText(MainActivity.this,"?????????????????????????????????",Toast.LENGTH_LONG).show();
//        }
//    }
//
//    /*
//     *
//     * ??????????????????
//     *
//     * */
//    private void stopScroll(){
//        int type = getScrollStatus();
//        if (type!=0){
//            if (type==1){
//                Toast.makeText(MainActivity.this,"????????????????????????",Toast.LENGTH_LONG).show();
//            }else {
//                if (VarManger.isXfkLandSpaceShow){
//                    PopupManager.getInstance().beyondViewLandSpace.changeTvStatus();
//                }else {
//                    PopupManager.getInstance().beyondView.changeTvStatus();
//                }
//            }
//        }else {
//            Toast.makeText(MainActivity.this,"?????????????????????????????????",Toast.LENGTH_LONG).show();
//        }
//    }
//
//   /*
//   *
//   * ???????????????????????????
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
//            Toast.makeText(MainActivity.this,"?????????????????????????????????",Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//    /*
//     *
//     * ???????????????????????????
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
//            Toast.makeText(MainActivity.this,"?????????????????????????????????",Toast.LENGTH_LONG).show();
//        }
//    }
//
//    /*
//    *
//    *
//    * ??????????????????????????????
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
//            Toast.makeText(MainActivity.this,"?????????????????????????????????",Toast.LENGTH_LONG).show();
//        }
//    }
//
//
//    /*
//     *
//     *
//     * ??????????????????????????????
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
//            Toast.makeText(MainActivity.this,"?????????????????????????????????",Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 102:
                if (grantResults!=null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "???????????????", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "?????????????????????", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


}