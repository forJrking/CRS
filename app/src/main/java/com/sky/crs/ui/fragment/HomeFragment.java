package com.sky.crs.ui.fragment;

/*
 * @创建者     Jrking
 * @创建时间   2017/3/16 21:56
 * @描述	      ${TODO}
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.adapter.CBViewHolderCreator;
import com.sky.crs.R;
import com.sky.crs.bean.NoticeBo;
import com.sky.crs.control.ADControl;
import com.sky.crs.model.NoticeModel;
import com.sky.crs.net.JsonCallback;
import com.sky.crs.ui.ExpressActivity;
import com.sky.crs.ui.FeedbackActivity;
import com.sky.crs.ui.MainActivity;
import com.sky.crs.util.UIUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private ConvenientBanner banner;

    private ViewFlipper mViewFlipper;

    String[] strs = {"大学生晚睡成风为何难改...", "上了大学你迷茫吗？你又努力奋斗么...", "谁的大学不迷茫 名师为你导航...", "一图读懂“普通高等学校学生党建工作标准..."};
    private TextView mContent;
    private TextView mTVtime;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    private List<Integer> images = new ArrayList<Integer>();

    @Override
    protected void init() {
        banner = (ConvenientBanner) findViewById(R.id.banner);
        findViewById(R.id.exam_num).setOnClickListener(this);
        findViewById(R.id.c_order).setOnClickListener(this);
        findViewById(R.id.express).setOnClickListener(this);
        findViewById(R.id.no_develop).setOnClickListener(this);
        images.add(R.drawable.b1);
        images.add(R.drawable.b2);
        images.add(R.drawable.b3);
        images.add(R.drawable.b4);
        initPoint();
        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, images);

        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPoints(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mViewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
        for (int i = 0; i < strs.length; i++) {
            ADControl adControl = new ADControl(mActivity);
            adControl.setAD(strs[i]);
            mViewFlipper.addView(adControl.getRootView());
        }

        mContent = (TextView) findViewById(R.id.tv_notice_content);
        mTVtime = (TextView) findViewById(R.id.tv_notice_time);

//        获取公告
        new NoticeModel(getContext()).loadNotice(new JsonCallback<NoticeBo>() {

            @Override
            public void onSuccess(NoticeBo bo, Call call, Response response) {
                if (bo == null) return;
                mContent.setText(bo.content);
                mTVtime.setText(getFormatTime(bo.time));
            }
        });
    }

    private String getFormatTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }


    private ImageView[] mPoints;
    private int currentIndex = 0;

    private void setPoints(int pos) {
        if (pos < 0 || pos > images.size() || currentIndex == pos) return;
        mPoints[pos].setEnabled(true);
        mPoints[currentIndex].setEnabled(false);
        currentIndex = pos;
    }

    private void initPoint() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hy_layout);
        mPoints = new ImageView[images.size()];
        for (int i = 0; i < images.size(); i++) {
            mPoints[i] = (ImageView) linearLayout.getChildAt(i);
            mPoints[i].setEnabled(false);
            mPoints[i].setTag(i);
        }
        mPoints[currentIndex].setEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startTurning(3000);// 2s 换一张
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopTurning();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exam_num:
                FeedbackActivity.launch(getActivity(),false);
                break;
            case R.id.c_order:
//                预约
                ((MainActivity) getActivity()).switchCurrent(1);
                break;
            case R.id.express:
//
                startActivity(new Intent(getActivity(), ExpressActivity.class));
                break;
            default:
                UIUtil.toastShort("正在努力开发中...");
                break;
        }
    }


    public class NetworkImageHolderView implements CBPageAdapter.Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }


    }
}
