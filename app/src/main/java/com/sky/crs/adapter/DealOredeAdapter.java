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
import com.sky.crs.util.Cxt;

import java.util.List;

public class DealOredeAdapter extends BaseAdapter {

    private Context mContext;
    private List<OrderResultBo> mBos;


    public DealOredeAdapter(Context mContext, List<OrderResultBo> docs) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_oder_deal, viewGroup, false);
            holder = new MyViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (MyViewHolder) view.getTag();
        }
        final OrderResultBo bo = mBos.get(position);

        holder.name.setText(Cxt.getStr(R.string.deal_or, bo.studentid));
        holder.tv_class.setText(bo.number + " " + bo.name);
        holder.hotic.setVisibility(bo.dealtype == 2 ? View.VISIBLE : View.GONE);
        holder.typeIc.setImageResource(bo.getTypeIc());
        holder.dealtype.setImageResource(bo.getdealtype());

        final View finalView = view;
        holder.dealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onClickDeal(finalView, bo);
            }
        });

        return view;
    }

    static class MyViewHolder {
        TextView name;
        TextView tv_class;

        ImageView typeIc, hotic, dealtype;

        public MyViewHolder(View rootView) {
            this.name = (TextView) rootView.findViewById(R.id.tv_id);
            this.tv_class = (TextView) rootView.findViewById(R.id.tv_class);
            this.dealtype = (ImageView) rootView.findViewById(R.id.iv_result);
            this.typeIc = (ImageView) rootView.findViewById(R.id.iv_type);
            this.hotic = (ImageView) rootView.findViewById(R.id.iv_hot);
        }
    }

    OnDealWithListener mListener;

    public void setOnDealWithListener(OnDealWithListener listener) {
        this.mListener = listener;
    }

    public interface OnDealWithListener {
        void onClickDeal(View view, OrderResultBo bo);
    }
}
