package com.sky.crs.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.sky.crs.R;
import com.sky.crs.bean.Admin;
import com.sky.crs.bean.LoginUserManger;
import com.sky.crs.bean.Result;
import com.sky.crs.bean.Student;
import com.sky.crs.conf.Constant;
import com.sky.crs.net.JsonCallback;
import com.sky.crs.net.URL;
import com.sky.crs.util.UIUtil;

import okhttp3.Call;
import okhttp3.Response;


/*
 * @创建者     Administrator
 * @创建时间   2017/1/3 0:17
 * @描述	      ${TODO}
 */
public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private EditText mConten;

    private Button mCommit;
    private boolean mIsNotice;
    private String createrid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_notice);
        Intent intent = getIntent();
        if (intent != null) {
            mIsNotice = intent.getBooleanExtra(Constant.Intent.IS_NOTICE, false);
        }
        initView();
    }

    public static void launch(Context cxt, boolean isNotice) {
        Intent intent = new Intent(cxt, FeedbackActivity.class);
        intent.putExtra(Constant.Intent.IS_NOTICE, isNotice);
        cxt.startActivity(intent);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mConten = (EditText) findViewById(R.id.et_notice);
        mCommit = (Button) findViewById(R.id.commit_btn);
        mCommit.setOnClickListener(this);


        //如果是更新界面显示 初始化的数据
        if (mIsNotice) {
            mToolbar.setTitle("发布公告");
            mConten.setHint(R.string.notice_tip);
            Admin admin = LoginUserManger.getInstance().getAdmin();
            createrid = admin.getAccount();
        } else {
            Student student = LoginUserManger.getInstance().getStudent();
            createrid = student.getStudentid();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_btn:
                toCommit();
                break;
        }
    }

    //注册和更新资料复用
    private void toCommit() {
        String content = mConten.getText().toString().trim();

        if (isEmpty(content)) {
            UIUtil.toastShort("信息不能为空");
            return;
        }


        OkGo.post(URL.BASE + (URL.FEEDBACK_URL))
                .tag(this)
                .params("type", mIsNotice ? 0 : 1)
                .params("content", content)
                .params("createrid", createrid)
                .execute(new JsonCallback<Result>() {
                    @Override
                    public void onSuccess(Result result, Call call, Response response) {
                        if (result == null) {
                            return;
                        }
                        UIUtil.toastShort(result.msg);
                        if (result.sta == Constant.SUCCESS) {
                            finish();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        UIUtil.toastShort(e.toString());
                    }
                });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    //判空工具
    public boolean isEmpty(String... arg) {
        for (String s : arg) {
            if (TextUtils.isEmpty(s)) {
                return true;
            }
        }
        return false;
    }
}
