package com.jw.tcdialogplugin.dialog;

public interface ViewEvent {
    void changeWH(int w,int h,int x,int y);

    void changeScreenOri(boolean landSpace);

    void closePopupWindow();
}
