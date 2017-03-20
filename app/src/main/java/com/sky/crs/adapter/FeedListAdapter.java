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
import android.widget.TextView;

import com.sky.crs.R;
import com.sky.crs.bean.NoticeBo;
import com.sky.crs.util.Cxt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FeedListAdapter extends BaseAdapter {

    private Context mContext;
    private List<NoticeBo> mBos;


    public FeedListAdapter(Context mContext, List<NoticeBo> docs) {
        this.mContext = mContext;
        this.mBos = docs;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_feedback, viewGroup, false);
            holder = new MyViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (MyViewHolder) view.getTag();
        }

        NoticeBo bo = mBos.get(position);
        holder.author_tv.setText(Cxt.getStr(R.string.creater, bo.createrid));
        holder.content.setText(bo.content);
        holder.time_tv.setText(getFormatTime(bo.time));
        return view;
    }

    private String getFormatTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }


    static class MyViewHolder {
        TextView author_tv;
        TextView content;
        TextView time_tv;

        public MyViewHolder(View rootView) {
            this.author_tv = (TextView) rootView.findViewById(R.id.tv_create);
            this.content = (TextView) rootView.findViewById(R.id.tv_content);
            this.time_tv = (TextView) rootView.findViewById(R.id.tv_time);
        }
    }

}
