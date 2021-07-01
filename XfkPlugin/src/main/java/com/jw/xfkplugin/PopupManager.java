package com.jw.xfkplugin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PopupManager {

    private static PopupManager instance;

    public synchronized static PopupManager getInstance() {
        if (instance == null) {
            instance = new PopupManager();
        }
        return instance;
    }

    private PopupWindow popupWindow;
    private PopupWindow bottomPopupWindow;
    private View popupView;
    public View bottomPopupView;
    public View bottomPopupViewLandSpace;
    public BeyondView beyondView;
    public BeyondViewLandSpace beyondViewLandSpace;
    //        public BeyondViewLandSpace beyondViewLandSpace:BeyondViewLandSpace? = null
    public LinearLayout.LayoutParams lp;
    public RelativeLayout rootView;

    public void removeBeyondView() {
        if (rootView != null) {
            rootView.removeView(beyondView);
        }
    }

//        public void  showBeyondView(context: Context,rootView: RelativeLayout){
//        if (beyondView==null){
//        beyondView = BeyondView(context)
//        val displayMetrics = context.resources.displayMetrics
//        lp = LinearLayout.LayoutParams((displayMetrics.widthPixels * 1).toInt(),
//        (displayMetrics.heightPixels * 0.4).toInt())
//        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
//        if (resourceId > 0) {
//        lp?.let {
//        it.topMargin =  context.resources.getDimensionPixelSize(resourceId)
//        it.leftMargin =  context.resources.getDimensionPixelSize(resourceId)
//        it.rightMargin =  context.resources.getDimensionPixelSize(resourceId)
//        it.bottomMargin =  context.resources.getDimensionPixelSize(resourceId)
//        }
//        }
//        beyondView?.onBottomImageViewClick = object :BeyondView.OnBottomImageViewClick{
//        override fun onClick() {
//        showBottomPop(context, rootView)
//        }
//        }
//        rootView.addView(beyondView,lp)
//        this.rootView = rootView
//        }
//        }

    public void addView() {
        rootView.addView(beyondView);
    }

//    public void showTextPop(context: Context, decorView: View) {
//        if (popupWindow == null) {
//        popupView = View.inflate(context, R.layout.pop_app_text, null)
//        val displayMetrics = context.resources.displayMetrics
//        popupWindow = PopupWindow(
//        popupView,
//        (displayMetrics.widthPixels * 0.9).toInt(),
//        (displayMetrics.heightPixels * 0.3).toInt()
//        )
//        }
////        popupWindow?.setBackgroundDrawable()
////        val image2 = popupView?.findViewById(R.id.img_2) as ImageView
////        image2.setOnClickListener {
////            showBottomPop(context, decorView)
////        }
//
//        popupView?.let {
//        initbgAlpha(it)
//        val image2 = it.findViewById(R.id.img_2) as ImageView
//        image2.setOnClickListener {
//        showBottomPop(context, decorView)
//        }
//        }
//
//        popupWindow?.let {
//        it.isFocusable = false
//        val colorDrawable = ColorDrawable()
//        it.setBackgroundDrawable(colorDrawable)
//        it.isOutsideTouchable = false
//        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
//        if (resourceId > 0) {
//        it.showAtLocation(decorView, Gravity.TOP, 0, context.resources.getDimensionPixelSize(resourceId))
//        } else {
//        it.showAtLocation(decorView, Gravity.TOP, 0,0)
//        }
//        }
//        }

    public void initBottomSetting(Context context) {
        bottomPopupView = View.inflate(context, R.layout.pop_app_bottom, null);
        initBottomView(context);
    }

    public void initBottomLandspaceSetting(Context context) {
        bottomPopupViewLandSpace = View.inflate(context, R.layout.pop_app_bottom_landspace, null);
        initBottomViewLandSpace(context);
    }

//        fun showBottomPop(context: Context, decorView: View) {
//        if (bottomPopupWindow == null) {
//        bottomPopupView = View.inflate(context, R.layout.pop_app_bottom, null);
//        val displayMetrics = context.resources.displayMetrics
//        bottomPopupWindow = PopupWindow(
//        bottomPopupView,
//        WindowManager.LayoutParams.MATCH_PARENT,
//        (displayMetrics.heightPixels * 0.48).toInt()
//        )
//        }
//        initBottomView(context)
//        bottomPopupWindow?.let {
//        it.isFocusable = true
//        it.isOutsideTouchable = true
//        it.animationStyle = R.style.pop_animation
//        it.showAtLocation(decorView, Gravity.BOTTOM, 0, 0)
//        }
//        }

    public void initBottomView(Context context) {
        ImageView closeBtn = bottomPopupView.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.stopService(new Intent(context, SettingService.class));
            }
        });
        CommViewPager viewPager = bottomPopupView.findViewById(R.id.viewPager);
        viewPager.setCanScroll(false);
        LayoutInflater inflater = LayoutInflater.from(context);

        View setXfView = inflater.inflate(R.layout.viewpager_tc_set, null);
        initXfView(setXfView, context);

