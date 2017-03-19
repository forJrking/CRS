package com.sky.crs.ui;

/*
 * @创建者     Jrking
 * @创建时间   2017/3/18 2:05
 * @描述	      ${TODO}
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.crs.R;
import com.sky.crs.bean.Express;
import com.sky.crs.listener.NavigationFinishClickListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static com.sky.crs.R.id.toolbar;

public class ExpressActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private AppCompatSpinner mSpinner;
    private ImageView mIvQr;
    private EditText mEtNumber;
    private Button mBtnLoad;
    private TextView mTextView;
    private Express mExpress;

    private String comp = "";
    private String num;
    private List<String> mCompanys;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_express);
        initView();
//        https://m.kuaidi100.com/index_all.html?type=ems&postid=9890534827287&callbackurl=[点击"返回"跳转的地址]
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(toolbar);
        mToolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        mSpinner = (AppCompatSpinner) findViewById(R.id.spinner);
        mIvQr = (ImageView) findViewById(R.id.iv_qr);
        mEtNumber = (EditText) findViewById(R.id.et_number);
        mBtnLoad = (Button) findViewById(R.id.btn_load);
        mTextView = (TextView) findViewById(R.id.textView);

        mBtnLoad.setOnClickListener(this);
        mIvQr.setOnClickListener(this);

        mExpress = new Express();
        mCompanys = mExpress.getExCompanys();
        comp = mCompanys.get(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, mCompanys);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//      绑定 Adapter到控件
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                //选中快递公司
                comp = mCompanys.get(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load:
                submit();
                break;
            case R.id.iv_qr:
                ACT_QR.launch(this, false, true, false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;
        if (requestCode == ACT_QR.REQUEST_CODE) {
            String dataNum = data.getStringExtra(ACT_QR.RESULT);
            mEtNumber.setText(dataNum);
            mEtNumber.setSelection(num.length());
        }
    }

    private void submit() {
        // validate
        num = mEtNumber.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            Toast.makeText(this, "请输入快递单号", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            ACT_Web.launch(this, "https://m.kuaidi100.com/index_all.html?type=" + URLEncoder.encode(comp, "UTF-8") + "&postid=" + URLEncoder.encode(num, "UTF-8") + "&callbackurl=http://sky.crs");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
