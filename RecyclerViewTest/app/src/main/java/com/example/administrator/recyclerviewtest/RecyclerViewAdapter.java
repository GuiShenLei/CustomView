package com.example.administrator.recyclerviewtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wbx on 2018/3/19.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.LinearViewHoler> {
    private Context mContext;
    private OnItemClick mOnItemClick;

    public RecyclerViewAdapter(Context context, OnItemClick onItemClick){
        mContext = context;
        mOnItemClick = onItemClick;
    }

    @Override
    public RecyclerViewAdapter.LinearViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHoler(LayoutInflater.from(mContext).inflate(R.layout.linearlayout,null));
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.LinearViewHoler holder, int position) {
        holder.textView.setText("gellllgh");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClick.onClickLinstener();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class LinearViewHoler extends RecyclerView.ViewHolder{
        private TextView textView;

        public LinearViewHoler(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text);
        }
    }

    public interface OnItemClick{
        public void onClickLinstener();
    }
}