//        View setTextView = inflater.inflate(R.layout.viewpager_textset, null);
//        initSetTextView(setTextView, context);

//        View setBgView = inflater.inflate(R.layout.viewpager_bgset, null);
//        initSetBgView(setBgView, context);
//
//        View setSpeedView = inflater.inflate(R.layout.viewpager_speedset, null);
//        initSetSpeedView(setSpeedView, context);

        View setContentView = inflater.inflate(R.layout.viewpager_contentset, null);
        initSetContentView(setContentView, context);
//        val setView =  inflater.inflate(R.layout.viewpager_set, null)
//        initSetView(context,setView)
//        val textView =  inflater.inflate(R.layout.viewpager_text, null)

        List<View> list = Arrays.asList(setXfView, setContentView);
//        List<View> list = Arrays.asList(setTextView, setBgView, setSpeedView, setContentView);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return o == view;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = list.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(list.get(position));
            }
        });

        CommonTabLayout tabLayout = bottomPopupView.findViewById(R.id.tabLayout);
        ArrayList<CustomTabEntity> entities = new ArrayList<>();
        List<CustomTabEntity> entities1 = Arrays.asList(
                new TabEntity(
                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
                        context.getResources().getString(R.string.xf_set)
                ), new TabEntity(
                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
                        context.getResources().getString(R.string.content_set)
                ));
//        List<CustomTabEntity> entities1 = Arrays.asList(
//                new TabEntity(
//                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
//                        context.getResources().getString(R.string.text_set)
//                ), new TabEntity(
//                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
//                        context.getResources().getString(R.string.bg_set)
//                ), new TabEntity(
//                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
//                        context.getResources().getString(R.string.speed_set)
//                ), new TabEntity(
//                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
//                        context.getResources().getString(R.string.content_set)
//                ));
        entities.addAll(entities1);
        tabLayout.setTabData(entities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        if (VarManger.showTcSet) {
            viewPager.setCurrentItem(3);
        } else {
            viewPager.setCurrentItem(0);
        }
    }


    public void initBottomViewLandSpace(Context context) {
        ImageView closeBtn = bottomPopupViewLandSpace.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.stopService(new Intent(context, SettingServiceLandSpace.class));
            }
        });
        CommViewPager viewPager = bottomPopupViewLandSpace.findViewById(R.id.viewPager);
        viewPager.setCanScroll(false);
        LayoutInflater inflater = LayoutInflater.from(context);

        View setXfView = inflater.inflate(R.layout.viewpager_tc_set, null);
        initSetXfLandSpace(setXfView, context);

//        View setTextView = inflater.inflate(R.layout.viewpager_textset, null);
//        initSetTextViewLandSpace(setTextView, context);

//        View setBgView = inflater.inflate(R.layout.viewpager_bgset, null);
//        initSetBgViewLandSpace(setBgView, context);
//
//        View setSpeedView = inflater.inflate(R.layout.viewpager_speedset, null);
//        initSetSpeedViewLandSpace(setSpeedView, context);

        View setContentView = inflater.inflate(R.layout.viewpager_contentset, null);
        initSetContentViewLandSpace(setContentView, context);
//        val setView =  inflater.inflate(R.layout.viewpager_set, null)
//        initSetView(context,setView)
//        val textView =  inflater.inflate(R.layout.viewpager_text, null)

//        List<View> list = Arrays.asList(setTextView, setBgView, setSpeedView, setContentView);
        List<View> list = Arrays.asList(setXfView, setContentView);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return o == view;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = list.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(list.get(position));
            }
        });

        CommonTabLayout tabLayout = bottomPopupViewLandSpace.findViewById(R.id.tabLayout);
        ArrayList<CustomTabEntity> entities = new ArrayList<>();
        List<CustomTabEntity> entities1 = Arrays.asList(
                new TabEntity(
                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
                        context.getResources().getString(R.string.xf_set)
                ), new TabEntity(
                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
                        context.getResources().getString(R.string.content_set)
                ));
