package com.jw.xfkplugin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class TcAdapter extends RecyclerView.Adapter<TcAdapter.MyViewHolder> {

    private List<TcBean> datalist;
    private Context context;

    public TcAdapter(List<TcBean> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }

    public ChangeTcListener onChangeTcListener;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tcTile;
        Button tcChangeBtn;
        public MyViewHolder(View view) {
            super(view);
            tcTile = view.findViewById(R.id.tcTitle);
            tcChangeBtn = view.findViewById(R.id.tcChangeBtn);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup p0, int i) {
        View view = LayoutInflater.from(p0.getContext()).inflate(R.layout.item_tc, p0, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder p0, int p1) {
        if (datalist != null) {
            TcBean tcBean = datalist.get(p1);
            p0.tcTile.setText(tcBean.getTitle());
            p0.tcTile.setTextColor(context.getResources().getColor(R.color.white));
            p0.tcChangeBtn.setText("切换");
            p0.tcChangeBtn.setBackground(context.getResources().getDrawable(R.drawable.btn_unuse_bg));
            if (tcBean.getUseing()) {
                p0.tcChangeBtn.setText("使用中");
                p0.tcChangeBtn.setBackground(context.getResources().getDrawable(R.drawable.btn_use_bg));
                p0.tcTile.setTextColor(context.getResources().getColor(R.color.yellow));
            }
            p0.tcChangeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!tcBean.getUseing()) {
                        for (int i = 0; i < datalist.size(); i++) {
                            TcBean tc = datalist.get(i);
                            tc.setUseing(false);
                        }
                        tcBean.setUseing(true);
                        notifyDataSetChanged();
                        if (onChangeTcListener != null) {
                            onChangeTcListener.click(tcBean.getContent());
                        }
                    }
                }
            });
        }
    }


    interface ChangeTcListener {
        void click(String content);
    }


}