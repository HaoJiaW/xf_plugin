package com.jw.tcdialogplugin.uni;

import android.app.Activity;

import com.alibaba.fastjson.JSONObject;
import com.jw.tcdialogplugin.VarManger;
import com.jw.tcdialogplugin.dialog.DialogManager;
import com.taobao.weex.annotation.JSMethod;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

public class DialogModule extends UniModule {
    //    String TAG = "DialogModule";
//    public static int REQUEST_CODE = 1000;
    @JSMethod(uiThread = true)
    public void showTcDialog(JSONObject options, UniJSCallback callback){
        int dialogType = 0;
        if (options.containsKey("dialogType")){
            dialogType = options.getInteger("dialogType");
        }
        if(callback != null) {
            JSONObject data = new JSONObject();
            String result = DialogManager.getInstance().showPopupWindow(((Activity)mUniSDKInstance.getContext()),dialogType);
            data.put("result",result);
            callback.invoke(data);
        }else {
            DialogManager.getInstance().showPopupWindow(((Activity)mUniSDKInstance.getContext()),dialogType);
        }
    }

    @JSMethod(uiThread = true)
    public void closeTcDialog(){
        DialogManager.getInstance().closeTcDialog();
    }

    @JSMethod(uiThread = true)
    public void hideTcDialog(){
        DialogManager.getInstance().hideTcDialog();
    }

    @JSMethod(uiThread = true)
    public void resetToTop(){
        DialogManager.getInstance().resetToTop();
    }

    @JSMethod(uiThread = true)
    public void setLandSpace(JSONObject options, UniJSCallback callback){
        boolean landSpace= true;
        if (options.containsKey("landSpace")){
            landSpace = options.getBoolean("landSpace");
        }
        boolean otherDirection= true;
        if (options.containsKey("otherDirection")){
            otherDirection = options.getBoolean("otherDirection");
        }
        DialogManager.getInstance().setLandSpace(landSpace,otherDirection);
    }


    @JSMethod(uiThread = true)
    public void getScrollStatus(JSONObject options, UniJSCallback callback){
        if(callback != null) {
            JSONObject data = new JSONObject();
            data.put("scrollStatus",DialogManager.getInstance().getScrollStatus() );
            callback.invoke(data);
        }
    }


    @JSMethod(uiThread = true)
    public void cotrolTcScroll(JSONObject options, UniJSCallback callback){
        boolean startScroll= true;
        boolean animation;
        if (options.containsKey("startScroll")){
            startScroll = options.getBoolean("startScroll");
        }
        if (options.containsKey("animation")){
            animation = options.getBoolean("animation");
            if (startScroll){
                VarManger.fristStartTc = animation;
            }
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


    @JSMethod(uiThread = true)
    public void setDialogParams(JSONObject options, UniJSCallback callback){
        if (options.containsKey("marginTop")){
            int marginTop = options.getInteger("marginTop");
            if (marginTop!=0){
                DialogManager.getInstance().setMarginTop(marginTop);
            }
        }
        int dialogWidth =0;
        if (options.containsKey("dialogWidth")) {
            dialogWidth = options.getInteger("dialogWidth");
        }
        int dialogHeight =0;
        if (options.containsKey("dialogHeight")) {
            dialogHeight = options.getInteger("dialogHeight");
        }
        if (dialogHeight!=0 || dialogWidth!=0){
            DialogManager.getInstance().setWidhtOrHeight(dialogWidth,dialogHeight);
        }
        int dialogPaddingTop =0;
        if (options.containsKey("dialogPaddingTop")) {
            dialogPaddingTop = options.getInteger("dialogPaddingTop");
        }
        int dialogPaddingBottom =0;
        if (options.containsKey("dialogPaddingBottom")) {
            dialogPaddingBottom = options.getInteger("dialogPaddingBottom");
        }
        if (dialogPaddingTop!=0 || dialogPaddingBottom!=0){
            DialogManager.getInstance().setPaddingTopOrPaddingBottom((Activity)mUniSDKInstance.getContext(),dialogPaddingTop,dialogPaddingBottom);
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
            DialogManager.getInstance().setTvColor(color);
        }
        if (options.containsKey("bgColor")){
            String color = options.getString("bgColor");
            DialogManager.getInstance().setBgColor(color);
        }
        if (options.containsKey("alpha")){
            Integer alpha = options.getInteger("alpha");
            DialogManager.getInstance().setViewAlpha(alpha);
        }
        if (options.containsKey("baselineColor")){
            String baselineColor= options.getString("baselineColor");
            DialogManager.getInstance().setBaselineColor(baselineColor);
        }
        if (options.containsKey("baselineVisible")){
            Boolean baselineVisible= options.getBoolean("baselineVisible");
            DialogManager.getInstance().setBaselineVisible(baselineVisible);
        }
        if (options.containsKey("countTimeTextColor")){
            String countTimeTextColor= options.getString("countTimeTextColor");
            DialogManager.getInstance().setCountTimeTextColor(countTimeTextColor);
        }
    }


    @JSMethod(uiThread = true)
    public void setTextMirror(JSONObject options, UniJSCallback callback){
        int direction = 1;
        if (options.containsKey("direction")){
            direction = options.getInteger("direction");
        }
        DialogManager.getInstance().setTextViewMirror(direction);
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