//        List<CustomTabEntity> entities1 = Arrays.asList(
//                new TabEntity(
//                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
//                        context.getResources().getString(R.string.text_set)
//                ), new TabEntity(
//                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
//                        context.getResources().getString(R.string.bg_set)
//                ), new TabEntity(
//                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
//                        context.getResources().getString(R.string.speed_set)
//                ), new TabEntity(
//                        R.drawable.arrow_circle_up_24, R.drawable.arrow_circle_up_24,
//                        context.getResources().getString(R.string.content_set)
//                ));
        entities.addAll(entities1);
        tabLayout.setTabData(entities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        if (VarManger.showTcSet) {
            viewPager.setCurrentItem(3);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    float tcContentSize = 25f;
    int alpahVal = 70;
    String selectRes;
    int bgColor = R.color.black;
    int textColor = R.color.white;
    boolean floatTc = false;
    int speed = 30;

    public void initXfView(View v, Context context) {
        if (beyondView != null) {
            tcContentSize = PreferencesUtils.getFloat("tcContentSize",tcContentSize);
            TextView tcContentText = beyondView.findViewById(R.id.tcContentText);
            SeekBar textSizeSeekBar = v.findViewById(R.id.textSizeSeekBar);
            TextView textSizeText = v.findViewById(R.id.textSizeText);
            textSizeSeekBar.setProgress((int) tcContentSize - 10);
            tcContentText.setTextSize(tcContentSize);
            textSizeText.setText("" + ((int) (tcContentSize)));
            textSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    textSizeText.setText("" + (i + 10));
                    tcContentSize = 10f + i;
                    tcContentText.setTextSize(tcContentSize);
                    if (beyondViewLandSpace!=null){
                        TextView tv = beyondViewLandSpace.findViewById(R.id.tcContentText);
                        tv.setTextSize(tcContentSize);
                    }
                    PreferencesUtils.setFloat("tcContentSize",tcContentSize);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            alpahVal = PreferencesUtils.getInt("alpahVal",alpahVal);
//            TextView tcContentText = beyondView.findViewById(R.id.tcContentText);
            SeekBar bgAlphaSeekbar = v.findViewById(R.id.bgAlphaSeekbar);
            TextView bgAlphaText = v.findViewById(R.id.bgAlphaText);
            bgAlphaText.setText("" + alpahVal + "");
            bgAlphaSeekbar.setProgress(alpahVal);
            beyondView.setBgAlpha((int)( (alpahVal / 100f) * 255));
            bgAlphaSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    alpahVal = i;
                    bgAlphaText.setText("" + alpahVal + "");
                    System.out.println("now alpha:" + alpahVal);
//                    topDecorShapeDrawable?.alpha = ((alpahVal/100f)*255).toInt()
                    if (beyondView!=null){
                        beyondView.setBgAlpha((int)( (alpahVal / 100f) * 255));
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setBgAlpha((int)( (alpahVal / 100f) * 255));
                    }
                    PreferencesUtils.setInt("alpahVal",alpahVal);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


            SeekBar scrollSpeedSeekbar = v.findViewById(R.id.scrollSpeedSeekbar);
            TextView scrollText = v.findViewById(R.id.scrollText);
            speed = PreferencesUtils.getInt("speed",speed);
            scrollText.setText("" + speed);
            scrollSpeedSeekbar.setProgress(speed);
            beyondView.setScrollSpeed(speed);
            scrollSpeedSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    speed = i;
                    scrollText.setText("" + (speed));
                    scrollSpeedSeekbar.setProgress(speed);
                    if (beyondView!=null){
                        beyondView.setScrollSpeed(speed);
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setScrollSpeed(speed);
                    }
                    PreferencesUtils.setInt("speed",speed);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            List<Integer> dataList = new ArrayList<>();
            dataList.addAll(Arrays.asList(new Integer[]{R.color.c_1, R.color.c_2, R.color.c_3, R.color.c_4, R.color.c_5, R.color.c_6,
                    R.color.c_7, R.color.c_8, R.color.c_9, R.color.c_10, R.color.c_11, R.color.c_12,
                    R.color.c_13, R.color.c_14, R.color.c_15, R.color.c_16, R.color.c_17, R.color.c_18, R.color.c_19,
                    R.color.c_20, R.color.c_21, R.color.c_22, R.color.c_23, R.color.c_24}));
            ColorPickerAdapter colorAdapter1 = new ColorPickerAdapter(dataList, context);
            textColor = PreferencesUtils.getInt("textColor",textColor);
            colorAdapter1.mItemClick = new ColorPickerAdapter.OnRecyclerViewItemClick<Integer>() {
                @Override
                public void OnItemClick(View view, Integer integer, int i) {
                    textColor = integer == null ? R.color.white : integer;
                    tcContentText.setTextColor(context.getResources().getColorStateList(textColor));
                    if (beyondViewLandSpace!=null){
                        TextView tv = beyondViewLandSpace.findViewById(R.id.tcContentText);
                        tv.setTextColor(context.getResources().getColorStateList(textColor));
                    }
                    PreferencesUtils.setInt("textColor",textColor);
                }
            };
            RecyclerView colorRecyView = v.findViewById(R.id.textColorRecyView);
            colorRecyView.setAdapter(colorAdapter1);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            colorRecyView.setLayoutManager(layoutManager);

            ColorPickerAdapter colorAdapter2 = new ColorPickerAdapter(dataList, context);
            bgColor = PreferencesUtils.getInt("bgColor",bgColor);
            colorAdapter2.mItemClick = new ColorPickerAdapter.OnRecyclerViewItemClick<Integer>() {
                @Override
                public void OnItemClick(View view, Integer integer, int i) {
                    bgColor = integer == null ? R.color.black : integer;
                    if (beyondView!=null){
                        beyondView.setBgColor(context.getResources().getColor(bgColor));
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setBgColor(context.getResources().getColor(bgColor));
                    }
                    PreferencesUtils.setInt("bgColor",bgColor);
                }
            };
            RecyclerView bgColorRecyView = v.findViewById(R.id.bgColorRecyView);
            bgColorRecyView.setAdapter(colorAdapter2);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(context);
            layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
            bgColorRecyView.setLayoutManager(layoutManager2);
        }
    }


//    public void initSetTextView(View v, Context context) {
//        if (beyondView != null) {
//            tcContentSize = PreferencesUtils.getFloat("tcContentSize",tcContentSize);
//            TextView tcContentText = beyondView.findViewById(R.id.tcContentText);
//            SeekBar textSizeSeekBar = v.findViewById(R.id.textSizeSeekBar);
//            TextView textSizeText = v.findViewById(R.id.textSizeText);
//            textSizeSeekBar.setProgress((int) tcContentSize - 10);
//            tcContentText.setTextSize(tcContentSize);
//            textSizeText.setText("" + ((int) (tcContentSize)));
//            textSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                    textSizeText.setText("" + (i + 10));
//                    tcContentSize = 10f + i;
//                    tcContentText.setTextSize(tcContentSize);
//                    if (beyondViewLandSpace!=null){
//                        TextView tv = beyondViewLandSpace.findViewById(R.id.tcContentText);
//                        tv.setTextSize(tcContentSize);
//                    }
//                    PreferencesUtils.setFloat("tcContentSize",tcContentSize);
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
//
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//
//                }
//            });
//            List<Integer> dataList = new ArrayList<>();
//            dataList.addAll(Arrays.asList(new Integer[]{R.color.c_1, R.color.c_2, R.color.c_3, R.color.c_4, R.color.c_5, R.color.c_6,
//                    R.color.c_7, R.color.c_8, R.color.c_9, R.color.c_10, R.color.c_11, R.color.c_12,
//                    R.color.c_13, R.color.c_14, R.color.c_15, R.color.c_16, R.color.c_17, R.color.c_18, R.color.c_19,
//                    R.color.c_20, R.color.c_21, R.color.c_22, R.color.c_23, R.color.c_24}));
//            ColorPickerAdapter colorAdapter = new ColorPickerAdapter(dataList, context);
//            textColor = PreferencesUtils.getInt("textColor",textColor);
//            colorAdapter.mItemClick = new ColorPickerAdapter.OnRecyclerViewItemClick<Integer>() {
//                @Override
//                public void OnItemClick(View view, Integer integer, int i) {
//                    textColor = integer == null ? R.color.white : integer;
//                    tcContentText.setTextColor(context.getResources().getColorStateList(textColor));
//                    if (beyondViewLandSpace!=null){
//                        TextView tv = beyondViewLandSpace.findViewById(R.id.tcContentText);
//                        tv.setTextColor(context.getResources().getColorStateList(textColor));
//                    }
//                    PreferencesUtils.setInt("textColor",textColor);
//                }
//            };
//            RecyclerView colorRecyView = v.findViewById(R.id.colorRecyView);
//            colorRecyView.setAdapter(colorAdapter);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            colorRecyView.setLayoutManager(layoutManager);
//        }
//    }

    public void initSetXfLandSpace(View v, Context context) {
        if (beyondViewLandSpace != null) {
            tcContentSize = PreferencesUtils.getFloat("tcContentSize",tcContentSize);
            TextView tcContentText = beyondViewLandSpace.findViewById(R.id.tcContentText);
            SeekBar textSizeSeekBar = v.findViewById(R.id.textSizeSeekBar);
            TextView textSizeText = v.findViewById(R.id.textSizeText);
            textSizeSeekBar.setProgress((int) tcContentSize - 10);
            tcContentText.setTextSize(tcContentSize);
            textSizeText.setText("" + ((int) (tcContentSize)));
            textSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    textSizeText.setText("" + (i + 10));
                    tcContentSize = 10f + i;
                    tcContentText.setTextSize(tcContentSize);
                    if (beyondView!=null){
                        TextView tv = beyondView.findViewById(R.id.tcContentText);
                        tv.setTextSize(tcContentSize);
                    }
                    PreferencesUtils.setFloat("tcContentSize",tcContentSize);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


            alpahVal = PreferencesUtils.getInt("alpahVal",alpahVal);
//            TextView tcContentText = beyondView.findViewById(R.id.tcContentText);
            SeekBar bgAlphaSeekbar = v.findViewById(R.id.bgAlphaSeekbar);
            TextView bgAlphaText = v.findViewById(R.id.bgAlphaText);
            bgAlphaText.setText("" + alpahVal + "");
            bgAlphaSeekbar.setProgress(alpahVal);
            beyondViewLandSpace.setBgAlpha((int)( (alpahVal / 100f) * 255));
            bgAlphaSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    alpahVal = i;
                    bgAlphaText.setText("" + alpahVal + "");
                    System.out.println("now alpha:" + alpahVal);
//                    topDecorShapeDrawable?.alpha = ((alpahVal/100f)*255).toInt()
                    if (beyondView!=null){
                        beyondView.setBgAlpha((int)( (alpahVal / 100f) * 255));
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setBgAlpha((int)( (alpahVal / 100f) * 255));
                    }

                    beyondViewLandSpace.setBgAlpha((int)( (alpahVal / 100f) * 255));
                    PreferencesUtils.setInt("alpahVal",alpahVal);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            SeekBar scrollSpeedSeekbar = v.findViewById(R.id.scrollSpeedSeekbar);
            TextView scrollText = v.findViewById(R.id.scrollText);
            speed = PreferencesUtils.getInt("speed",speed);
            scrollText.setText("" + speed);
            scrollSpeedSeekbar.setProgress(speed);
            beyondViewLandSpace.setScrollSpeed(speed);
            scrollSpeedSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    speed = i;
                    scrollText.setText("" + (speed));
                    scrollSpeedSeekbar.setProgress(speed);
                    if (beyondView!=null){
                        beyondView.setScrollSpeed(speed);
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setScrollSpeed(speed);
                    }
                    PreferencesUtils.setInt("speed",speed);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


            List<Integer> dataList = new ArrayList<>();
            dataList.addAll(Arrays.asList(new Integer[]{R.color.c_1, R.color.c_2, R.color.c_3, R.color.c_4, R.color.c_5, R.color.c_6,
                    R.color.c_7, R.color.c_8, R.color.c_9, R.color.c_10, R.color.c_11, R.color.c_12,
                    R.color.c_13, R.color.c_14, R.color.c_15, R.color.c_16, R.color.c_17, R.color.c_18, R.color.c_19,
                    R.color.c_20, R.color.c_21, R.color.c_22, R.color.c_23, R.color.c_24}));
            ColorPickerAdapter colorAdapter = new ColorPickerAdapter(dataList, context);
            textColor = PreferencesUtils.getInt("textColor",textColor);
            colorAdapter.mItemClick = new ColorPickerAdapter.OnRecyclerViewItemClick<Integer>() {
                @Override
                public void OnItemClick(View view, Integer integer, int i) {
                    textColor = integer == null ? R.color.white : integer;
                    tcContentText.setTextColor(context.getResources().getColorStateList(textColor));
                    if (beyondView!=null){
                        TextView tv = beyondView.findViewById(R.id.tcContentText);
                        tv.setTextColor(context.getResources().getColorStateList(textColor));
                    }
                    PreferencesUtils.setInt("textColor",textColor);
                }
            };
            RecyclerView colorRecyView = v.findViewById(R.id.textColorRecyView);
            colorRecyView.setAdapter(colorAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            colorRecyView.setLayoutManager(layoutManager);

            ColorPickerAdapter colorAdapter2 = new ColorPickerAdapter(dataList, context);
            bgColor = PreferencesUtils.getInt("bgColor",bgColor);
            colorAdapter2.mItemClick = new ColorPickerAdapter.OnRecyclerViewItemClick<Integer>() {
                @Override
                public void OnItemClick(View view, Integer integer, int i) {
                    bgColor = integer == null ? R.color.black : integer;
//                        topDecorShapeDrawable.setColor(context.resources.getColor(bgColor))
                    if (beyondView!=null){
                        beyondView.setBgColor(context.getResources().getColor(bgColor));
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setBgColor(context.getResources().getColor(bgColor));
                    }
                    PreferencesUtils.setInt("bgColor",bgColor);
                }
            };
            RecyclerView colorRecyView2 = v.findViewById(R.id.bgColorRecyView);
            colorRecyView2.setAdapter(colorAdapter2);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(context);
            layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
            colorRecyView2.setLayoutManager(layoutManager2);
        }
    }


//    public void initSetTextViewLandSpace(View v, Context context) {
//        if (beyondViewLandSpace != null) {
//            tcContentSize = PreferencesUtils.getFloat("tcContentSize",tcContentSize);
//            TextView tcContentText = beyondViewLandSpace.findViewById(R.id.tcContentText);
//            SeekBar textSizeSeekBar = v.findViewById(R.id.textSizeSeekBar);
//            TextView textSizeText = v.findViewById(R.id.textSizeText);
//            textSizeSeekBar.setProgress((int) tcContentSize - 10);
//            tcContentText.setTextSize(tcContentSize);
//            textSizeText.setText("" + ((int) (tcContentSize)));
//            textSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                @Override
//                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                    textSizeText.setText("" + (i + 10));
//                    tcContentSize = 10f + i;
//                    tcContentText.setTextSize(tcContentSize);
//                    if (beyondView!=null){
//                        TextView tv = beyondView.findViewById(R.id.tcContentText);
//                        tv.setTextSize(tcContentSize);
//                    }
//                    PreferencesUtils.setFloat("tcContentSize",tcContentSize);
//                }
//
//                @Override
//                public void onStartTrackingTouch(SeekBar seekBar) {
//
//                }
//
//                @Override
//                public void onStopTrackingTouch(SeekBar seekBar) {
//
//                }
//            });
//            List<Integer> dataList = new ArrayList<>();
//            dataList.addAll(Arrays.asList(new Integer[]{R.color.c_1, R.color.c_2, R.color.c_3, R.color.c_4, R.color.c_5, R.color.c_6,
//                    R.color.c_7, R.color.c_8, R.color.c_9, R.color.c_10, R.color.c_11, R.color.c_12,
//                    R.color.c_13, R.color.c_14, R.color.c_15, R.color.c_16, R.color.c_17, R.color.c_18, R.color.c_19,
//                    R.color.c_20, R.color.c_21, R.color.c_22, R.color.c_23, R.color.c_24}));
//            ColorPickerAdapter colorAdapter = new ColorPickerAdapter(dataList, context);
//            textColor = PreferencesUtils.getInt("textColor",textColor);
//            colorAdapter.mItemClick = new ColorPickerAdapter.OnRecyclerViewItemClick<Integer>() {
//                @Override
//                public void OnItemClick(View view, Integer integer, int i) {
//                    textColor = integer == null ? R.color.white : integer;
//                    tcContentText.setTextColor(context.getResources().getColorStateList(textColor));
//                    if (beyondView!=null){
//                        TextView tv = beyondView.findViewById(R.id.tcContentText);
//                        tv.setTextColor(context.getResources().getColorStateList(textColor));
//                    }
//                    PreferencesUtils.setInt("textColor",textColor);
//                }
//            };
//            RecyclerView colorRecyView = v.findViewById(R.id.colorRecyView);
//            colorRecyView.setAdapter(colorAdapter);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            colorRecyView.setLayoutManager(layoutManager);
//        }
//    }

    private void initSetBgView(View v, Context context) {
        if (beyondView != null) {
            alpahVal = PreferencesUtils.getInt("alpahVal",alpahVal);
//            TextView tcContentText = beyondView.findViewById(R.id.tcContentText);
            SeekBar bgAlphaSeekbar = v.findViewById(R.id.bgAlphaSeekbar);
            TextView bgAlphaText = v.findViewById(R.id.bgAlphaText);
            bgAlphaText.setText("" + alpahVal + "%");
            bgAlphaSeekbar.setProgress(alpahVal);
            beyondView.setBgAlpha((int)( (alpahVal / 100f) * 255));
            bgAlphaSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    alpahVal = i;
                    bgAlphaText.setText("" + alpahVal + "%");
                    System.out.println("now alpha:" + alpahVal);
//                    topDecorShapeDrawable?.alpha = ((alpahVal/100f)*255).toInt()
                    if (beyondView!=null){
                        beyondView.setBgAlpha((int)( (alpahVal / 100f) * 255));
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setBgAlpha((int)( (alpahVal / 100f) * 255));
                    }
                    PreferencesUtils.setInt("alpahVal",alpahVal);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            List<Integer> dataList = new ArrayList<>();
            dataList.addAll(Arrays.asList(new Integer[]{R.color.c_1, R.color.c_2, R.color.c_3, R.color.c_4, R.color.c_5, R.color.c_6,
                    R.color.c_7, R.color.c_8, R.color.c_9, R.color.c_10, R.color.c_11, R.color.c_12,
                    R.color.c_13, R.color.c_14, R.color.c_15, R.color.c_16, R.color.c_17, R.color.c_18, R.color.c_19,
                    R.color.c_20, R.color.c_21, R.color.c_22, R.color.c_23, R.color.c_24}));
            ColorPickerAdapter colorAdapter = new ColorPickerAdapter(dataList, context);
            bgColor = PreferencesUtils.getInt("bgColor",bgColor);
            colorAdapter.mItemClick = new ColorPickerAdapter.OnRecyclerViewItemClick<Integer>() {
                @Override
                public void OnItemClick(View view, Integer integer, int i) {
                    bgColor = integer == null ? R.color.black : integer;
                    if (beyondView!=null){
                        beyondView.setBgColor(context.getResources().getColor(bgColor));
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setBgColor(context.getResources().getColor(bgColor));
                    }
                    PreferencesUtils.setInt("bgColor",bgColor);
                }
            };
            RecyclerView colorRecyView = v.findViewById(R.id.colorRecyView);
            colorRecyView.setAdapter(colorAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            colorRecyView.setLayoutManager(layoutManager);
        }
    }

    private void initSetBgViewLandSpace(View v, Context context) {
        if (beyondViewLandSpace != null) {
            alpahVal = PreferencesUtils.getInt("alpahVal",alpahVal);
//            TextView tcContentText = beyondView.findViewById(R.id.tcContentText);
            SeekBar bgAlphaSeekbar = v.findViewById(R.id.bgAlphaSeekbar);
            TextView bgAlphaText = v.findViewById(R.id.bgAlphaText);
            bgAlphaText.setText("" + alpahVal + "%");
            bgAlphaSeekbar.setProgress(alpahVal);
            beyondViewLandSpace.setBgAlpha((int)( (alpahVal / 100f) * 255));
            bgAlphaSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    alpahVal = i;
                    bgAlphaText.setText("" + alpahVal + "%");
                    System.out.println("now alpha:" + alpahVal);
//                    topDecorShapeDrawable?.alpha = ((alpahVal/100f)*255).toInt()
                    if (beyondView!=null){
                        beyondView.setBgAlpha((int)( (alpahVal / 100f) * 255));
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setBgAlpha((int)( (alpahVal / 100f) * 255));
                    }

                    beyondViewLandSpace.setBgAlpha((int)( (alpahVal / 100f) * 255));
                    PreferencesUtils.setInt("alpahVal",alpahVal);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            List<Integer> dataList = new ArrayList<>();
            dataList.addAll(Arrays.asList(new Integer[]{R.color.c_1, R.color.c_2, R.color.c_3, R.color.c_4, R.color.c_5, R.color.c_6,
                    R.color.c_7, R.color.c_8, R.color.c_9, R.color.c_10, R.color.c_11, R.color.c_12,
                    R.color.c_13, R.color.c_14, R.color.c_15, R.color.c_16, R.color.c_17, R.color.c_18, R.color.c_19,
                    R.color.c_20, R.color.c_21, R.color.c_22, R.color.c_23, R.color.c_24}));
            ColorPickerAdapter colorAdapter = new ColorPickerAdapter(dataList, context);
            bgColor = PreferencesUtils.getInt("bgColor",bgColor);
            colorAdapter.mItemClick = new ColorPickerAdapter.OnRecyclerViewItemClick<Integer>() {
                @Override
                public void OnItemClick(View view, Integer integer, int i) {
                    bgColor = integer == null ? R.color.black : integer;
//                        topDecorShapeDrawable.setColor(context.resources.getColor(bgColor))
                    if (beyondView!=null){
                        beyondView.setBgColor(context.getResources().getColor(bgColor));
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setBgColor(context.getResources().getColor(bgColor));
                    }
                    PreferencesUtils.setInt("bgColor",bgColor);
                }
            };
            RecyclerView colorRecyView = v.findViewById(R.id.colorRecyView);
            colorRecyView.setAdapter(colorAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            colorRecyView.setLayoutManager(layoutManager);
        }
    }


    public void initSetSpeedView(View v, Context context) {
        if (beyondView != null) {
            SeekBar scrollSpeedSeekbar = v.findViewById(R.id.scrollSpeedSeekbar);
            TextView scrollText = v.findViewById(R.id.scrollText);
            speed = PreferencesUtils.getInt("speed",speed);
            scrollText.setText("" + speed);
            scrollSpeedSeekbar.setProgress(speed);
            beyondView.setScrollSpeed(speed);
            scrollSpeedSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    speed = i;
                    scrollText.setText("" + (speed));
                    scrollSpeedSeekbar.setProgress(speed);
                    if (beyondView!=null){
                        beyondView.setScrollSpeed(speed);
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setScrollSpeed(speed);
                    }
                    PreferencesUtils.setInt("speed",speed);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }

    public void initSetSpeedViewLandSpace(View v, Context context) {
        if (beyondViewLandSpace != null) {
            SeekBar scrollSpeedSeekbar = v.findViewById(R.id.scrollSpeedSeekbar);
            TextView scrollText = v.findViewById(R.id.scrollText);
            speed = PreferencesUtils.getInt("speed",speed);
            scrollText.setText("" + speed);
            scrollSpeedSeekbar.setProgress(speed);
            beyondViewLandSpace.setScrollSpeed(speed);
            scrollSpeedSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    speed = i;
                    scrollText.setText("" + (speed));
                    scrollSpeedSeekbar.setProgress(speed);
                    if (beyondView!=null){
                        beyondView.setScrollSpeed(speed);
                    }
                    if (beyondViewLandSpace!=null){
                        beyondViewLandSpace.setScrollSpeed(speed);
                    }
                    PreferencesUtils.setInt("speed",speed);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }

    public void initSetContentView(View v, Context context) {
        if (beyondView != null) {
            RecyclerView tcRecyclerView = v.findViewById(R.id.tcRecycler);
            TextView tcContentText = beyondView.findViewById(R.id.tcContentText);
            TcAdapter tcAdapter = new TcAdapter(VarManger.tcList, context);
            tcRecyclerView.setAdapter(tcAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            tcRecyclerView.setLayoutManager(layoutManager);
            tcAdapter.onChangeTcListener = new TcAdapter.ChangeTcListener() {
                @Override
                public void click(String content) {
                    beyondView.setY(0);
                    tcContentText.setText("\n\n"+content+"(完)");
                    VarManger.fristStartTc = true;
                }
            };
        }
    }

    public void initSetContentViewLandSpace(View v, Context context) {
        if (beyondViewLandSpace != null) {
            RecyclerView tcRecyclerView = v.findViewById(R.id.tcRecycler);
            TextView tcContentText = beyondViewLandSpace.findViewById(R.id.tcContentText);
            TcAdapter tcAdapter = new TcAdapter(VarManger.tcList, context);
            tcRecyclerView.setAdapter(tcAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            tcRecyclerView.setLayoutManager(layoutManager);
            tcAdapter.onChangeTcListener = new TcAdapter.ChangeTcListener() {
                @Override
                public void click(String content) {
                    beyondViewLandSpace.setY(0);
                    tcContentText.setText("\n\n"+content+"(完)");
                    VarManger.fristStartTc = true;
                }
            };
        }
    }

}
