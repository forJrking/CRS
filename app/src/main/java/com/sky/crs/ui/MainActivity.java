package com.sky.crs.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sky.crs.AppBase;
import com.sky.crs.R;
import com.sky.crs.ui.fragment.HomeFragment;
import com.sky.crs.ui.fragment.MineFragment;
import com.sky.crs.ui.fragment.OrderFragment;
import com.sky.crs.util.UIUtil;
import com.sky.crs.widget.NoScrollViewPager;

public class MainActivity extends AppCompatActivity {

    NoScrollViewPager homeViewPager;
    MenuItem prevMenuItem;
    BottomNavigationView bottomNavView;
    private TextView mTitle;

    private int[] titles = {R.string.item_home, R.string.item_explore, R.string.item_mine};
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.tv_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        homeViewPager = (NoScrollViewPager) findViewById(R.id.home_view_pager);
        bottomNavView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        initViewPager();
        initBottomNav();
    }

    long exitTime = 0;

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            exitTime = System.currentTimeMillis();
            UIUtil.toastShort("再点击一次退出");
        } else {
            super.onBackPressed();
            System.exit(0);
        }

    }

    public void switchCurrent(int cur) {
        homeViewPager.setCurrentItem(cur);
    }


    private void initViewPager() {
        homeViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new HomeFragment();
                        break;
                    case 1:
                        fragment = new OrderFragment();
                        break;
                    case 2:
                        fragment = new MineFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        homeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();

                if (position == 2) {
                    mToolbar.setVisibility(View.GONE);
                } else {
                    mToolbar.setVisibility(View.VISIBLE);
                }
                /**
                 * 该方法只有在有新的页面被选中时才会回调
                 * 如果 preMenuItem 为 null，说明该方法还没有被回调过
                 * 则ViewPager从创建到现在都处于 position 为 0 的页面
                 * 所以当该方法第一次被回调的时候，直接将 position 为 0 的页面的 selected 状态设为 false 即可
                 *
                 * 如果 preMenuItem 不为 null，说明该方法内的
                 * "prevMenuItem = bottomNavView.getMenu().getItem(position);"
                 * 之前至少被调用过一次
                 * 所以当该方法再次被回调的时候，直接将上一个 prevMenuItem 的 selected 状态设为 false 即可
                 * 在做完上一句的事情后，将当前页面设为 prevMenuItem，以备下次调用

                 */
                if (prevMenuItem == null) {
                    bottomNavView.getMenu().getItem(0).setChecked(false);
                } else {
                    prevMenuItem.setChecked(false);
                }

                bottomNavView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavView.getMenu().getItem(position);
                mTitle.setText(titles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void initBottomNav() {
        bottomNavView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        homeViewPager.setCurrentItem(item.getOrder());
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.about:
                AppBase.getInstance().postDelay(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                    }
                }, 400);
                break;
            case R.id.scan:
                AppBase.getInstance().postDelay(new Runnable() {
                    @Override
                    public void run() {
                        ACT_QR.launch(MainActivity.this, true, true, true);
                    }
                }, 400);
                break;
            case R.id.exit:
                //v7包
                AlertDialog dialog = new AlertDialog.Builder(this, R.style.AppDialogLight)
                        .setMessage(R.string.agree_exit)
                        .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton(R.string.disagree, null)
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;
        if (requestCode == ACT_QR.REQUEST_CODE) {
            String dataNum = data.getStringExtra(ACT_QR.RESULT);
            if (TextUtils.isEmpty(dataNum)) return;
            if (dataNum.startsWith("http")) {
                ACT_Web.launch(MainActivity.this, dataNum);
            } else {
                UIUtil.toastShort(dataNum);
            }
        }
    }
}
