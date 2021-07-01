package com.jw.tcdialogplugin.uni;//package com.jw.xfkplugin.uni;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.provider.Settings;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.example.athree_MeisheSdk.utils.PathUtils;
//import com.jw.xfkplugin.BeyondService_2;
//import com.jw.xfkplugin.LogoService;
//import com.jw.xfkplugin.TcBean;
//import com.jw.xfkplugin.VarManger;
//import com.meicam.sdk.NvsAVFileInfo;
//import com.meicam.sdk.NvsMediaFileConvertor;
//import com.meicam.sdk.NvsStreamingContext;
//import com.taobao.weex.WXSDKEngine;
//import com.taobao.weex.adapter.URIAdapter;
//import com.taobao.weex.annotation.JSMethod;
//import com.taobao.weex.bridge.JSCallback;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Hashtable;
//import java.util.List;
//
//import static com.example.athree_MeisheSdk.utils.PathUtils.getCharacterAndNumber;
//
//public class MeisheSdkXFWXModule extends WXSDKEngine.DestroyableModule   {
////    public  static Activity Mainactivity;
////
////    private static JSCallback static_CB;
////    private static final String TAG = "####mirror";
////
////    public static Context mContext;
////
////    private NvsStreamingContext mStreamingContext;
////
////    public double temp = 0;
////
////    public JSONArray array;
////
////
////    @JSMethod(uiThread = true)
////    public void openXF(JSONArray options, JSCallback  callback) {
//////        TcBean  sss =    new TcBean("台本1","sdsddd",false);
////        array = options;
//////        path = "/sdcard/1604885387375548.mp4";
////
////        mContext = mWXSDKInstance.getContext();
////        Mainactivity = (Activity)mWXSDKInstance.getContext();
////
////
////        if (Build.VERSION.SDK_INT >= 23) {
////            if (!Settings.canDrawOverlays(Mainactivity)) {
////                Mainactivity.startActivityForResult(new Intent(
////                                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
////                                Uri.parse("package:" + Mainactivity.getPackageName())
////                        ), 102
////                );
////            } else {
////                showFloatWindow();
////            }
////        } else {
////            showFloatWindow();
////        }
////    }
////
////    private void showFloatWindow() {
////        if(VarManger.isXfkShow)
////        {
////            Toast.makeText(Mainactivity,"悬浮窗已显示",Toast.LENGTH_LONG).show();
////            return
////                    ;
////        }
////        try {
////            List<TcBean> list = new ArrayList();
////            for(int i= 0;i<array.size();i++)
////            {
////                JSONObject obj =  array.getJSONObject(i);
////                TcBean bean = new TcBean(obj.getString("title"),obj.getString("content"),obj.getBoolean("isUseing"));
////                list.add(bean);
////            }
////            VarManger.tcList = list;
////            if (VarManger.floatLogoViewShow) {
////                Mainactivity.stopService(new Intent(Mainactivity, LogoService.class));
////            }
////            Mainactivity.startService(new Intent(Mainactivity, BeyondService_2.class));
////        }
////        catch ( Exception e)
////        {
////            Log.e("","");Toast.makeText(Mainactivity,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
////        }
////    }
////
////
////    @Override
////    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
////        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
////        switch (requestCode) {
////            case 102:
////                if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//////                    Toast.makeText(Mainactivity, "权限已给！", Toast.LENGTH_LONG).show();
////                    showFloatWindow();
////                } else {
////                    Toast.makeText(Mainactivity, "请先授予权限！", Toast.LENGTH_LONG).show();
////                }
////                break;
////        }
////
////    }
////    @Override
////    public void onActivityResume() {
////        super.onActivityResume();
////
////    }
////
////    @Override
////    public void onActivityDestroy()
////    {
////        super.onActivityDestroy();
////
////    }
////
////    @Override
////    public void onActivityPause()
////    {
////        super.onActivityPause();
////
////    }
////
////    @Override
////    public void destroy() {
////
////    }
////
////    public static void detailData(JSCallback  CB,   boolean keep,   JSONObject result)
////    {
////        Log.e("####",result.toJSONString());
////        if(keep)
////            CB.invokeAndKeepAlive(result);
////        else
////            CB.invoke(result);
////
////
////    }
//
//}
