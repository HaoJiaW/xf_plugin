package com.jw.xfkplugin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.VH> {

    private List<Integer> dataList;
    private Context context;

    public ColorPickerAdapter(List<Integer> dataList,Context context) {
        this.dataList = dataList;
        this.context = context;
    }


    public OnRecyclerViewItemClick<Integer> mItemClick;

    //ViewHolder
    public static class VH extends RecyclerView.ViewHolder {
        public View colorView;

        public VH(View itemView) {
            super(itemView);
            colorView = itemView.findViewById(R.id.colorView);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.color_item, viewGroup, false);
        return new VH(view);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        if (dataList != null) {
            int resId = dataList.get(i);
            vh.colorView.setBackgroundColor(context.getResources().getColor(resId));
            vh.colorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClick != null) {
                        mItemClick.OnItemClick(view, resId, i);
                    }
                }
            });
        }
    }

    interface OnRecyclerViewItemClick<T> {
        void OnItemClick(View view, T t, int i);
    }
}