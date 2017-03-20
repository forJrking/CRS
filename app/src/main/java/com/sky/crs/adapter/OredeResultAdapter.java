package com.sky.crs.adapter;

/*
 * @创建者     Administrator
 * @创建时间   2017/1/4 21:23
 * @描述	      ${TODO}
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.crs.R;
import com.sky.crs.bean.OrderResultBo;

import java.util.List;

public class OredeResultAdapter extends BaseAdapter {

    private Context mContext;
    private List<OrderResultBo> mBos;


    public OredeResultAdapter(Context mContext, List<OrderResultBo> docs) {
        this.mContext = mContext;
        this.mBos = docs;
    }

    public void addData(List<OrderResultBo> docs) {
        mBos.clear();
        mBos = docs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mBos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final MyViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_oder_resu, viewGroup, false);
            holder = new MyViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (MyViewHolder) view.getTag();
        }
        OrderResultBo bo = mBos.get(position);

        holder.name.setText(bo.name);
        holder.number.setText(bo.number);
        holder.hotic.setVisibility(bo.dealtype == 2 ? View.VISIBLE : View.GONE);
        holder.typeIc.setImageResource(bo.getTypeIc());
        holder.dealtype.setImageResource(bo.getdealtype());
        return view;
    }


    static class MyViewHolder {
        TextView name;
        TextView number;

        ImageView typeIc, hotic, dealtype;

        public MyViewHolder(View rootView) {
            this.name = (TextView) rootView.findViewById(R.id.tv_name);
            this.number = (TextView) rootView.findViewById(R.id.tv_num);
            this.dealtype = (ImageView) rootView.findViewById(R.id.iv_result);
            this.typeIc = (ImageView) rootView.findViewById(R.id.iv_type);
            this.hotic = (ImageView) rootView.findViewById(R.id.iv_hot);
        }
    }

}
