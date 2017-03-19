package com.sky.crs.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sky.crs.R;
import com.sky.crs.listener.NavigationFinishClickListener;
import com.sky.crs.util.UIUtil;


public class AboutActivity extends AppCompatActivity {

    TextView mAboutTvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mAboutTvVersion = (TextView) findViewById(R.id.about_tv_version);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        mAboutTvVersion.setText("1.0正式版");

        findViewById(R.id.about_btn_advice_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEmailClick();
            }
        });
    }


    protected void onEmailClick() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mailto:" + "forjrking@sina.com"));
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            intent.putExtra(Intent.EXTRA_SUBJECT, "来自客户端反馈");
            intent.putExtra(Intent.EXTRA_TEXT, "设备信息：Android " + Build.VERSION.RELEASE + " - " + Build.MANUFACTURER + " - " + Build.MODEL + "\n（如果涉及隐私请手动删除这个内容）\n\n");
            this.startActivity(intent);
        } else {
            UIUtil.toastShort("您的系统中没有安装邮件客户端");
        }
    }


}
