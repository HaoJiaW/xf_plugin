package com.jw.xfkplugin;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class TestRotationActivity extends AppCompatActivity {


    private float biliW=1f;
    private float biliH=1f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_rotation);
        LinearLayout rootView = findViewById(R.id.rootView);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        ObjectAnimator animator = ObjectAnimator.ofFloat(rootView,"rotation",0f,90f);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(rootView,"alpha",0.9f,1f);
        animator.setDuration(5000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rootView.getLayoutParams();
                int width =  rootView.getRight()-rootView.getLeft();
//                biliW = (float) lp.width/(float)displayMetrics.widthPixels;
                System.out.println("lp.width:"+width+",displayMetrics.widthPixels:"+displayMetrics.widthPixels);
//                biliH = (float) lp.height/(float)displayMetrics.heightPixels;
                System.out.println("lp.height:"+lp.height+",displayMetrics.heightPixels:"+displayMetrics.heightPixels);
                System.out.println("biliW:"+biliW+",biliH:"+biliH);
//                int width = lp.width;
                lp.width = VarManger.dip2px(TestRotationActivity.this,100f);
                lp.height = VarManger.dip2px(TestRotationActivity.this,100f);
                rootView.setLayoutParams(lp);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) rootView.getLayoutParams();
//                int width = lp.width;
                lp.width = (int)(displayMetrics.heightPixels*biliW);
                lp.height = (int)(displayMetrics.widthPixels*biliH);
//                rootView.setLayoutParams(lp);
                rootView.layout(0,0,lp.height,lp.width);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }
}
