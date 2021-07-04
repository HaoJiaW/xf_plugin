package com.jw.xfkplugin.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jw.xfkplugin.R;
import com.jw.xfkplugin.VarManger;
import com.jw.xfkplugin.mode.AppBean;

import java.util.ArrayList;
import java.util.List;

public class AppIconAdapter extends RecyclerView.Adapter<AppIconAdapter.AppIconViewHolder>{

    private Context mContext;

    public List<AppBean> appBeanList = new ArrayList<>();

    public AppIconAdapter(Context context){
        mContext = context;
    }

    public void setData(List<AppBean> appBeanList){
        this.appBeanList.clear();
        this.appBeanList.addAll(appBeanList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AppIconViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_adapter_icon_item,viewGroup,false);
        return new AppIconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppIconViewHolder appIconViewHolder, int position) {
        AppBean appBean = appBeanList.get(position);
        Glide.with(mContext).load(appBean.getImageUrl()).into(appIconViewHolder.ivAppIcon);
        appIconViewHolder.ivAppIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VarManger.redictAct(mContext,appBean.getPkgName(),String.format("您的手机未安装%sAPP",appBean.getAppName()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return appBeanList.size();
    }

    public static class AppIconViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivAppIcon;

        public AppIconViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAppIcon = itemView.findViewById(R.id.iv_app_icon);
        }

    }

}
