package com.sky.crs.util;

import android.support.v4.widget.SwipeRefreshLayout;

import com.sky.crs.AppBase;
import com.sky.crs.R;


public final class RefreshLayoutUtils {

    private RefreshLayoutUtils() {}

    public static void initOnCreate(SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener refreshListener) {
        refreshLayout.setColorSchemeResources(R.color.red_light, R.color.green_light, R.color.blue_light, R.color.orange_light);
        refreshLayout.setOnRefreshListener(refreshListener);
    }

    /**
     * TODO refreshLayout无法直接在onCreate中设置刷新状态
     */
    public static void refreshOnCreate(final SwipeRefreshLayout refreshLayout, final SwipeRefreshLayout.OnRefreshListener refreshListener) {
        AppBase.getInstance().postDelay(new Runnable() {

            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                refreshListener.onRefresh();
            }

        }, 100);
    }

}
