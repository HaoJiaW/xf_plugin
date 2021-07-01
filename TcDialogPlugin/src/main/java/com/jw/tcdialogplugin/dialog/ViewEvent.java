package com.jw.tcdialogplugin.dialog;

public interface ViewEvent {
    void changeWH(int w,int h,int x,int y,boolean ç);

    void changeScreenOri(boolean landSpace,boolean otherDirection);

    void closePopupWindow();

    void changeScorllStatus();

    void setY(int y);
}
