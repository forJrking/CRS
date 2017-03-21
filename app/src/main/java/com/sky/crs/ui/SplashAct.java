package com.sky.crs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sky.crs.R;

import java.lang.ref.WeakReference;


public class SplashAct extends AppCompatActivity implements OnClickListener, OnPageChangeListener {

    private static final long DELAY = 3000;
    private ViewPager mViewPager;

    private final int[] pics = {R.drawable.sp1, R.drawable.sp2, R.drawable.sp3, R.drawable.sp4};

    private ImageView[] mPoints;

    private int currentIndex = 0;

    private Button btn;
    private AutoHandler mHandler;

    //  if (currentIndex == pics.length - 1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        initView();
        initPoint();
        mHandler = new AutoHandler(this);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new ViewPagerAdapter());
        mViewPager.setOnTouchListener(new TouchListener());
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(currentIndex);
        btn = (Button) findViewById(R.id.hy_btn);
        btn.setOnClickListener(this);
    }

    private void initPoint() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hy_layout);
        mPoints = new ImageView[pics.length];
        for (int i = 0; i < pics.length; i++) {
            mPoints[i] = (ImageView) linearLayout.getChildAt(i);
            mPoints[i].setEnabled(false);
            mPoints[i].setTag(i);
        }
        mPoints[currentIndex].setEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendMessageDelayed(Message.obtain(), DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pics.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            ImageView iv = new ImageView(SplashAct.this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[position]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        setPoints(arg0);
    }


    private void setPoints(int pos) {
        if (pos < 0 || pos > pics.length || currentIndex == pos) return;
        mPoints[pos].setEnabled(true);
        mPoints[currentIndex].setEnabled(false);
        currentIndex = pos;
    }

    @Override
    public void onClick(View arg0) {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public static class AutoHandler extends Handler {
        WeakReference<SplashAct> ref;

        AutoHandler(SplashAct act) {
            this.ref = new WeakReference<SplashAct>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SplashAct activity = ref.get();
            if (activity == null) return;
            if (activity.isContinue) {
                if (activity.currentIndex == activity.pics.length - 1) {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                    return;
                }
                activity.mViewPager.setCurrentItem(activity.currentIndex + 1);
            }
            sendMessageDelayed(Message.obtain(), DELAY);
        }
    }

    boolean isContinue = true;

    class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    isContinue = false;
                    break;
                case MotionEvent.ACTION_UP:
                default:
                    isContinue = true;
                    break;
            }
            return false;
        }

    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

}
