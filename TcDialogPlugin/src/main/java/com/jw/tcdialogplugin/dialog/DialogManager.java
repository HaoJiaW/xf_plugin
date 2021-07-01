package com.jw.tcdialogplugin.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;


import com.jw.tcdialogplugin.PreferencesUtils;
import com.jw.tcdialogplugin.R;
import com.jw.tcdialogplugin.VarManger;

import java.util.HashMap;

public class DialogManager {

    private volatile static DialogManager instance;
    private PopupWindow popupWindow;
    private boolean landSpace;
    private boolean otherDirection;
    private TcView tcView;
    private TcViewLandSpace tcViewLandSpace;
    private TcViewOtherDirection tcViewOtherDirection;
    private int marginTop;
    private int marginLeft;
    private int popHeight;
    private int popWidth;

    private HashMap<String,Integer> colorIdMap = new HashMap<>();
    private ViewEvent viewEvent;

    private int dialogType;

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

    public String showPopupWindow(Activity context,int dialogType){
        this.dialogType = dialogType;
        StringBuilder sb = new StringBuilder();
        if (popupWindow==null){
            sb.append("dialog init\n");
            tcView = new TcView(context);
            tcView.setDialogType(dialogType);
            tcViewLandSpace = new TcViewLandSpace(context);
            tcViewLandSpace.setDialogType(dialogType);
            tcViewOtherDirection = new TcViewOtherDirection(context);
            tcViewOtherDirection.setDialogType(dialogType);
            viewEvent = new ViewEvent() {
                @Override
                public void changeWH(int w, int h, int x, int y,boolean isR) {
                    popWidth = w;
                    popHeight = h;
                    marginLeft = x;
                    popupWindow.update(x,y,w,h);
                    //更新基准线的最低限制
                    tcView.post(new Runnable() {
                        @Override
                        public void run() {
                            tcView.tvContent.setYLimit(-tcView.tvContent.getHeight()/2);
//                            tcView.tvContent.setLineCount(tcView.tvContent.getLineCount());
//                            tcView.tvContent.setLineHeight(tcView.tvContent.getLineHeight());
                            if (isR){
//                                int height = tcView.tvContent.getHeight();
//                                int h = (int)Math.floor(((double)height)/12);
                                int h =0;
                                tcView.baseline.layout(tcView.baseline.getLeft(),tcView.baseline.getTop()-h,tcView.baseline.getRight(),tcView.baseline.getBottom()-h);
                            }
                        }
                    });
                    tcViewLandSpace.post(new Runnable() {
                        @Override
                        public void run() {
                            tcViewLandSpace.tvContent.setYLimit(-tcViewLandSpace.tvContent.getHeight()/2);
//                            tcViewLandSpace.tvContent.setLineCount(tcViewLandSpace.tvContent.getLineCount());
//                            tcViewLandSpace.tvContent.setLineHeight(tcViewLandSpace.tvContent.getLineHeight());
                            if (isR){
//                                int height = tcViewLandSpace.tvContent.getHeight();
//                                int h = (int)Math.floor(((double)height)/12);
                                int h =0;
                                tcViewLandSpace.baseline.layout(tcViewLandSpace.baseline.getLeft(),tcViewLandSpace.baseline.getTop()-h,tcViewLandSpace.baseline.getRight(),tcViewLandSpace.baseline.getBottom()-h);
                            }
                        }
                    });
                    tcViewOtherDirection.post(new Runnable() {
                        @Override
                        public void run() {
                            tcViewOtherDirection.tvContent.setYLimit(-tcViewOtherDirection.tvContent.getHeight()/2);
//                            tcViewLandSpace.tvContent.setLineCount(tcViewLandSpace.tvContent.getLineCount());
//                            tcViewLandSpace.tvContent.setLineHeight(tcViewLandSpace.tvContent.getLineHeight());
                            if (isR){
//                                int height = tcViewLandSpace.tvContent.getHeight();
//                                int h = (int)Math.floor(((double)height)/12);
                                int h =0;
                                tcViewOtherDirection.baseline.layout(tcViewOtherDirection.baseline.getLeft(),tcViewOtherDirection.baseline.getTop()-h,tcViewOtherDirection.baseline.getRight(),tcViewOtherDirection.baseline.getBottom()-h);
                            }
                        }
                    });
                }

                @Override
                public void changeScreenOri(boolean ls,boolean otherDirection) {
                    landSpace = ls;
                    DialogManager.this.otherDirection = otherDirection;
                    popupWindow.dismiss();
                    popupWindow.setContentView(ls?DialogManager.this.otherDirection?tcViewOtherDirection:tcViewLandSpace:tcView);
                    popWidth = ls? VarManger.getDefWidthHeightLandSpace(context,dialogType)[0]:VarManger.getDefWidthHeight(context,dialogType)[0];
                    popHeight = ls? VarManger.getDefWidthHeightLandSpace(context,dialogType)[1]:VarManger.getDefWidthHeight(context,dialogType)[1];
                    popupWindow.setWidth(popWidth);
                    popupWindow.setHeight(popHeight);
                    setPopupWindowShowLocation(context.getWindow().getDecorView(),VarManger.getDefXY(context,dialogType)[0],VarManger.getDefXY(context,dialogType)[1]);
                    if (!ls){
                        tcView.startScroll = !(DialogManager.this.otherDirection?tcViewOtherDirection.startScroll:tcViewLandSpace.startScroll);
                        tcView.changeTvStatus();
//                        tcViewLandSpace.tvContent.startScroll = PreferencesUtils.getBool("startScroll",false);
                    }else {
                        if (DialogManager.this.otherDirection){
                            tcViewOtherDirection.startScroll = !tcView.startScroll;
                            tcViewOtherDirection.changeTvStatus();
                        }else {
                            tcViewLandSpace.startScroll = !tcView.startScroll;
                            tcViewLandSpace.changeTvStatus();
                        }
//                        tcView.tvContent.startScroll = PreferencesUtils.getBool("startScroll",false);

                    }
//                    popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.TOP,
//                            VarManger.getDefXY(context)[0],VarManger.getDefXY(context)[1]);
                }

                @Override
                public void closePopupWindow() {
                    popupWindow.dismiss();
                }

                @Override
                public void changeScorllStatus() {
//                    if (landSpace){
//                        tcView.changeTvStatus();
//                    }else {
//                        tcViewLandSpace.changeTvStatus();
//                    }
                }

                @Override
                public void setY(int y) {
                    if (landSpace){
                        tcView.tvContent.y = y;
                    }else {
                        tcViewLandSpace.tvContent.y = y;
                        tcViewOtherDirection.tvContent.y = y;
                    }
                }
            };
            sb.append("viewEvent init\n");
            popWidth = VarManger.getDefWidthHeight(context,dialogType)[0];
            popHeight = VarManger.getDefWidthHeight(context,dialogType)[1];
            popupWindow = new PopupWindow(tcView, popWidth,popHeight);
            tcView.setViewEvent(viewEvent);
            tcView.tvContent.setViewEvent(viewEvent);
            tcViewLandSpace.setViewEvent(viewEvent);
            tcViewLandSpace.tvContent.setViewEvent(viewEvent);
            tcViewOtherDirection.setViewEvent(viewEvent);
            tcViewOtherDirection.tvContent.setViewEvent(viewEvent);
            popupWindow.setOutsideTouchable(false);
            popupWindow.setFocusable(false);
        }
        setPopupWindowShowLocation(context.getWindow().getDecorView(),VarManger.getDefXY(context,dialogType)[0],VarManger.getDefXY(context,dialogType)[1]);
        //        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.TOP,VarManger.getDefXY(context)[0],VarManger.getDefXY(context)[1]);
            tcView.post(new Runnable() {
                @Override
                public void run() {
                    int height = tcView.tvContent.getHeight();
                    int h = (int)Math.floor(((double)height)/12);
                    tcView.baseline.layout(tcView.baseline.getLeft(),tcView.baseline.getTop()-h,tcView.baseline.getRight(),tcView.baseline.getBottom()-h);
                }
            });
            tcViewLandSpace.post(new Runnable() {
                @Override
                public void run() {
                    int height = tcViewLandSpace.tvContent.getHeight();
                    int h = (int)Math.floor(((double)height)/12);
                    tcViewLandSpace.baseline.layout(tcViewLandSpace.baseline.getLeft(),tcViewLandSpace.baseline.getTop()-h,tcViewLandSpace.baseline.getRight(),tcViewLandSpace.baseline.getBottom()-h);
                }
            });
            tcViewOtherDirection.post(new Runnable() {
                @Override
                public void run() {
                    int height = tcViewOtherDirection.tvContent.getHeight();
                    int h = (int)Math.floor(((double)height)/12);
                    tcViewOtherDirection.baseline.layout(tcViewOtherDirection.baseline.getLeft(),tcViewOtherDirection.baseline.getTop()-h,tcViewOtherDirection.baseline.getRight(),tcViewOtherDirection.baseline.getBottom()-h);
                }
            });
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
        return sb.toString();
    }

