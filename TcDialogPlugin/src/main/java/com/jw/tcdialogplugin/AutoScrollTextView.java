package com.jw.tcdialogplugin;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.jw.tcdialogplugin.dialog.ViewEvent;

import java.util.Random;

public class AutoScrollTextView extends TextView {

    @Override
    protected void onDraw(Canvas canvas) {
        scrollIng();
        super.onDraw(canvas);
    }

    private ViewEvent viewEvent;

    public void setViewEvent(ViewEvent viewEvent){
        this.viewEvent = viewEvent;
    }

    public int x = 0;
    public int y = 0;
    public boolean startScroll = false;
    public int speed = 50;
    public int i = 0;
    private TextviewEvent textviewEvent;
    private int yLimit;
    private int lineHeight;
    private int lineCount;

    public AutoScrollTextView(Context context) {
        this(context, null);
    }

    public AutoScrollTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void customScrollTo(int x,int y){
        scrollTo(x, y);
        if (viewEvent!=null){
            viewEvent.setY(y);
        }
    }

    public void scrollToY(int xfkHei){
        if (y <= getTextHeight()) {
            y += xfkHei;
            if (y<0){
                y=0;
            }
            if (y > getTextHeight() - getLineHeight()*3) {
                y = getTextHeight() - getLineHeight()*3;
            }
            customScrollTo(0, y);
        }
    }

    public void scrollIng() {
        if (startScroll && !touchEvent) {
            if (y <= getTextHeight()) {
                if (speed < 50) {
                    i += 3;
                    if (i + speed * 3 >= 100) {
                        y += 1;
                        i = 0;
                    }
                } else {
                    y += speed / 10 - 4;
                }
                if (y > getLineHeight()*(getLineCount()-3)) {
                    y = getLineHeight()*(getLineCount()-3);
                    if (textviewEvent!=null){
                        textviewEvent.scrollToEnd();
                    }
                }
                customScrollTo(0, y);
            } else {
                y = getScrollY();
            }
        }
        postInvalidate();
    }

    public int lastX = 0;
    public int lastY = 0;
    public int dx = 0;
    public int dy = 0;
    public boolean touchEvent = false;


    public void setTextviewEvent(TextviewEvent textviewEvent){
        this.textviewEvent = textviewEvent;
    }


    @Override
    public boolean onTouchEvent(MotionEvent it) {
        switch (it.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                lastX = event?.x.toInt()
                lastY = (int) it.getY();
                touchEvent = true;
                break;
            case MotionEvent.ACTION_MOVE:
//                dx = event?.x.toInt() - lastX
                dy = (int) it.getY() - lastY;
                y = getScrollY() - dy;
                if (y < yLimit) {
                    y = yLimit;
                }
//                y += 20+(int)(Math.random()*20);
                if (y > getTextHeight() - getLineHeight()) {
                    y = getTextHeight() - getLineHeight();
                }
                customScrollTo(0, y);
                postInvalidate();
                lastY = (int) it.getY();
                break;
            case MotionEvent.ACTION_UP:
                y = getScrollY() - dy;
                touchEvent = false;
                break;
        }
        return true;
    }

    public void reset(){
//        scrollToY(0)；
        y=0;
        if (yLimit!=0){
            y = yLimit;
        }
        customScrollTo(0,y);
    }

    public void setYLimit(int yLimit){
        if (y==this.yLimit){
            y = yLimit;
        }
        this.yLimit = yLimit;
        customScrollTo(0,y);
    }

    public void setCustomText(String text,int height){
        setText(text);
        y = height;
        yLimit=height;
        customScrollTo(0,y);
    }


//    @Override
//    public int getLineCount() {
//        if (lineCount==0){
//            return super.getLineCount();
//        }
//        return lineCount;
//    }
//
//    @Override
//    public int getLineHeight() {
//        if (lineHeight==0)
//            return super.getLineHeight();
//        return lineHeight;
//    }
//
    public int getTextHeight() {
        return getLineHeight() * getLineCount();
    }
//
//    public void setLineCount(int lineCount) {
//        this.lineCount = lineCount;
//    }
//
//    @Override
//    public void setLineHeight(int lineHeight) {
//        this.lineHeight = lineHeight;
//    }
}
