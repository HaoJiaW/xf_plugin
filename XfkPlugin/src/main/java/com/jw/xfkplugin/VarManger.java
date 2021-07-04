package com.jw.xfkplugin;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.jw.xfkplugin.mode.AppBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class VarManger {

    public static int getSystemBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int dip2px(Context context, Float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //px转dp
    public int px2dip(Context context, int pxValue) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                (float) pxValue,
                context.getResources().getDisplayMetrics()
        );
    }

    public static Boolean startWithUrl(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static int getToolBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static WindowManager.LayoutParams logoViewLpw;
    public static WindowManager.LayoutParams beyonViewLpw;
    public static WindowManager.LayoutParams beyondLandSpaceViewLpw;
    public static boolean floatLogoViewShow = false;
    public static boolean showTcSet = false;
    public static boolean showLandSpaceView = false;
    public static boolean settingServiceShow = false;
    public static List<TcBean> tcList;
    public static List<AppBean> appBeanList;
    public static boolean isAppResume = false;
    public static boolean isStartOtherAct = false;
    public static boolean fristStartTc = true;
    public static boolean isXfkShow = false;
    public static boolean isXfkLandSpaceShow = false;

    //判断当前界面显示的是哪个Activity
    public static void getTopActivity(Context context){
        ActivityManager am =   (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        ComponentName cn =   am.getRunningTasks(1).get(0).topActivity;
        System.out.println("包名："+cn.getPackageName()+",包名+类名："+cn.getClassName());
        if (cn.getPackageName().contains("launcher") || isStartOtherAct){
            isAppResume = false;
        }else {
            isAppResume = true;
        }
    }

    //判断当前界面显示的是哪个Activity
    public static void removeTopActivity(Context context){
        ActivityManager am =   (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        ComponentName cn =   am.getRunningTasks(1).get(0).topActivity;
    }

    public static void redictAct(Context context,String pkgName,String warnInfo){
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkgName);
        if (intent != null) {
            isStartOtherAct = true;
            context.startActivity(intent);
        } else {
            // 未安装应用
            Toast.makeText(context, warnInfo, Toast.LENGTH_LONG).show();
        }
    }

    public static void runCommand(){
        BufferedReader reader = null;
        String content = "";
        try {
            //("ps -P|grep bg")执行失败，PC端adb shell ps -P|grep bg执行成功
            //Process process = Runtime.getRuntime().exec("ps -P|grep tv");
            //-P 显示程序调度状态，通常是bg或fg，获取失败返回un和er
            // Process process = Runtime.getRuntime().exec("ps -P");
            //打印进程信息，不过滤任何条件
            Process process = Runtime.getRuntime().exec("adb shell dumpsys window w |findstr \\/ |findstr name=");
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer output = new StringBuffer();
            int read;
            char[] buffer = new char[4096];
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            content = output.toString();
            System.out.println("command result:"+content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer[] getDefWidthHeight(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new Integer[]{PreferencesUtils.getInt("dialogW",(int) (displayMetrics.widthPixels * 0.8)),
                PreferencesUtils.getInt("dialogH",(int) (displayMetrics.heightPixels * 0.4))};
    }

    public static Integer[] getDefWidthHeightLandSpace(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return new Integer[]{PreferencesUtils.getInt("dialogLsW",(int) (displayMetrics.widthPixels * 0.8)),
                PreferencesUtils.getInt("dialogLsH",(int) (displayMetrics.heightPixels * 0.4))};
    }


    public static int getScreenWidth(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static Integer[] getDefXY(Context context){
        return new Integer[]{PreferencesUtils.getInt("dialogX",0),PreferencesUtils.getInt("dialogY",VarManger.getSystemBarHeight(context))};
    }


    public static Boolean isRightBottom(Context context,View v, int x, int y) {
        if (v.getRight() - v.getLeft() - x < VarManger.dip2px(context, 35f) && v.getBottom() - v.getTop() - y < VarManger.dip2px(context, 35f)) {
            return true;
        }
        return false;
    }
}