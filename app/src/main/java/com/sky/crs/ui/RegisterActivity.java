package com.sky.crs.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.sky.crs.R;
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
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private Toolbar mRegisterToolbar;
    private EditText mNameEt;
    private RadioGroup mGenderRg;
    private EditText mId, mCollege;
    private EditText mPassword;
    private EditText mCheckcodeEt;
    private ImageView mCheckcodeIv;
    private Button mRegisterBtn;

    private int gender = 1;
    private String mHeaderCode;
    private int perm = 0;
    private boolean mIsUpdate;//是否是更新数据界面
    private AppCompatSpinner mPost;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Intent intent = getIntent();
        if (intent != null) {
            mIsUpdate = intent.getBooleanExtra(Constant.Intent.IS_UPDATE, false);
        }
        initView();
        initCode();
    }

    private void initCode() {
        OkGo.post(URL.BASE + URL.CHECKCODE).execute(new BitmapCallback() {
            @Override
            public void onSuccess(Bitmap bitmap, Call call, Response response) {

                if (bitmap != null) {
                    mCheckcodeIv.setImageBitmap(bitmap);
                    mHeaderCode = response.header("checkcode");
                }
            }
        });

    }

    private void initView() {
        mRegisterToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        mNameEt = (EditText) findViewById(R.id.name_et);
        mGenderRg = (RadioGroup) findViewById(R.id.gender_rg);
        mId = (EditText) findViewById(R.id.tel_et);
        mPassword = (EditText) findViewById(R.id.password);
        mCheckcodeEt = (EditText) findViewById(R.id.checkcode_et);
        mPost = (AppCompatSpinner) findViewById(R.id.zhiw_et);
        mCollege = (EditText) findViewById(R.id.xuey_et);
        mCheckcodeIv = (ImageView) findViewById(R.id.checkcode_iv);
        mRegisterBtn = (Button) findViewById(R.id.register_btn);
        mPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                perm = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mRegisterBtn.setOnClickListener(this);
        mCheckcodeIv.setOnClickListener(this);
        mRegisterToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mGenderRg.setOnCheckedChangeListener(this);

        //如果是更新界面显示 初始化的数据
        if (mIsUpdate) {
            mRegisterToolbar.setTitle("修改资料");
            mRegisterBtn.setText("提交");
            mId.setEnabled(false);
            if (LoginUserManger.getInstance().getisAdmin()) {
            } else {
                Student student = LoginUserManger.getInstance().getStudent();
                mGenderRg.check(student.isMan() ? R.id.man : R.id.femal);
                mNameEt.setText(student.getName());
                mId.setText(student.getStudentid());
                mCollege.setText(student.getCollege());
                mPost.setSelection(student.getPerm());
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                toRegister(mIsUpdate);
                break;
            case R.id.checkcode_iv:
                initCode();
                break;
        }
    }

    //注册和更新资料复用
    private void toRegister(final boolean isUpdate) {
        String name = mNameEt.getText().toString().trim();
        final String id = mId.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String checkcode = mCheckcodeEt.getText().toString().trim();

        String coloege = mCollege.getText().toString().trim();

        if (isEmpty(name, id, password, checkcode, coloege)) {
            UIUtil.toastShort("注册信息都不能为空");
            return;
        }

        if (!checkcode.equals(mHeaderCode)) {
            UIUtil.toastShort("验证码错误");
            return;
        }

        OkGo.post(URL.BASE + (isUpdate ? URL.UPDATE_URL : URL.REGISTER_URL)).tag(this)
                .params("studentid", id)
                .params("psd", password)
                .params("name", name)
                .params("gender", gender)
                .params("college", coloege)
                .params("permission", perm)
                .execute(new JsonCallback<Result>() {
                    @Override
                    public void onSuccess(Result result, Call call, Response response) {
                        if (result == null) {
                            return;
                        }
                        UIUtil.toastShort(result.msg);
                        if (result.sta == Constant.SUCCESS) {
                            Intent data = new Intent();
                            data.putExtra(Constant.Intent.USERNAME, id);
                            setResult(RESULT_OK, data);
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
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.man) {
            gender = 1;
        } else {
            gender = 0;
        }
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
