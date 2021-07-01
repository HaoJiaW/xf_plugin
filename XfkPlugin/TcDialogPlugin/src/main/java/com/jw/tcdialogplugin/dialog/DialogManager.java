package com.jw.tcdialogplugin.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jw.xfkplugin.R;
import com.jw.xfkplugin.VarManger;

import java.util.HashMap;

public class DialogManager {

    private volatile static DialogManager instance;
    private PopupWindow popupWindow;
    private boolean landSpace;
    private TcView tcView;
    private TcViewLandSpace tcViewLandSpace;

    private HashMap<String,Integer> colorIdMap = new HashMap<>();

    public static DialogManager getInstance() {
        if (instance==null){
            synchronized (DialogManager.class){
                if (instance==null){
                    instance = new DialogManager();
                }
            }
        }
        return instance;
    }

    private DialogManager(){
        initColorMap();
    }

    private void initColorMap(){
        colorIdMap.put("#FEFFFE", R.color.t_1);
        colorIdMap.put("#000000", R.color.t_2);
        colorIdMap.put("#6D7A76", R.color.t_3);
        colorIdMap.put("#62D3A8", R.color.t_4);
        colorIdMap.put("#94D443", R.color.t_5);
        colorIdMap.put("#C75427", R.color.t_6);
        colorIdMap.put("#2653CF", R.color.t_7);
        colorIdMap.put("#C42DA7", R.color.t_8);
        colorIdMap.put("#891E5A", R.color.t_9);
        colorIdMap.put("#8C531D", R.color.t_10);
        colorIdMap.put("#E1A158", R.color.t_11);
        colorIdMap.put("#C4AF95", R.color.t_12);
        colorIdMap.put("#8FA9EF", R.color.t_13);
        colorIdMap.put("#E68DD2", R.color.t_14);
        colorIdMap.put("#E68DA1", R.color.t_15);
        colorIdMap.put("#63CFD7", R.color.t_16);
        colorIdMap.put("#2A5F64", R.color.t_17);
        colorIdMap.put("#9DA2B8", R.color.t_18);
        colorIdMap.put("#8FB73A", R.color.t_19);
        colorIdMap.put("#54B571", R.color.t_20);
    }

