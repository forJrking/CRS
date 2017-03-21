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

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sky.crs.R;
import com.sky.crs.adapter.DealOredeAdapter;
import com.sky.crs.bean.OrderResultBo;
import com.sky.crs.bean.Result;
import com.sky.crs.model.OrderModel;
import com.sky.crs.net.JsonCallback;
import com.sky.crs.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class DealWithOrderAct extends AppCompatActivity implements View.OnClickListener, DealOredeAdapter.OnDealWithListener {

    private ListView mListView;
    private View mEmpty;
    private List<OrderResultBo> mBos = new ArrayList<>();
    private DealOredeAdapter mAdapter;
    private OrderModel mModel;
    private BottomSheetDialog mDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order);
        mModel = new OrderModel(this);

        mListView = (ListView) findViewById(R.id.listview);
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText("预约审核");
        mEmpty = findViewById(R.id.empty);
        mListView.setEmptyView(mEmpty);
        mAdapter = new DealOredeAdapter(this, mBos);
        mListView.setAdapter(mAdapter);
        mEmpty.setOnClickListener(this);

        mAdapter.setOnDealWithListener(this);
        initData();
    }

    private void initBottom() {
        mDialog = new BottomSheetDialog(this);
        View view = View.inflate(this, R.layout.item_deal_list, null);
        mDialog.setContentView(view);
        mDialog.setCancelable(true);
        mDialog.findViewById(R.id.pass).setOnClickListener(this);
        mDialog.findViewById(R.id.stop).setOnClickListener(this);
        mDialog.findViewById(R.id.wait).setOnClickListener(this);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mDialog = null;
            }
        });
        mDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.empty:
                initData();
                break;
            case R.id.pass:
                toDeal(2);
                break;
            case R.id.stop:
                toDeal(1);
                break;
            case R.id.wait:
                toDeal(0);
                break;
        }
    }

    private void toDeal(int i) {

        int res = R.drawable.lo;
        switch (i) {
            case 0:
                res = R.drawable.lo;
                break;
            case 1:
                res = R.drawable.pas;
                break;
            case 2:
                res = R.drawable.su;
                break;
        }


        final int finalRes = res;
        mModel.dealorder(new JsonCallback<Result>() {
            @Override
            public void onSuccess(Result result, Call call, Response response) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                if (result != null)
                    UIUtil.toastShort(result.msg);
                if (result.sta == 0) {
                    ImageView iv = (ImageView) view.findViewById(R.id.iv_result);
                    if (iv != null)
                        iv.setImageResource(finalRes);
                }
            }
        }, bo.number, bo.studentid, i);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData() {
        mModel.loadDealorder(new JsonCallback<List<OrderResultBo>>() {
            @Override
            public void onSuccess(List<OrderResultBo> bos, Call call, Response response) {
                if (bos != null) {
                    mBos = bos;
                    mAdapter.addData(bos);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mModel.cancle();
    }

    OrderResultBo bo;
    View view;

    @Override
    public void onClickDeal(View view, OrderResultBo bo) {
        this.view = view;
        this.bo = bo;
        initBottom();
    }
}
