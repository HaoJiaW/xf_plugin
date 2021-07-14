package com.example.athree_MeisheSdk;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.athree_MeisheSdk.utils.PathUtils;
import com.meicam.sdk.NvsAVFileInfo;
import com.meicam.sdk.NvsMediaFileConvertor;
import com.meicam.sdk.NvsStreamingContext;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.adapter.URIAdapter;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;

import java.util.Hashtable;

import static com.example.athree_MeisheSdk.utils.PathUtils.getCharacterAndNumber;


public class MSAudioWXModule extends WXSDKEngine.DestroyableModule   {




    public  static Activity Mainactivity;

    private static JSCallback static_CB;
    private static final String                 TAG = "####mirror";

    public static Context mContext;

    private NvsStreamingContext mStreamingContext;

    public double temp = 0;


    @JSMethod(uiThread = true)
    public void getAudio(JSONObject options, JSCallback  callback) {


        String path = options.getString("path");

//        path = "/sdcard/1604885387375548.mp4";

        mContext = mWXSDKInstance.getContext();
        Mainactivity = (Activity)mWXSDKInstance.getContext();


        NvsMediaFileConvertor convertor = new NvsMediaFileConvertor();

        temp = 0;

        String fileName = getCharacterAndNumber( ) + ".mp3";
        fileName =   PathUtils.getFileDirPath( Mainactivity.getCacheDir().getAbsolutePath(), fileName);


        mStreamingContext = NvsStreamingContext.getInstance();

        NvsAVFileInfo info = mStreamingContext.getAVFileInfo(path);
        long duration = info.getDuration();

        if(duration <= 0)
        {
            Toast.makeText(Mainactivity,"视频持续时间小于0",Toast.LENGTH_LONG).show();
            return;
        }


        convertor.setMeidaFileConvertorCallback(new NvsMediaFileConvertor.MeidaFileConvertorCallback(){

            @Override
            public void onProgress(long l, float v) {

                Log.e("","");


                if(temp == 0||  (v ) > temp+ 0.1)
                    {

                        temp =  v;

                        JSONObject obj = new JSONObject();
                        obj.put("state","loading"); ;
                        obj.put("rate",temp);
                        detailData(callback,true,obj);
                    }

            }

            @Override
            public void onFinish(long l, String s, String s1, int i) {
                Log.e("","");


                JSONObject obj = new JSONObject();
                obj.put("state","loaded");
                obj.put("rate",1);
                obj.put("inFile",s);
                obj.put("outFile",s1);
                detailData(callback,true,obj);

            }

            @Override
            public void notifyAudioMuteRage(long l, long l1, long l2) {
                Log.e("","");

            }
        },true);




//        duration = duration/1000000;

        Hashtable<String,Object> config = new Hashtable<>();
        config.put(NvsMediaFileConvertor.CONVERTOR_NO_VIDEO,true);

        convertor.convertMeidaFile(path,fileName,false,0,duration,config);


    }


    @Override
    public void onActivityResume() {
        super.onActivityResume();

    }

    @Override
    public void onActivityDestroy()
    {
        super.onActivityDestroy();

    }

    @Override
    public void onActivityPause()
    {
        super.onActivityPause();

    }

    @Override
    public void destroy() {

    }

    public static void detailData(JSCallback  CB,   boolean keep,   JSONObject result)
    {
        Log.e("####",result.toJSONString());
        if(keep)
            CB.invokeAndKeepAlive(result);
        else
            CB.invoke(result);


    }

}