    public void setMarginTop(int y){
        if (popupWindow==null)return;
        marginTop = y;
//        PreferencesUtils.setInt("dialogY", marginTop);
        if (popupWindow.isShowing()){
            popupWindow.update(marginLeft,marginTop,popWidth,popHeight);
        }
//        popupWindow.showAtLocation(activity.getWindow().getDecorView(),Gravity.TOP,marginLeft,y);
    }

    public void setPaddingTopOrPaddingBottom(Context context,int paddingTop,int paddingBottom){
        tcView.topDecorView.setPadding(VarManger.dip2px(context,6f),paddingTop,VarManger.dip2px(context,6f),paddingBottom);
        tcViewLandSpace.topDecorView.setPadding(VarManger.dip2px(context,6f),paddingTop,VarManger.dip2px(context,6f),paddingBottom);
        tcViewOtherDirection.topDecorView.setPadding(VarManger.dip2px(context,6f),paddingTop,VarManger.dip2px(context,6f),paddingBottom);

    }

    public void setWidhtOrHeight(int w,int h){
        if (popupWindow==null)return;
        if (w!=0){
            popWidth = w;
        }
        if (h!=0){
            popHeight = h;
        }
        if (popupWindow.isShowing()){
            popupWindow.update(marginLeft,marginTop,popWidth,popHeight);
            if (landSpace){
                PreferencesUtils.setInt(dialogType==0?"dialogLsW":"dialogLsWN", popWidth);
                PreferencesUtils.setInt(dialogType==0?"dialogLsH":"dialogLsHN",popWidth);
            }else {
                PreferencesUtils.setInt(dialogType==0?"dialogW":"dialogWN", popWidth);
                PreferencesUtils.setInt(dialogType==0?"dialogH":"dialogHN",popHeight);
            }
        }
    }


