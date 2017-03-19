package com.sky.crs.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/*
 * @创建者     Administrator
 * @创建时间   2016/2/17 20:46
 * @描述	      不可滑动的ViewPager

 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }


    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //不消费触摸事件
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //不拦截子的事件
        return false;
    }
}
