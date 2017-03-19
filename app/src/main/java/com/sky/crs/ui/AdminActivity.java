package com.sky.crs.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sky.crs.R;
import com.sky.crs.util.UIUtil;


public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView del_order;
    private TextView publish_not;
    private TextView feed_look;
    private TextView admin_cls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initView();
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void initView() {
        del_order = (TextView) findViewById(R.id.del_order);
        publish_not = (TextView) findViewById(R.id.publish_not);
        feed_look = (TextView) findViewById(R.id.feed_look);
        admin_cls = (TextView) findViewById(R.id.admin_cls);

        del_order.setOnClickListener(this);
        publish_not.setOnClickListener(this);
        feed_look.setOnClickListener(this);
        admin_cls.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.del_order:

                break;
            case R.id.publish_not:
                FeedbackActivity.launch(this,true);
                break;
            case R.id.feed_look:

                break;
            case R.id.admin_cls:


            default:
                break;
        }
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
}
