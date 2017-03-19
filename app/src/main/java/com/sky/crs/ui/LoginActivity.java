package com.sky.crs.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.sky.crs.R;
import com.sky.crs.bean.Admin;
import com.sky.crs.bean.LoginUserManger;
import com.sky.crs.bean.Student;
import com.sky.crs.conf.Constant;
import com.sky.crs.net.StringDialogCallback;
import com.sky.crs.net.URL;
import com.sky.crs.util.NetworkUtils;
import com.sky.crs.util.RegexUtils;
import com.sky.crs.util.SPUtils;
import com.sky.crs.util.UIUtil;

import okhttp3.Call;
import okhttp3.Response;

import static android.text.TextUtils.isEmpty;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;
    private EditText mUsername_et;
    private EditText mPassword_et, etIp;
    private CheckBox mAdminCheckBox;
    private TextView mService_Tv, mRegister;
    private Button mLoginButton;
    private Dialog cloudDialog;

    private String mUsername;
    private String mPassword;

    private boolean isAdmin = false;

    private int requestCode = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initAddress();
        initData();
    }

    private void initData() {
        isAdmin = LoginUserManger.getInstance().getisAdmin();
        mAdminCheckBox.setChecked(isAdmin);
        if (isAdmin) {
            Admin admin = LoginUserManger.getInstance().getAdmin();
            if (admin != null) {
                mUsername_et.setText(admin.getAccount());
                mPassword_et.setText(admin.getPsd());
            }
        } else {
            Student student = LoginUserManger.getInstance().getStudent();
            if (student != null) {
                mUsername_et.setText(student.getStudentid());
                mPassword_et.setText(student.getPsd());
            }
        }
    }


    private void initView() {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mUsername_et = (EditText) findViewById(R.id.username);
        mPassword_et = (EditText) findViewById(R.id.password);
        mAdminCheckBox = (CheckBox) findViewById(R.id.admin_checkBox);
        mService_Tv = (TextView) findViewById(R.id.service_tv);
        mRegister = (TextView) findViewById(R.id.register_tv);
        mLoginButton = (Button) findViewById(R.id.login_button);

        mAdminCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mAdminCheckBox.setText(R.string.admin_user);
                    mRegister.setVisibility(View.GONE);
                } else {
                    mAdminCheckBox.setText(R.string.student_login);
                    mRegister.setVisibility(View.VISIBLE);
                }
                isAdmin = b;
            }
        });

        mRegister.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mService_Tv.setOnClickListener(this);
        mUsername_et.addTextChangedListener(loginTextWatch);
        mPassword_et.addTextChangedListener(loginTextWatch);
    }

    TextWatcher loginTextWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            mUsername = mUsername_et.getText().toString().trim();
            mPassword = mPassword_et.getText().toString().trim();
            if (!isEmpty(mUsername) && !isEmpty(mPassword)) {
                mLoginButton.setEnabled(true);
            } else {
                mLoginButton.setEnabled(false);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                //登录
                toLogin();
                break;
            case R.id.service_tv:
                cloudDialog.show();
                break;
            case R.id.register_tv:
                //注册
                if (isAdmin) {
                    UIUtil.toastShort("APP只允许学生注册");
                    return;
                }
                startActivityForResult(new Intent(this, RegisterActivity.class), requestCode);

                break;
        }
    }

    private void toLogin() {
        if (NetworkUtils.isConnected()) {
            OkGo.post(URL.BASE + URL.LOGIN_URL)
                    .tag(this)
                    .params("account", mUsername)
                    .params("psd", mPassword)
                    .params("type", isAdmin ? 1 : 0)
                    .execute(new StringDialogCallback(this) {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            if (!response.isSuccessful() || TextUtils.isEmpty(s)) {
                                UIUtil.toastShort("检查用户名密码");
                                return;
                            }
                            try {
                                Gson gson = new Gson();
                                Intent intent = null;
                                if (isAdmin) {
                                    Admin admin = gson.fromJson(s, Admin.class);
                                    LoginUserManger.getInstance().setLoginUser(admin);
                                    intent = new Intent(LoginActivity.this, AdminActivity.class);
                                } else {
                                    Student student = gson.fromJson(s, Student.class);
                                    LoginUserManger.getInstance().setLoginUser(student);
                                    intent = new Intent(LoginActivity.this, MainActivity.class);
                                }
                                LoginUserManger.getInstance().setIsAmin(isAdmin);
                                startActivity(intent);
                                finish();
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                                onError(call, response, e);
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            UIUtil.toastShort(e.toString());
                        }
                    });
        }
    }

    private void initAddress() {
        cloudDialog = new Dialog(this, R.style.IPDialog);
        cloudDialog.setContentView(R.layout.dialog_address);
        cloudDialog.setCancelable(true);
        cloudDialog.setCanceledOnTouchOutside(false);

        etIp = (EditText) cloudDialog.findViewById(R.id.edit_ip);

        String address = SPUtils.getString(this, Constant.CONN_ADDRESS);

        if (!TextUtils.isEmpty(address)) {
            URL.BASE = getString(R.string.address, address);
            etIp.setText(address);
            etIp.setSelection(address.length());
        } else {
            UIUtil.toastLong("请配置服务器IP地址");
        }

        cloudDialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cloudDialog.dismiss();
            }
        });

        //确定按钮
        cloudDialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String ip = etIp.getText().toString().trim();

                //校验数据的格式问题
                if (TextUtils.isEmpty(ip) || !RegexUtils.isIP(ip)) {
                    UIUtil.toastShort("服务器地址格式错误");
                    return;
                }

                SPUtils.putString(LoginActivity.this, Constant.CONN_ADDRESS, ip);
                String address = getString(R.string.address, ip);
                URL.BASE = address;
                cloudDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            mUsername_et.setText(data.getStringExtra(Constant.Intent.USERNAME));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
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

