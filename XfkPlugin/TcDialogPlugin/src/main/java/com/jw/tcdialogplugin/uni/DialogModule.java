package com.jw.tcdialogplugin.uni;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.jw.xfkplugin.dialog.DialogManager;
import com.taobao.weex.annotation.JSMethod;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

public class DialogModule extends UniModule {
//    String TAG = "DialogModule";
//    public static int REQUEST_CODE = 1000;
    @JSMethod(uiThread = true)
    public void showTcDialog(){
        DialogManager.getInstance().showPopupWindow(((Activity)mUniSDKInstance.getContext()));
    }

    @JSMethod(uiThread = true)
    public void CotrolTcScroll(JSONObject options, UniJSCallback callback){
        boolean startScroll= true;
        if (options.containsKey("startScroll")){
            startScroll = options.getBoolean("startScroll");
        }
        if (startScroll){
            DialogManager.getInstance().startScroll((Activity)mUniSDKInstance.getContext());
        }else {
            DialogManager.getInstance().stopScroll((Activity)mUniSDKInstance.getContext());
        }
    }

    @JSMethod(uiThread = true)
    public void preTcScreen(JSONObject options, UniJSCallback callback){
        boolean nextScreen= false;
        if (options.containsKey("nextScreen")){
            nextScreen = options.getBoolean("nextScreen");
        }
        if (nextScreen){
            DialogManager.getInstance().nextScreen((Activity)mUniSDKInstance.getContext());
        }else {
            DialogManager.getInstance().preScreen((Activity)mUniSDKInstance.getContext());
        }
    }

    @UniJSMethod(uiThread = true)
    public void setTcContent(JSONObject options, UniJSCallback callback) {
//        if(callback != null) {
//            JSONObject data = new JSONObject();
//            data.put("code", "success");
//            callback.invoke(data);
//        }
        if (options.containsKey("text")){
            String text = options.getString("text");
            DialogManager.getInstance().setTvContent(text);
        }
        if (options.containsKey("size")){
            Integer size = options.getInteger("size");
            DialogManager.getInstance().setTvSize(size);
        }
        if (options.containsKey("speed")){
            Integer speed = options.getInteger("speed");
            DialogManager.getInstance().setScrollSpeed(speed);
        }
        if (options.containsKey("color")){
            String color = options.getString("color");
            DialogManager.getInstance().setTvColor((Activity)mUniSDKInstance.getContext(),color);
        }
        if (options.containsKey("alpha")){
            Integer alpha = options.getInteger("alpha");
            DialogManager.getInstance().setViewAlpha(alpha);
        }
    }

//    //run JS thread
//    @UniJSMethod(uiThread = false)
//    public JSONObject testSyncFunc(){
//        JSONObject data = new JSONObject();
//        data.put("code", "success");
//        return data;
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == REQUEST_CODE && data.hasExtra("respond")) {
//            Log.e("TestModule", "原生页面返回----"+data.getStringExtra("respond"));
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }

//    @UniJSMethod(uiThread = true)
//    public void gotoNativePage(){
//        if(mUniSDKInstance != null && mUniSDKInstance.getContext() instanceof Activity) {
//            Intent intent = new Intent(mUniSDKInstance.getContext(), NativePageActivity.class);
//            ((Activity)mUniSDKInstance.getContext()).startActivityForResult(intent, REQUEST_CODE);
//        }
//    }
}