    private void setPopupWindowShowLocation(View view, int x, int y){
        marginLeft = x;
        marginTop = y;
        popupWindow.showAtLocation(view, Gravity.TOP,x,y);
    }


    public void setTvContent(String text){
        tcViewLandSpace.setText(text);
        tcViewOtherDirection.setText(text);
        tcView.setText(text);
    }

    public void closeTcDialog(){
        if(popupWindow!=null && popupWindow.isShowing()){
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    public void hideTcDialog(){
        if(popupWindow!=null && popupWindow.isShowing()){
            popupWindow.dismiss();
        }
    }

    public void setTvSize(int size){
        tcViewLandSpace.tvContent.setTextSize(size);
        tcViewOtherDirection.tvContent.setTextSize(size);
        tcView.tvContent.setTextSize(size);
    }

    public void resetToTop(){
//        tcViewLandSpace.;
        tcView.resetToTop();
        tcViewLandSpace.resetToTop();
        tcViewOtherDirection.resetToTop();
    }


    public void setBaselineColor(String colorStr){
        tcView.setBaselineColor(colorStr);
        tcViewLandSpace.setBaselineColor(colorStr);
        tcViewOtherDirection.setBaselineColor(colorStr);
    }

    public void setBaselineVisible(boolean visible){
        tcView.setBaselineVisible(visible);
        tcViewLandSpace.setBaselineVisible(visible);
        tcViewOtherDirection.setBaselineVisible(visible);
    }

    public void setCountTimeTextColor(String colorStr){
        tcView.setCountTimeTextColor(colorStr);
        tcViewLandSpace.setCountTimeTextColor(colorStr);
        tcViewOtherDirection.setCountTimeTextColor(colorStr);
    }

    public void setLandSpace(boolean landSpace,boolean otherDirection){
        if (viewEvent!=null){
            if (this.landSpace == landSpace && this.otherDirection == otherDirection) return;
            viewEvent.changeScreenOri(landSpace,otherDirection);
        }
    }

    public void setTvColor(String colorStr){
        tcViewLandSpace.tvContent.setTextColor(Color.parseColor(colorStr));
        tcViewOtherDirection.tvContent.setTextColor(Color.parseColor(colorStr));
        tcView.tvContent.setTextColor(Color.parseColor(colorStr));
    }

    public void setBgColor(String colorStr){
        tcViewLandSpace.setBgColor(Color.parseColor(colorStr));
        tcViewOtherDirection.setBgColor(Color.parseColor(colorStr));
        tcView.setBgColor(Color.parseColor(colorStr));
    }

    public void setScrollSpeed(int speed) {
        tcViewLandSpace.tvContent.speed = speed;
        tcViewOtherDirection.tvContent.speed = speed;
        tcView.tvContent.speed = speed;
    }

    public void setViewAlpha(int alpahVal) {
        tcViewLandSpace.setBgAlpha((int)( (alpahVal / 100f) * 255));
        tcViewOtherDirection.setBgAlpha((int)( (alpahVal / 100f) * 255));
        tcView.setBgAlpha((int)( (alpahVal / 100f) * 255));
    }


    public void setTextViewMirror(int direction) {
        tcViewLandSpace.tvContent.setScaleX(direction);
        tcViewOtherDirection.tvContent.setScaleX(direction);
        tcView.tvContent.setScaleX(direction);
    }

    public boolean getScrollStatus(){
        if (landSpace){
            return tcViewLandSpace.startScroll;
        }else {
            return tcView.startScroll;
        }
    }

    /*
     *
     * 启动文字滚动
     *
     * */
    public void startScroll(Context context) {
        if (popupWindow!=null && popupWindow.isShowing()){
            if (landSpace){
                if (otherDirection){
                    if (!tcViewOtherDirection.tvContent.startScroll){
                        tcViewOtherDirection.changeTvStatus();
                    }
                }else {
                    if (!tcViewLandSpace.tvContent.startScroll){
                        tcViewLandSpace.changeTvStatus();
                    }
                }

            }else {
                if (!tcView.tvContent.startScroll){
                   tcView.changeTvStatus();
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
                if (otherDirection){
                    if (tcViewOtherDirection.tvContent.startScroll){
                        tcViewOtherDirection.changeTvStatus();
                    }
                }else {
                    if (tcViewLandSpace.tvContent.startScroll){
                        tcViewLandSpace.changeTvStatus();
                    }
                }
            }else {
                if (tcView.tvContent.startScroll){
                    tcView.changeTvStatus();
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
                if (otherDirection){
                    tcViewOtherDirection.scrollToY(false);
                }else {
                    tcViewLandSpace.scrollToY(false);
                }
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
                if (otherDirection){
                    tcViewOtherDirection.scrollToY(true);
                }else {
                    tcViewLandSpace.scrollToY(true);
                }
            }else {
                tcView.scrollToY(true);
            }
        }else {
            Toast.makeText(context,"文本框未弹出，请先打开",Toast.LENGTH_LONG).show();
        }
    }


}
