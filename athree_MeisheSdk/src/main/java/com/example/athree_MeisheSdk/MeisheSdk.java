package com.example.athree_MeisheSdk;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.opengl.EGLContext;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.athree_MeisheSdk.utils.Constants;
import com.example.athree_MeisheSdk.utils.FileUtils;
import com.example.athree_MeisheSdk.utils.Logger;
import com.example.athree_MeisheSdk.utils.MediaScannerUtil;
import com.example.athree_MeisheSdk.utils.PathUtils;
import com.example.athree_MeisheSdk.utils.asset.NvAssetManager;
import com.example.athree_MeisheSdk.utils.dataInfo.ClipInfo;
import com.example.athree_MeisheSdk.utils.permission.PermissionsActivity;
import com.example.athree_MeisheSdk.utils.permission.PermissionsChecker;
import com.meicam.sdk.NvsAVFileInfo;
import com.meicam.sdk.NvsAssetPackageManager;
import com.meicam.sdk.NvsCaptureVideoFx;
import com.meicam.sdk.NvsLiveWindow;
import com.meicam.sdk.NvsRational;
import com.meicam.sdk.NvsSize;
import com.meicam.sdk.NvsStreamingContext;
import com.meicam.sdk.NvsVideoStreamInfo;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.dom.WXAttr;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static com.example.athree_MeisheSdk.utils.Constants.HUMAN_AI_TYPE_MS;
import static com.example.athree_MeisheSdk.utils.Constants.HUMAN_AI_TYPE_NONE;
import static com.meicam.sdk.NvsStreamingContext.STREAMING_ENGINE_RECORDING_FLAG_FLIP_HORIZONTALLY;


public class MeisheSdk extends WXComponent<FrameLayout>  implements NvsStreamingContext.CaptureDeviceCallback, NvsStreamingContext.CaptureRecordingDurationCallback, NvsStreamingContext.CaptureRecordingStartedCallback  {

    private static final String TAG = "SmartPlayer";




    private boolean pauseRecording = false;
    private int mirror ;


    private String urlWater ;
    private Boolean enableWater ;

    private SensorManager mSensorManager;
    private OrientationListener mOrientationListener;

    private HandlerThread mHandlerThread;
    // 最大时长
    private long mMaxDuration = 30;

//    private ArrayList<Long> mRecordTimeList = new ArrayList<>();
    private ArrayList<String> mRecordFileList = new ArrayList<>();

//    private long mEachRecodingVideoTime = 0, mEachRecodingImageTime = 4000000;
//    private FrameLayout mPreviewLayout;

    /*
     * 人脸初始化完成的标识
     * Face initialization completed logo
     * */
    private boolean arSceneFinished = false;
    /*
     * 记录人脸模块正在初始化
     * Recording face module is initializing
     * */
    private boolean initARSceneing = true;


    private int mCanUseARFaceType = HUMAN_AI_TYPE_NONE;

    private FrameLayout MainView;

    private NvsLiveWindow XXXXXView;
    private NvsStreamingContext mStreamingContext;
    public Activity MainActivity;
    public Context mContext;


    public int captureResolution  = 2;
    // 权限相关
    private PermissionsChecker mPermissionsChecker;
    List<String> permissionList;
//    public  String currentFile;




//    private long mAllRecordingTime = 0;
    private String mCurRecordVideoPath;
    private NvAssetManager mAssetManager;
    private int mCurrentDeviceIndex;
    private boolean mIsSwitchingCamera;
    static final int REQUEST_CODE = 123;

    public static final int INIT_ARSCENE_COMPLETE_CODE = 201;
    public static final int INIT_ARSCENE_FAILURE_CODE = 202;

    private NvsRational Rational  = null;

    private NvsCaptureVideoFx mArSceneFaceEffect;

    private int Orientation;

      public int mOrientation;

    private int Ratio;

    private int  resolution = 720;


    private NvsCaptureVideoFx mCurCaptureVideoFxSHUINEN;
    private NvsCaptureVideoFx mCurCaptureVideoFxJIAOSE;
    private NvsCaptureVideoFx mCurCaptureVideoFxMIRROR;


    // 录制操作开始
    private boolean mOperateStarted = false;



    public MeisheSdk(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);

        WXAttr attr =  basicComponentData.getAttrs();


        mMaxDuration =   Integer.parseInt((String)attr.get("maxTime"));

