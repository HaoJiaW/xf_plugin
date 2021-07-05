package com.jw.xfkplugin.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jw.xfkplugin.BeyondView;
import com.jw.xfkplugin.R;
import com.jw.xfkplugin.VarManger;
import com.jw.xfkplugin.adapter.AppIconAdapter;

public abstract class BaseView extends LinearLayout {

    private RecyclerView rvAppIcon;
    private AppIconAdapter appIconAdapter;
    protected View mView;
    protected Context context;
    protected ImageView bottomSetImageView;
    protected boolean isWindowMangerFlag = false;
    protected boolean iconShow = true;
    protected WindowManager.LayoutParams lpW;
    protected WindowManager wm;


    public BaseView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public abstract int getLayoutId();

    public void init(){
        mView = LayoutInflater.from(context).inflate(getLayoutId(), this);
        rvAppIcon = mView.findViewById(R.id.rv_app_icon);
        appIconAdapter = new AppIconAdapter(context);
        rvAppIcon.setLayoutManager(new LinearLayoutManager(this.context,RecyclerView.HORIZONTAL,false));
        rvAppIcon.setAdapter(appIconAdapter);
        appIconAdapter.setData(VarManger.appBeanList);

        bottomSetImageView = mView.findViewById(R.id.img_2);
        bottomSetImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isWindowMangerFlag) {
                    if (iconShow) {
                        rvAppIcon.setVisibility(View.GONE);
                        if (lpW != null) {
                            lpW.height += -VarManger.dip2px(context, 50f);
                            wm.updateViewLayout(BaseView.this, lpW);
                        }
                        iconShow = false;
                    } else {
                        rvAppIcon.setVisibility(View.VISIBLE);
                        if (lpW != null) {
                            lpW.height += VarManger.dip2px(context, 50f);
                            wm.updateViewLayout(BaseView.this, lpW);
                        }
                        iconShow = true;
                    }
                }
            }
        });
    }

    public void setWindowMangerFlag(boolean isWindowMangerFlag){
        this.isWindowMangerFlag = isWindowMangerFlag;
    }

    public void setWm(WindowManager wm){
        this.wm = wm;
    }


}
