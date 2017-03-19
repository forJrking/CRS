package com.sky.crs;

/*
 * @创建者     Jrking
 * @创建时间   2017/3/13 1:45
 * @描述	      ${TODO}
 *
 * @更新者     $Author
 * @更新时间   $Date
 * @更新描述   ${TODO}
 */

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.sky.crs.util.Cxt;

public class AppBase extends Application {

    static AppBase cxt;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate() {
        super.onCreate();
        Cxt.init(this);
        cxt = this;
        OkGo.init(this);
        OkGo.getInstance().debug("OKGo")
                .setConnectTimeout(5*1000)
                .setCacheMode(CacheMode.DEFAULT)
                .setCookieStore(new MemoryCookieStore());
    }

    public static AppBase getInstance() {
        return cxt;
    }

    public void post(Runnable run) {
        handler.post(run);
    }

    public void postDelay(Runnable run, long delay) {
        handler.postDelayed(run, delay);
    }
}