        MainActivity = (Activity)instance.getContext();
        mContext = instance.getContext();




//
    }


    public MeisheSdk(WXSDKInstance instance, WXVContainer parent, int type, BasicComponentData basicComponentData) {
        super(instance, parent, type, basicComponentData);
    }
    @Override
    protected FrameLayout initComponentHostView(@NonNull Context context)
    {
        MainActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

         MainView = new FrameLayout(context);

        XXXXXView = new NvsLiveWindow(context);


        mStreamingContext = NvsStreamingContext.getInstance();
        mStreamingContext.removeAllCaptureVideoFx();





        mArSceneFaceEffect = mStreamingContext.appendBuiltinCaptureVideoFx("AR Scene");

        mStreamingContext.setCaptureDeviceCallback( this);
        mStreamingContext.setCaptureRecordingDurationCallback(  this);
        mStreamingContext.setCaptureRecordingStartedCallback(  this);


        if (mStreamingContext.getCaptureDeviceCount() == 0) {
            Toast.makeText(MainActivity,"mStreamingContext.getCaptureDeviceCount() == 0",Toast.LENGTH_LONG).show();
//
        }
        if (!mStreamingContext.connectCapturePreviewWithLiveWindow(XXXXXView)) {
            Log.e(TAG, "Failed to connect capture preview with livewindow!");
            Toast.makeText(MainActivity,"Failed to connect capture preview with livewindow!",Toast.LENGTH_LONG).show();
//            return;
        }

        mCurrentDeviceIndex = 1;
//        /*
//         * 采集设备数量判定
//         * Judging the count of collection equipment
//         * */
//        if (mStreamingContext.getCaptureDeviceCount() > 1) {
//            mSwitchFacingLayout.setEnabled(true);
//        } else {
//            mSwitchFacingLayout.setEnabled(false);
//        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkPermissions();


            if (!isCameraEnable()|| !isRecordAudioEnable()) {
                String[] permissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};
                ActivityCompat.requestPermissions(MainActivity, permissions, 10);
            }
            else
            {
                initARSceneEffect();

            }

        } else {

            initARSceneEffect();
            try {
                startCapturePreview(false,Rational);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity,"startCapturePreviewException: initCapture failed,under 6.0 device may has no access to camera",Toast.LENGTH_LONG);

            }
        }


