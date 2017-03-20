package com.sky.crs.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sky.crs.R;
import com.sky.crs.adapter.ClassRoomAdapter;
import com.sky.crs.bean.ClassRoomBo;
import com.sky.crs.bean.LoginUserManger;
import com.sky.crs.bean.Result;
import com.sky.crs.bean.Student;
import com.sky.crs.conf.Constant;
import com.sky.crs.model.OrderModel;
import com.sky.crs.net.JsonCallback;
import com.sky.crs.util.UIUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class OrderFragment extends BaseFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView mListView;
    private View mEmpty;
    private List<ClassRoomBo> mBos = new ArrayList<>();
    private ClassRoomAdapter mAdapter;
    private OrderModel mModel;
    private Student mStudent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void init() {
        mModel = new OrderModel(mActivity);

        mStudent = LoginUserManger.getInstance().getStudent();
        mListView = (ListView) findViewById(R.id.listview);
        mEmpty = findViewById(R.id.empty);
        mListView.setEmptyView(mEmpty);
        mAdapter = new ClassRoomAdapter(mActivity, mBos);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mEmpty.setOnClickListener(this);
        initData();
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        mModel.loadClassRooms(new JsonCallback<List<ClassRoomBo>>() {
            @Override
            public void onSuccess(List<ClassRoomBo> bos, Call call, Response response) {
                if (bos != null) {
                    mBos = bos;
                    mAdapter.addData(bos);
                }
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
//        调用预约接口
        final ClassRoomBo bo = mBos.get(position);
        if (bo.steated >= bo.seat) {
            UIUtil.toastShort("预约已满！");
            return;
        }


        if (mStudent.getPerm() == 0 && bo.type != 4) {
            UIUtil.toastShort("学生不能预约" + bo.name);
            return;
        }

        if (mStudent.getPerm() == 1 && bo.type != 3 && bo.type != 4) {
            UIUtil.toastShort("班委不能预约" + bo.name);
            return;
        }


        mModel.orderClassRooms(new JsonCallback<Result>() {
            @Override
            public void onSuccess(Result result, Call call, Response response) {
                if (result != null) {
                    UIUtil.toastShort(result.msg);
                    if (result.sta == Constant.SUCCESS) {
                        TextView tv = (TextView) view.findViewById(R.id.tv_seatnum);
                        int i = bo.steated++;
                        tv.setText(i + "/" + bo.seat);
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    UIUtil.toastShort("出错");
                }
            }
        }, bo.name, bo.number, mStudent.getStudentid(), bo.seat, bo.type);
    }


    @Override
    public void onClick(View v) {
        initData();
    }
}
