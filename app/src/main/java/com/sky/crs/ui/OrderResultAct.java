package com.sky.crs.ui;

/*
 * @创建者     Jrking
 * @创建时间   2017/3/21 3:04
 * @描述	      ${TODO}
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sky.crs.R;
import com.sky.crs.adapter.OredeResultAdapter;
import com.sky.crs.bean.LoginUserManger;
import com.sky.crs.bean.OrderResultBo;
import com.sky.crs.bean.Student;
import com.sky.crs.model.OrderModel;
import com.sky.crs.net.JsonCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class OrderResultAct extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private View mEmpty;
    private List<OrderResultBo> mBos = new ArrayList<>();
    private OredeResultAdapter mAdapter;
    private OrderModel mModel;
    private Student mStudent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order);
        mModel = new OrderModel(this);

        mStudent = LoginUserManger.getInstance().getStudent();
        mListView = (ListView) findViewById(R.id.listview);
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText("预约结果");
        mEmpty = findViewById(R.id.empty);
        mListView.setEmptyView(mEmpty);
        mAdapter = new OredeResultAdapter(this, mBos);
        mListView.setAdapter(mAdapter);
        mEmpty.setOnClickListener(this);
        initData();
    }

    @Override
    public void onClick(View v) {
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData() {
        mModel.loadorderRes(new JsonCallback<List<OrderResultBo>>() {
            @Override
            public void onSuccess(List<OrderResultBo> bos, Call call, Response response) {
                if (bos != null) {
                    mBos = bos;
                    mAdapter.addData(bos);
                }
            }
        }, mStudent.getStudentid());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mModel.cancle();
    }
}