//        FrameLayout.LayoutParams frameLayout =new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//        );
//        MainView.setLayoutParams(frameLayout);


        FrameLayout.LayoutParams frameLayout2 =new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        XXXXXView.setLayoutParams(frameLayout2);





        mSensorManager = (SensorManager)MainActivity.getSystemService(Context.SENSOR_SERVICE);
        mOrientationListener = new OrientationListener(newOrientation -> {
            //设置屏幕方向
            //setRequestedOrientation(newOrientation);


            mOrientation =  newOrientation;

            int ooo = 0;

            JSONObject json =new JSONObject();
            json.put("type","orientChange");

            if(mOrientation==1)
                json.put("Orient",1);
            else if(mOrientation==8)
                json.put("Orient",3);
            else if(mOrientation==0)
                json.put("Orient",4);
            else if(mOrientation==9)
                json.put("Orient",2);

            Log.e("###newOrientation -> ",json.toJSONString());


            getInstance().fireGlobalEventCallback("onevent",json);


        });
        mSensorManager.registerListener(mOrientationListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);


        StringBuilder packageIdBuilder = new StringBuilder();
        packageIdBuilder.setLength(0);

        String licensePath = "assets:/filter/668F3E1B-2F84-4D23-AE7A-041D87EE4A82.lic";
      boolean  cg =   installAssetPackage("assets:/filter/668F3E1B-2F84-4D23-AE7A-041D87EE4A82.1.videofx",licensePath,packageIdBuilder);

            licensePath = "assets:/beauty/971C84F9-4E05-441E-A724-17096B3D1CBD.lic";
        cg  =   installAssetPackage("assets:/beauty/971C84F9-4E05-441E-A724-17096B3D1CBD.2.videofx",licensePath,packageIdBuilder);



        licensePath = "assets:/filter/473EB7E9-469B-49D3-8F83-33F0AC48A49F.lic";
        cg  =   installAssetPackage("assets:/filter/473EB7E9-469B-49D3-8F83-33F0AC48A49F.1.videofx",licensePath,packageIdBuilder);





        MainView.addView(XXXXXView);


        RectF rectFrame = new RectF();
        rectFrame.set(XXXXXView.getX(), XXXXXView.getY(),
                XXXXXView.getX() + XXXXXView.getWidth(),
                XXXXXView.getY() + XXXXXView.getHeight());


        mStreamingContext.setAutoExposureRect(rectFrame);



        return   MainView;

    }

    private void initARSceneEffect() {
        mCanUseARFaceType = NvsStreamingContext.hasARModule();
        /*
         *  初始化AR Scene，全局只需一次
         * Initialize AR Scene, only once globally
         * */
        if (mCanUseARFaceType == HUMAN_AI_TYPE_MS && !arSceneFinished) {
            if (mHandlerThread == null) {
                mHandlerThread = new HandlerThread("handlerThread");
                mHandlerThread.start();
            }
            Handler initHandler = new Handler(mHandlerThread.getLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    String modelPath = null;
                    String licensePath = null;
                    String faceModelName = null;
                    String className = null;
//                    if (BuildConfig.HUMAN_AI_TYPE.equals(BUILD_HUMAN_AI_TYPE_MS)) {
                    modelPath = "/facemode/ms_face_v1.1.2.model";
                    faceModelName = "ms_face_v1.1.2.model";
                    className = "facemode";
                    licensePath = "assets:/facemode/tt_face.license";
//                    } else if (BuildConfig.HUMAN_AI_TYPE.equals(BUILD_HUMAN_AI_TYPE_MS_ST)) {
//                        modelPath = "/facemode/tt_face.model";
//                        faceModelName = "tt_face.model";
//                        className = "facemode";
//                        licensePath = "assets:/facemode/tt_face.license";
//                    } else if (BuildConfig.HUMAN_AI_TYPE.equals(BUILD_HUMAN_AI_TYPE_FU))
//                    {
//                        modelPath = "/facemode/fu/fu_face_v3.model";
//                        faceModelName = "fu_face_v3.model";
//                        className = "facemode/fu";
//                        licensePath = "assets:/facemode/fu/fu_face_v3.license";
//                    }

                    boolean copySuccess = FileUtils.copyFileIfNeed(MainActivity, faceModelName, className);
                    Logger.e(TAG, "copySuccess-->" + copySuccess);

                    File rootDir = MainActivity.getExternalFilesDir(null);
                    String destModelDir = rootDir + modelPath;

                    boolean initSuccess = NvsStreamingContext.initHumanDetection(MSHookProxy.getmContext(),
                            destModelDir, licensePath,
                            NvsStreamingContext.HUMAN_DETECTION_FEATURE_FACE_LANDMARK | NvsStreamingContext.HUMAN_DETECTION_FEATURE_FACE_ACTION);
                    Logger.e(TAG, "initSuccess-->" + initSuccess);
                    String fakefacePath = "assets:/facemode/fakeface.dat";
                    boolean fakefaceSuccess = NvsStreamingContext.setupHumanDetectionData(NvsStreamingContext.HUMAN_DETECTION_DATA_TYPE_FAKE_FACE, fakefacePath);
                    Logger.e(TAG, "fakefaceSuccess-->" + fakefaceSuccess);
                    String maleupPath = "assets:/facemode/makeup.dat";
                    boolean makeupSuccess = NvsStreamingContext.setupHumanDetectionData(NvsStreamingContext.HUMAN_DETECTION_DATA_TYPE_MAKEUP, maleupPath);
                    Logger.e(TAG, "makeupSuccess-->" + makeupSuccess);
                    if (initSuccess) {
//                        mHandler.sendEmptyMessage(INIT_ARSCENE_COMPLETE_CODE);

                        try {
                            startCapturePreview(false,Rational);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity,"startCapturePreviewException: initCapture failed,under 6.0 device may has no access to camera",Toast.LENGTH_LONG);

                        }
                    } else {
                        Toast.makeText(MainActivity,"Initializing ARScene failed",Toast.LENGTH_LONG).show();
//                        mHandler.sendEmptyMessage(INIT_ARSCENE_FAILURE_CODE);
                    }
                    return false;
                }
            });
            initHandler.sendEmptyMessage(1);
        } else {
            initARSceneing = false;
        }
    }
    public static List<String> getAllPermissionsList() {
        ArrayList<String> newList = new ArrayList<>();
        newList.add(Manifest.permission.CAMERA);
        newList.add(Manifest.permission.RECORD_AUDIO);
        newList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        newList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return newList;
    }
    private boolean installAssetPackage(String filterPackageFilePath, String lic,StringBuilder packageId){



        int error = mStreamingContext.getAssetPackageManager().installAssetPackage(filterPackageFilePath, lic, NvsAssetPackageManager.ASSET_PACKAGE_TYPE_VIDEOFX, true, packageId);
        if (error == NvsAssetPackageManager.ASSET_PACKAGE_MANAGER_ERROR_NO_ERROR
                || error == NvsAssetPackageManager.ASSET_PACKAGE_MANAGER_ERROR_ALREADY_INSTALLED) {
            if(error == NvsAssetPackageManager.ASSET_PACKAGE_MANAGER_ERROR_ALREADY_INSTALLED){
                mStreamingContext.getAssetPackageManager().upgradeAssetPackage(filterPackageFilePath, null, NvsAssetPackageManager.ASSET_PACKAGE_TYPE_VIDEOFX, true, packageId);
            }
            return true;
        }else {
            Logger.e(TAG,"Douyin installAssetPackage Failed = " + packageId.toString());
        }
        return false;
    }
    public void checkPermissions() {
        if (mPermissionsChecker == null) {
            mPermissionsChecker = new PermissionsChecker(MainActivity);
        }
        permissionList = getAllPermissionsList();
        permissionList = mPermissionsChecker.checkPermission(permissionList);
        String[] permissions = new String[permissionList.size()];
        permissionList.toArray(permissions);
        if (!permissionList.isEmpty()) {
            startPermissionsActivity(REQUEST_CODE, permissions);
        }
    }
    private void startPermissionsActivity(int code, String... permission) {
        PermissionsActivity.startActivityForResult(MainActivity, code, permission);
    }

    private boolean startCapturePreview(boolean deviceChanged, NvsRational rational) {


            if (!mStreamingContext.startCapturePreview(mCurrentDeviceIndex, captureResolution,
                    NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_DONT_USE_SYSTEM_RECORDER |
                            NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_CAPTURE_BUDDY_HOST_VIDEO_FRAME |
                            NvsStreamingContext.STREAMING_ENGINE_CAPTURE_FLAG_STRICT_PREVIEW_VIDEO_SIZE, rational)) {
                Log.e(TAG, "Failed to start capture preview!");
                return false;
            }

             mArSceneFaceEffect.setBooleanVal("Beauty Effect", true);

        mArSceneFaceEffect.setBooleanVal("Beauty Shape", true);;


        mArSceneFaceEffect.setStringVal("Default Beauty Lut File", "assets:/capture/preset.mslut");
        mArSceneFaceEffect.setStringVal("Whitening Lut File", "assets:/capture/filter.png");
        mArSceneFaceEffect.setBooleanVal("Whitening Lut Enabled", true);


//        NvsCaptureVideoFx videofx = mStreamingContext.appendBuiltinCaptureVideoFx("Definition");
//        videofx.setFloatVal("Intensity",0.4);


        return true;

    }


    //准备写一个获取当前图片的方法，qaq
    @JSMethod(uiThread = true)
    public void StartRecord(JSONObject jsonObject){


        if (mOperateStarted) {
            Toast.makeText(MainActivity,"当前正在录像，无法重新开始",Toast.LENGTH_LONG);

            return;
        }


        if (pauseRecording) {
            Toast.makeText(MainActivity,"当前为暂停录像，无法重新开始",Toast.LENGTH_LONG);
            return;
        }

//        if (mAllRecordingTime   >= mMaxDuration)
        {
//            Toast.makeText(MainActivity,"超过最大录制时长",Toast.LENGTH_LONG);
        }

        mCurRecordVideoPath = PathUtils.getRecordVideoPath(MainActivity);

//        mEachRecodingVideoTime = 0;
        //当前未在视频录制状态，则启动视频录制。此处使用带特效的录制方式
        /*
         * 当前未在视频录制状态，则启动视频录制。此处使用带特效的录制方式
         * If video recording is not currently in progress, start video recording. Use the recording method with special effects here
         */


        int mm = Orientation==1?0:NvsStreamingContext.STREAMING_ENGINE_RECORDING_FLAG_IGNORE_VIDEO_ROTATION;
        if(mirror == 1)
            mm = mm|STREAMING_ENGINE_RECORDING_FLAG_FLIP_HORIZONTALLY;


        Hashtable<String,Object> config = new Hashtable<>();

        config.put(NvsStreamingContext.RECORD_GOP_SIZE,5);

        if (!mStreamingContext.startRecording(mCurRecordVideoPath,mm,  config)) {
            return;
        }


//        mRecordFileList.add(mCurRecordVideoPath);


    }
    //准备写一个获取当前图片的方法，qaq
    @JSMethod(uiThread = true)
    public void StopRecord(JSONObject jsonObject){

        if (!mOperateStarted) {
            return;
        }
        pauseRecording = false;
        mStreamingContext.stopRecording();
//            mAllRecordingTime += mEachRecodingVideoTime;
//            mRecordTimeList.add(mEachRecodingVideoTime);


    }
    @JSMethod(uiThread = true)
    public void play(JSONObject jsonObject){

        if(mCurRecordVideoPath == null ||mCurRecordVideoPath.length() == 0)
        {
            Toast.makeText(MainActivity, "不存在临时录像文件", Toast.LENGTH_SHORT).show();
        return;
        }
        MainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent v = new Intent(Intent.ACTION_VIEW);
                v.setDataAndType(Uri.parse(mCurRecordVideoPath), "video/mp4");
                MainActivity.startActivity(v);



                Intent intent = new Intent(Intent.ACTION_VIEW);
                File file = new File(mCurRecordVideoPath);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "video/*");
                MainActivity.startActivity(intent);



            }
        });
    }
    @JSMethod(uiThread = true)
    public void saveRecord(JSONObject jsonObject){

        if(mCurRecordVideoPath == null ||mCurRecordVideoPath.length() == 0)
        {
            Toast.makeText(MainActivity, "不存在录像文件", Toast.LENGTH_SHORT).show();
            return;
        }

        final String outPath  = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                +File.separator+"BeautifyShoot"+File.separator+ System.currentTimeMillis() + ".mp4";


        File outPath2 = new File(Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                +File.separator+"BeautifyShoot"+File.separator);
        if (!outPath2.exists()) {
            outPath2.mkdirs();
        }



        new Thread()
        {
            public void run()
            {
                //要做的工作

                PathUtils.copyFile(mCurRecordVideoPath,outPath);


                JSONObject json =new JSONObject();
                json.put("type","saveRecord");
                getInstance().fireGlobalEventCallback("onevent",json);

                File destFile = new File(mCurRecordVideoPath);
                destFile.delete();

                JSONObject json2 =new JSONObject();
                json2.put("type","deleteRecord");
                getInstance().fireGlobalEventCallback("onevent",json2);


                MediaScannerUtil.scanFile(outPath, "video/mp4");
                mCurRecordVideoPath = "";




            }
        }.start();








//                MediaScannerConnection.scanFile( destFile.getPath(), "video/mp4");

//        MediaScannerConnection mMediaScanner = new MediaScannerConnection(MainActivity, null);
//        mMediaScanner.connect();
//        if (mMediaScanner !=null && mMediaScanner.isConnected()) {
//            mMediaScanner.scanFile(outPath,"video/mp4");
//        }
//
//        MediaScannerConnection.scanFile(MainActivity, new String[] { destFile.getPath()},  new String[] {  "video/mp4"}, null);



    }
    @JSMethod(uiThread = true)
    public void deleteRecord(JSONObject jsonObject){

        if(mCurRecordVideoPath == null ||mCurRecordVideoPath.length() == 0)
        {
            Toast.makeText(MainActivity, "不存在录像文件", Toast.LENGTH_SHORT).show();
            return;
        }
        File destFile = new File(mCurRecordVideoPath);
        destFile.delete();

        JSONObject json2 =new JSONObject();
        json2.put("type","deleteRecord");
        getInstance().fireGlobalEventCallback("onevent",json2);
        mCurRecordVideoPath = "";

    }

    @JSMethod(uiThread = true)
    public void pauseRecord(JSONObject jsonObject)
    {
        if(!mOperateStarted)
        {
            Toast.makeText(MainActivity, "当前没有正在录像", Toast.LENGTH_SHORT).show();
            return;
        }
        pauseRecording = true;

        mStreamingContext.pauseRecording();

        JSONObject json =new JSONObject();
        json.put("type","pauseRecord");
        getInstance().fireGlobalEventCallback("onevent",json);
        Log.e("#######",json.toJSONString());
    }

    @JSMethod(uiThread = true)
    public void resumeRecord(JSONObject jsonObject)
    {
        if(!pauseRecording)
        {
            Toast.makeText(MainActivity, "当前非暂停录像", Toast.LENGTH_SHORT).show();
            return;
        }
        pauseRecording = false;

        mStreamingContext.resumeRecording();
        JSONObject json =new JSONObject();
        json.put("type","resumeRecord");
        getInstance().fireGlobalEventCallback("onevent",json);
        Log.e("#######",json.toJSONString());
    }

    //准备写一个获取当前图片的方法，qaq
    @JSMethod(uiThread = true)
    public void Flash(JSONObject jsonObject){

        int Flash = jsonObject.getIntValue("Flash");

        mStreamingContext.toggleFlash(Flash==1?true:false);



    }
    //准备写一个获取当前图片的方法，qaq
    @JSMethod(uiThread = true)
    public void Switch(JSONObject jsonObject){

        int Switch = jsonObject.getIntValue("Switch");


            mCurrentDeviceIndex = Switch;


                    startCapturePreview(true , Rational);

    }
    //准备写一个获取当前图片的方法，qaq
    @JSMethod(uiThread = true)
    public void Ratio(JSONObject jsonObject){


        Ratio = jsonObject.getIntValue("Ratio");

        //0    全屏
        //1    9:16
        //2    4:3
        //3    1:1

        if(Ratio == 0) {
            Rational = null;
            XXXXXView.setFillMode(NvsLiveWindow.FILLMODE_PRESERVEASPECTCROP);
        }
        else if(Ratio == 1) {
            Rational = new NvsRational(9, 16);
            XXXXXView.setFillMode(NvsLiveWindow.FILLMODE_PRESERVEASPECTFIT);
        }
        else if(Ratio == 2) {
            Rational = new NvsRational(4, 3);
            XXXXXView.setFillMode(NvsLiveWindow.FILLMODE_PRESERVEASPECTFIT);
        }
        else if(Ratio == 3) {
            Rational = new NvsRational(1, 1);
            XXXXXView.setFillMode(NvsLiveWindow.FILLMODE_PRESERVEASPECTFIT);
        }
        else if(Ratio == 4) {
            Rational = new NvsRational(6, 7);
            XXXXXView.setFillMode(NvsLiveWindow.FILLMODE_PRESERVEASPECTFIT);
        }
        else if(Ratio == 5) {
            Rational = new NvsRational(16, 9);
            XXXXXView.setFillMode(NvsLiveWindow.FILLMODE_PRESERVEASPECTFIT);
        }
        else if(Ratio == 6) {
            Rational = new NvsRational(3, 4);
            XXXXXView.setFillMode(NvsLiveWindow.FILLMODE_PRESERVEASPECTFIT);
        }


        startCapturePreview(true , Rational);

    }
    //准备写一个获取当前图片的方法，qaq
    @JSMethod(uiThread = true)
    public void meiyan(JSONObject jsonObject){

        float meiyan = jsonObject.getIntValue("meiyan")/100.f;;

        mArSceneFaceEffect.setFloatVal("Beauty Strength", meiyan);


    }
    //准备写一个获取当前图片的方法，qaq
    @JSMethod(uiThread = true)
    public void meibai(JSONObject jsonObject){

        float meibai = jsonObject.getIntValue("meibai")/100.f;
        mArSceneFaceEffect.setFloatVal("Beauty Whitening", meibai);

    }

    //准备写一个获取当前图片的方法，qaq
    @JSMethod(uiThread = true)
    public void hongrun(JSONObject jsonObject){

        float hongrun = jsonObject.getIntValue("hongrun")/100.f;
        mArSceneFaceEffect.setFloatVal("Beauty Reddening", hongrun);


    }


    @JSMethod(uiThread = true)
    public void resolution(JSONObject jsonObject) {



        int r = jsonObject.getIntValue("resolution");

        if(r == 720)
        captureResolution = 2;
        else if(r == 1080)
            captureResolution = 3;

        startCapturePreview(true , Rational);

    }

    @JSMethod(uiThread = true)
    public void maxTime(JSONObject jsonObject){

        int maxTime = jsonObject.getIntValue("maxTime");
        mMaxDuration =  maxTime;


    }
    @JSMethod(uiThread = true)
    public void Water(JSONObject jsonObject){

    }
    @JSMethod(uiThread = true)
    public void setVideoFile(JSONObject jsonObject){

    }
    @JSMethod(uiThread = true)
    public void Orientation(JSONObject jsonObject){

        Orientation = jsonObject.getIntValue("Orientation");


    }



    @JSMethod(uiThread = true)
    public void focal(JSONObject jsonObject){

       int focal = jsonObject.getIntValue("focal");


        mStreamingContext.setZoom(focal);


    }

    @JSMethod(uiThread = true)
    public void exposure(JSONObject jsonObject){

//        int exposure = jsonObject.getIntValue("exposure");
//
////        mStreamingContext.setExposureCompensation(exposure);
//        mStreamingContext.setAutoExposureRect();

    }


    @JSMethod(uiThread = true)
    public void FaceSize(JSONObject jsonObject){

        float FaceSize = jsonObject.getIntValue("FaceSize")/100.f;
        mArSceneFaceEffect.setFloatVal("Face Size Warp Degree", FaceSize);


    }
    @JSMethod(uiThread = true)
    public void EyeSize(JSONObject jsonObject){

        float EyeSize = jsonObject.getIntValue("EyeSize")/100.f;
        mArSceneFaceEffect.setFloatVal("Eye Size Warp Degree", -EyeSize);


    }

    @JSMethod(uiThread = true)
    public void zhailian(JSONObject jsonObject){

        float zhailian = jsonObject.getIntValue("zhailian")/100.f;
        mArSceneFaceEffect.setFloatVal("Face Width Warp Degree", -zhailian);


    }

    @JSMethod(uiThread = true)
    public void xiaolian(JSONObject jsonObject){

        float xiaolian = jsonObject.getIntValue("xiaolian")/100.f;
        mArSceneFaceEffect.setFloatVal("Face Length Warp Degree", -xiaolian);


    }
    @JSMethod(uiThread = true)
    public void zuiba(JSONObject jsonObject){

        float zuiba = jsonObject.getIntValue("zuiba")/100.f;
        mArSceneFaceEffect.setFloatVal("Mouth Size Warp Degree", -zuiba);


    }
    @JSMethod(uiThread = true)
    public void shoubi(JSONObject jsonObject){

        float shoubi = jsonObject.getIntValue("shoubi")/100.f;
        mArSceneFaceEffect.setFloatVal("Nose Width Warp Degree", -shoubi);


    }
    @JSMethod(uiThread = true)
    public void etou(JSONObject jsonObject){

        float etou = jsonObject.getIntValue("etou")/100.f;
        mArSceneFaceEffect.setFloatVal("Forehead Height Warp Degree", -etou);


    }
    @JSMethod(uiThread = true)
    public void xiaba(JSONObject jsonObject){

        float xiaba = jsonObject.getIntValue("xiaba")/100.f;
        mArSceneFaceEffect.setFloatVal("Chin Length Warp Degree", xiaba);


    }

    @JSMethod(uiThread = true)
    public void shuinen(JSONObject jsonObject){

        float shuinen = jsonObject.getIntValue("shuinen")/100.f;

        if(mCurCaptureVideoFxSHUINEN == null)
            mCurCaptureVideoFxSHUINEN = mStreamingContext.appendPackagedCaptureVideoFx("668F3E1B-2F84-4D23-AE7A-041D87EE4A82");
        mCurCaptureVideoFxSHUINEN.setFilterIntensity(shuinen);
    }

    @JSMethod(uiThread = true)
    public void jiaose(JSONObject jsonObject){

        float jiaose = jsonObject.getIntValue("jiaose")/100.f;

        if(mCurCaptureVideoFxJIAOSE == null)
            mCurCaptureVideoFxJIAOSE = mStreamingContext.appendPackagedCaptureVideoFx("971C84F9-4E05-441E-A724-17096B3D1CBD");
        mCurCaptureVideoFxJIAOSE.setFilterIntensity(jiaose);


    }

    @JSMethod(uiThread = true)
    public void mirror(JSONObject jsonObject){

        mirror = jsonObject.getIntValue("mirror");

//        if(mirror == 0)
//        {
//            if(mCurCaptureVideoFxMIRROR !=null)
//            {
//            mStreamingContext.removeCaptureVideoFx(mCurCaptureVideoFxMIRROR.getIndex());
//
//                mCurCaptureVideoFxMIRROR.setFilterIntensity(0);
//
//                mCurCaptureVideoFxMIRROR = null;
//            }
//        }
//        else
//        {
//            if(mCurCaptureVideoFxMIRROR == null)
//            {
//                mCurCaptureVideoFxMIRROR = mStreamingContext.appendPackagedCaptureVideoFx("473EB7E9-469B-49D3-8F83-33F0AC48A49F");
//
//                mCurCaptureVideoFxMIRROR.setInverseRegion(true);
//            }
//        }



//        if(mCurCaptureVideoFxJIAOSE == null)
//            mCurCaptureVideoFxJIAOSE = mStreamingContext.appendPackagedCaptureVideoFx("971C84F9-4E05-441E-A724-17096B3D1CBD");
//        mCurCaptureVideoFxJIAOSE.setFilterIntensity(jiaose);


    }





