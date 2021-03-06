package com.jw.xfkplugin;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class AutoScrollTextView extends TextView {

    @Override
    protected void onDraw(Canvas canvas) {
        scrollIng();
        super.onDraw(canvas);
    }

    public int x = 0;
    public int y = 0;
    public boolean startScroll = false;
    public int speed = 50;
    public int i = 0;

    public AutoScrollTextView(Context context) {
        this(context, null);
    }

    public AutoScrollTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
            scrollTo(0, y);
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
                if (y > getTextHeight() - getLineHeight()*3) {
                    y = getTextHeight() - getLineHeight()*3;
                }
                scrollTo(0, y);
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
                if (y < 0) {
                    y = 0;
                }
                if (y > getTextHeight() - getLineHeight()) {
                    y = getTextHeight() - getLineHeight();
                }
                scrollTo(0, y);
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

    public int getTextHeight() {
        return getLineHeight() * getLineCount();
    }

}