    public void showPopupWindow(Activity context){
        if (popupWindow==null){
            tcView = new TcView(context);
            tcViewLandSpace = new TcViewLandSpace(context);
            ViewEvent viewEvent = new ViewEvent() {
                @Override
                public void changeWH(int w, int h, int x, int y) {
                    popupWindow.update(x,y,w,h);
                }

                @Override
                public void changeScreenOri(boolean ls) {
                    landSpace = ls;
                    popupWindow.dismiss();
                    popupWindow.setContentView(ls?tcViewLandSpace:tcView);
                    popupWindow.setWidth(ls?VarManger.getDefWidthHeightLandSpace(context)[0]:VarManger.getDefWidthHeight(context)[0]);
                    popupWindow.setHeight(ls?VarManger.getDefWidthHeightLandSpace(context)[1]:VarManger.getDefWidthHeight(context)[1]);
                    popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.TOP,
                            VarManger.getDefXY(context)[0],VarManger.getDefXY(context)[1]);
                }

                @Override
                public void closePopupWindow() {
                    popupWindow.dismiss();
                }
            };
            popupWindow = new PopupWindow(tcView, VarManger.getDefWidthHeight(context)[0],VarManger.getDefWidthHeight(context)[1]);
            tcView.setViewEvent(viewEvent);
            tcViewLandSpace.setViewEvent(viewEvent);
            popupWindow.setOutsideTouchable(false);
            popupWindow.setFocusable(false);
        }
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.TOP,VarManger.getDefXY(context)[0],VarManger.getDefXY(context)[1]);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
////                    TimeUnit.SECONDS.sleep(1);
//                    context.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            popupWindow.update(100,100,popupWindow.getWidth()+20,popupWindow.getHeight()+20);
//                        }
//                    });
//
////                    TimeUnit.SECONDS.sleep(1);
//                    context.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            startScroll(context);
////                            popupWindow.update(100,100,popupWindow.getWidth()+20,popupWindow.getHeight()+20);
//                        }
//                    });
//
////                    TimeUnit.SECONDS.sleep(1);
//                    context.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            setScrollSpeed(100);
////                            popupWindow.update(100,100,popupWindow.getWidth()+20,popupWindow.getHeight()+20);
//                        }
//                    });
//
//                    TimeUnit.SECONDS.sleep(1);
//                    context.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            stopScroll(context);
////                            popupWindow.update(100,100,popupWindow.getWidth()+20,popupWindow.getHeight()+20);
//                        }
//                    });
//
//                    TimeUnit.SECONDS.sleep(1);
//                    context.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            preScreen(context);
////                            popupWindow.update(100,100,popupWindow.getWidth()+20,popupWindow.getHeight()+20);
//                        }
//                    });
//
//                    TimeUnit.SECONDS.sleep(1);
//                    context.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            nextScreen(context);
////                            popupWindow.update(100,100,popupWindow.getWidth()+20,popupWindow.getHeight()+20);
//                        }
//                    });
//
//                    TimeUnit.SECONDS.sleep(1);
//                    context.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            setTvSize(50);
////                            popupWindow.update(100,100,popupWindow.getWidth()+20,popupWindow.getHeight()+20);
//                        }
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    public void setTvContent(String text){
//        if (landSpace){
            tcViewLandSpace.tvContent.setText("\n\n" + text);
//        }else {
            tcView.tvContent.setText("\n\n" + text);
//        }
    }

    public void setTvSize(int size){
        tcViewLandSpace.tvContent.setTextSize(size);
        tcView.tvContent.setTextSize(size);
    }

    public void setTvColor(Context context,String colorStr){
        tcViewLandSpace.tvContent.setTextColor(context.getResources().getColorStateList(colorIdMap.get(colorStr)));
        tcView.tvContent.setTextColor(context.getResources().getColorStateList(colorIdMap.get(colorStr)));
    }

    public void setScrollSpeed(int speed) {
        tcViewLandSpace.tvContent.speed = speed;
        tcView.tvContent.speed = speed;
    }

    public void setViewAlpha(int alpahVal) {
        tcViewLandSpace.setBgAlpha((int)( (alpahVal / 100f) * 255));
        tcView.setBgAlpha((int)( (alpahVal / 100f) * 255));
    }

    /*
     *
     * 启动文字滚动
     *
     * */
    public void startScroll(Context context) {
        if (popupWindow!=null && popupWindow.isShowing()){
            if (landSpace){
                if (!tcViewLandSpace.tvContent.startScroll){
                    tcViewLandSpace.changeTvStatus();
                }else {
                    Toast.makeText(context,"当前处于启动状态",Toast.LENGTH_LONG).show();
                }
            }else {
                if (!tcView.tvContent.startScroll){
                   tcView.changeTvStatus();
                }else {
                    Toast.makeText(context,"当前处于启动状态",Toast.LENGTH_LONG).show();
                }
            }
        }else {
            Toast.makeText(context,"文本框未弹出，请先打开",Toast.LENGTH_LONG).show();
        }
    }

    /*
     *
     * 暂停文字滚动
     *
     * */
    public void stopScroll(Context context){
        if (popupWindow!=null && popupWindow.isShowing()){
            if (landSpace){
                if (tcViewLandSpace.tvContent.startScroll){
                    tcViewLandSpace.changeTvStatus();
                }else {
                    Toast.makeText(context,"当前处于停止状态",Toast.LENGTH_LONG).show();
                }
            }else {
                if (tcView.tvContent.startScroll){
                    tcView.changeTvStatus();
                }else {
                    Toast.makeText(context,"当前处于停止状态",Toast.LENGTH_LONG).show();
                }
            }
        }else {
            Toast.makeText(context,"文本框未弹出，请先打开",Toast.LENGTH_LONG).show();
        }
    }


    /*
     *
     *
     * 设置显示上一屏幕台词
     *
     * */
    public void preScreen(Context context){
        if (popupWindow!=null && popupWindow.isShowing()){
            if (landSpace){
                tcViewLandSpace.scrollToY(false);
            }else {
                tcView.scrollToY(false);
            }
        }else {
            Toast.makeText(context,"文本框未弹出，请先打开",Toast.LENGTH_LONG).show();
        }
    }


    /*
     *
     *
     * 设置显示下一屏幕台词
     *
     * */
    public void nextScreen(Context context){
        if (popupWindow!=null && popupWindow.isShowing()){
            if (landSpace){
                tcViewLandSpace.scrollToY(true);
            }else {
                tcView.scrollToY(true);
            }
        }else {
            Toast.makeText(context,"文本框未弹出，请先打开",Toast.LENGTH_LONG).show();
        }
    }


}
