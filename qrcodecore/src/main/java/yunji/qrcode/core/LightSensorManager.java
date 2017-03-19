package yunji.qrcode.core;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * @创建者 froJrking
 * @创建时间 2017/3/16 13:20
 * @描述 ${光感器 管理类}
 * @公司 浙江云集科技
 */

public class LightSensorManager {

    private static final boolean DEBUG = true;
    private static final String TAG = "LightSensor";

    private static LightSensorManager instance;
    private SensorManager mSensorManager;
    private LightSensorListener mLightSensorListener;
    private boolean mHasStarted = false;

    private LightSensorManager() {
    }

    public static LightSensorManager getInstance() {
        if (instance == null) {
            instance = new LightSensorManager();
        }
        return instance;
    }

    public void start(Context context) {
        if (mHasStarted) {
            return;
        }
        mHasStarted = true;
        mSensorManager = (SensorManager) context.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); // 获取光线传感器
        if (lightSensor != null) { // 光线传感器存在时
            mLightSensorListener = new LightSensorListener();
            mSensorManager.registerListener(mLightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL); // 注册事件监听
        }
    }

    public void stop() {
        if (!mHasStarted || mSensorManager == null) {
            return;
        }
        mHasStarted = false;
        mSensorManager.unregisterListener(mLightSensorListener);
    }

    /**
     * 获取光线强度
     */
    public float getLux() {
        if (mLightSensorListener != null) {
            return mLightSensorListener.lux;
        }
        return -1.0f; // 默认返回-1，表示设备无光线传感器或者为调用start()方法
    }

    private class LightSensorListener implements SensorEventListener {

        private float lux; // 光线强度

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                // 获取光线强度
                lux = event.values[0];
                if (DEBUG) {
                    Log.d(TAG, "lux : " + lux);
                }
            }
        }

    }
}