//    // ---------------------------------- 相机预览数据回调 ------------------------------------------
//    @Override
//    public void onPreviewFrame(byte[] data) {
//        Log.d(TAG, "onPreviewFrame: width - " + mCameraController.getPreviewWidth()
//                + ", height - " + mCameraController.getPreviewHeight());
////        FaceTracker.getInstance()
////                .trackFace(data, mCameraController.getPreviewWidth(),
////                        mCameraController.getPreviewHeight());
//    }


    //准备写一个获取当前图片的方法，qaq
    @JSMethod(uiThread = true)
    public void start(JSONObject jsonObject){


    }








    /**
     * 判断是否可以录制
     *
     * @return
     */
    private boolean isRecordAudioEnable() {
        return  permissionChecking(MainActivity, Manifest.permission.RECORD_AUDIO);
    }

    /**
     * 判断是否可以读取本地媒体
     *
     * @return
     */
    private boolean isStorageEnable() {
        return  permissionChecking(MainActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public  boolean permissionChecking(@NonNull Context context, @NonNull String permission) {
        int targetVersion = 1;

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            targetVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException var4) {
        }

        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23 && targetVersion >= 23) {
            result = ContextCompat.checkSelfPermission(context, permission) == 0;
        } else {
            result = PermissionChecker.checkSelfPermission(context, permission) == 0;
        }

        return result;
    }

    /**
     * 是否允许拍摄
     *
     * @return
     */
    private boolean isCameraEnable() {
        return  permissionChecking(MainActivity, Manifest.permission.CAMERA);
    }



    @Override
    public void onActivityResume() {
        super.onActivityResume();

        startCapturePreview(true , Rational);

//        openCamera();

    }

    @Override
    public void onActivityDestroy()
    {
        super.onActivityDestroy();

        if (mHandlerThread != null) {
            mHandlerThread.quit();
            mHandlerThread = null;
        }

        if (mStreamingContext != null) {
            mStreamingContext.removeAllCaptureVideoFx();
            mStreamingContext.stop();


            mStreamingContext.setCaptureDeviceCallback( null);
            mStreamingContext.setCaptureRecordingDurationCallback(  null);
            mStreamingContext.setCaptureRecordingStartedCallback(  null);

            mStreamingContext = null;
        }




    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if(requestCode == 10)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //扫码


                initARSceneEffect();

            }

        }
    }

    @Override
    public void onActivityPause()
    {
        super.onActivityPause();

        mStreamingContext.stop();
        StopRecord(null);

//        stopRecording();


    }

    @Override
    public void destroy() {

    }

    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public boolean saveMyBitmap(Bitmap bmp,File fff)  {
        boolean flag = false;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {



//            fff.createNewFile();
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(fff);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                flag = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return flag;
    }





    class MainActivityHandler extends Handler {
        WeakReference<MeisheSdk> mWeakReference;

        public MainActivityHandler(MeisheSdk mainActivityContext) {
            mWeakReference = new WeakReference<>(mainActivityContext);
        }

        @Override
        public void handleMessage(Message msg) {
            final MeisheSdk activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case INIT_ARSCENE_COMPLETE_CODE:
                        /*
                         *  初始化ARScene 完成
                         * Initialization of ARScene completed
                         * */
                        arSceneFinished = true;
                        initARSceneing = false;
                        break;
                    case INIT_ARSCENE_FAILURE_CODE:
                        /*
                         *  初始化ARScene 失败
                         * Initializing ARScene failed
                         * */
                        arSceneFinished = false;
                        initARSceneing = false;
                        break;
                    default:
                        break;

                }
            }
        }
    }


    @Override
    public void onCaptureDeviceCapsReady(int i) {

    }

    @Override
    public void onCaptureDevicePreviewResolutionReady(int i) {

    }

    @Override
    public void onCaptureDevicePreviewStarted(int i) {

    }

    @Override
    public void onCaptureDeviceError(int i, int i1) {

    }

    @Override
    public void onCaptureDeviceStopped(int i) {

    }

    @Override
    public void onCaptureDeviceAutoFocusComplete(int i, boolean b) {

    }

    @Override
    public void onCaptureRecordingFinished(int i) {

        /*
         *  保存到媒体库
         * Save to media library
         * */

//        if (mRecordFileList != null && !mRecordFileList.isEmpty()) {
//            for (String path : mRecordFileList) {
//                if (path == null) {
//                    continue;
//                }
//                if (path.endsWith(".mp4")) {
//                    MediaScannerUtil.scanFile(path, "video/mp4");
//                } else if (path.endsWith(".jpg")) {
//                    MediaScannerUtil.scanFile(path, "image/jpg");
//                }
//            }
//        }



        JSONObject json =new JSONObject();
        json.put("type","StopRecord");
        json.put("Path",mCurRecordVideoPath);
        getInstance().fireGlobalEventCallback("onevent",json);
        Log.e("#######",json.toJSONString());

        mOperateStarted = false;

    }

    @Override
    public void onCaptureRecordingError(int i) {

        pauseRecording = false;
        mOperateStarted = false;
        Toast.makeText(MainActivity,"录像失败，错误码："+i,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onCaptureRecordingDuration(int i, long l) {

//        mEachRecodingVideoTime = l;

        if ( l >= mMaxDuration*1000000) {
            StopRecord(null);
        }

        JSONObject json =new JSONObject();
        json.put("type","currentTime");
        json.put("Time",( l)/1000000);
        getInstance().fireGlobalEventCallback("onevent",json);
        Log.e("#######",json.toJSONString());
    }

    @Override
    public void onCaptureRecordingStarted(int i) {

        mOperateStarted = true;

        JSONObject json =new JSONObject();
        json.put("type","StartRecord");
        getInstance().fireGlobalEventCallback("onevent",json);
        Log.e("#######",json.toJSONString());
    }
}