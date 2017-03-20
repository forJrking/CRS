package com.sky.crs.ui.fragment;

/*
 * @创建者     Jrking
 * @创建时间   2017/3/16 21:55
 * @描述	      ${TODO}
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.sky.crs.R;
import com.sky.crs.bean.LoginUserManger;
import com.sky.crs.bean.Student;
import com.sky.crs.conf.Constant;
import com.sky.crs.ui.AboutActivity;
import com.sky.crs.ui.LoginActivity;
import com.sky.crs.ui.RegisterActivity;
import com.sky.crs.widget.CircleImageView;

import okhttp3.Call;
import okhttp3.Response;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    public CircleImageView mIvAvatar;
    public TextView mTvName;
    public TextView mStudentId, mtvGender;
    public TextView mXueyuan;
    public TextView mZhiw;

    public LinearLayout mAbout;
    public LinearLayout mExit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void init() {


        initView();
        initdata();
    }

    private void initdata() {
//        获取数据
        Student student = LoginUserManger.getInstance().getStudent();
        if (student == null) return;
        mTvName.setText(student.getName());
        mStudentId.setText("学号：" + student.getStudentid());
        mXueyuan.setText("学院：" + student.getCollege());
        mZhiw.setText("职位：" + student.getPerStr());
        mtvGender.setText("性别：" + (student.isMan() ? "男" : "女"));

        if (!TextUtils.isEmpty(student.getAvatar()) && student.getAvatar().contains(".jpg")) {
            OkGo.post(student.getAvatar()).execute(new BitmapCallback() {
                @Override
                public void onSuccess(Bitmap bitmap, Call call, Response response) {
                    if (bitmap != null) {
                        mIvAvatar.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }


    public void initView() {
        this.mIvAvatar = (CircleImageView) findViewById(R.id.iv_avatar);
        this.mTvName = (TextView) findViewById(R.id.tv_name);
        this.mStudentId = (TextView) findViewById(R.id.student_id);
        this.mXueyuan = (TextView) findViewById(R.id.xueyuan);
        this.mtvGender = (TextView) findViewById(R.id.gender);
        this.mZhiw = (TextView) findViewById(R.id.zhiw);
        this.mAbout = (LinearLayout) findViewById(R.id.about);
        this.mExit = (LinearLayout) findViewById(R.id.exit);

        findViewById(R.id.edit_tv).setOnClickListener(this);
        mExit.setOnClickListener(this);
        mAbout.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_tv:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                intent.putExtra(Constant.Intent.IS_UPDATE, true);
                startActivity(intent);
                break;
            case R.id.about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.exit:
                //v7包
                new AlertDialog.Builder(getActivity(), R.style.AppDialogLight)
                        .setMessage(R.string.agree_exit_login)
                        .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton(R.string.disagree, null)
                        .show();
                break;
            default:
                break;
        }
    }
}
