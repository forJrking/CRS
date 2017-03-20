package com.sky.crs.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.sky.crs.R;
import com.sky.crs.adapter.FeedListAdapter;
import com.sky.crs.bean.NoticeBo;
import com.sky.crs.model.NoticeModel;
import com.sky.crs.net.JsonCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/*
 * @创建者     Administrator
 * @创建时间   2017/1/3 0:17
 * @描述	      ${TODO}
 */
public class FeedListActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ListView mListview;
    private LinearLayout mEmpty;
    private NoticeModel mModel;
    private FeedListAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_feedback);
        mModel = new NoticeModel(this);
        initView();
        initdata();
    }

    public static void launch(Context cxt) {
        Intent intent = new Intent(cxt, FeedListActivity.class);
        cxt.startActivity(intent);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mListview = (ListView) findViewById(R.id.listview);
        mEmpty = (LinearLayout) findViewById(R.id.empty);

        mEmpty.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        initdata();
    }

    private void initdata() {
        mModel.loadFeedback(new JsonCallback<List<NoticeBo>>() {
            @Override
            public void onSuccess(List<NoticeBo> bos, Call call, Response response) {
                if (bos != null) {
                    mAdapter = new FeedListAdapter(FeedListActivity.this, bos);
                    mListview.setAdapter(mAdapter);
                }
                mListview.setEmptyView(mEmpty);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                mListview.setEmptyView(mEmpty);
            }
        });
    }
}
