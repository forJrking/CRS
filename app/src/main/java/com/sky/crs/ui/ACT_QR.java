package com.sky.crs.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sky.crs.R;
import com.sky.crs.listener.NavigationFinishClickListener;

import yunji.qrcode.core.LightSensorManager;
import yunji.qrcode.core.QRCodeView;

/**
 * @创建者 froJrking
 * @创建时间 2017/3/16 11:10
 * @描述 ${二维码 条形码封装} 需要清单文件注册
 * @公司 浙江云集科技
 * <权限>
 * <uses-permission android:name="android.permission.CAMERA" />
 * <uses-permission android:name="android.permission.VIBRATE" />
 * <uses-permission android:name="android.permission.FLASHLIGHT" />
 * 如果需要相册识别
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 */

public class ACT_QR extends AppCompatActivity implements QRCodeView.Delegate, View.OnClickListener {

    private static final String TAG = ACT_QR.class.getSimpleName();

    private static final String KEY_VIB = "VIB";

    private static final String KEY_STYLE = "STYLE";

    private static final String KEY_LIGHT = "LIGHT";

    public static final String RESULT = "DATA";
    //请求
    public static final int REQUEST_CODE = 0x000123;
    //  显示二维码样式
    private boolean isQR;
    private boolean vib;
    private boolean autoFlash; //自动
    boolean isLight = false;

    private QRCodeView mQRCodeView;
    private Button mBtnLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_qr);
        isQR = getIntent().getBooleanExtra(KEY_STYLE, true);
        vib = getIntent().getBooleanExtra(KEY_VIB, false);
        autoFlash = getIntent().getBooleanExtra(KEY_LIGHT, false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
        mQRCodeView = (QRCodeView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
        mBtnLight = (Button) findViewById(R.id.btn_flashlight);
        mBtnLight.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        if (autoFlash)
            LightSensorManager.getInstance().start(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开始扫描
        if (!isQR) mQRCodeView.changeToScanBarcodeStyle();
        mQRCodeView.startSpot();
        //自动
        if (autoFlash) {
            mQRCodeView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    float lux = LightSensorManager.getInstance().getLux();
                    if (lux < 20) {
                        mQRCodeView.openFlashlight();
                        mBtnLight.setBackgroundResource(R.drawable.light_on);
                        isLight = true;
                    }

                }
            }, 1500);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mQRCodeView.stopSpot();
        if (autoFlash || isLight) {
            mQRCodeView.closeFlashlight();
            mBtnLight.setBackgroundResource(R.drawable.light_off);
        }
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        if (autoFlash)
            LightSensorManager.getInstance().stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    public static void launch(Activity cxt) {
        launch(cxt, true, false, false);
    }

    public static void launch(Activity cxt, boolean isQR_Style) {
        launch(cxt, isQR_Style, false, false);
    }

    /**
     * REQUEST_CODE 0x000123
     *
     * @param isQR_Style 二维码还是条码
     * @param needVib    需要扫描完成震动
     * @param flashLight 需要自动开启闪关灯（这里是利用传感器）
     */
    public static void launch(Activity cxt, boolean isQR_Style, boolean needVib, boolean flashLight) {
        Intent intent = new Intent(cxt, ACT_QR.class);
        intent.putExtra(KEY_STYLE, isQR_Style);
        intent.putExtra(KEY_VIB, needVib);
        intent.putExtra(KEY_LIGHT, flashLight);
        cxt.startActivityForResult(intent, REQUEST_CODE);
    }


    public void returnResultOK(String result) {
        Intent intent = new Intent();
        intent.putExtra(RESULT, result);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void vibrate() {
        if (!vib) return;
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        returnResultOK(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


    @Override
    public void onClick(View v) {
        if (isLight) {
            mQRCodeView.closeFlashlight();
            mBtnLight.setBackgroundResource(R.drawable.light_off);
        } else {
            mQRCodeView.openFlashlight();
            mBtnLight.setBackgroundResource(R.drawable.light_on);
        }
        isLight = !isLight;
    }
}
