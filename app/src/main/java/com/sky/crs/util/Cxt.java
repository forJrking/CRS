package com.sky.crs.util;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by guoyufeng on 30/4/15.
 */
public class Cxt {

    private static Context _cxt;

    private static Resources _res;

    public static void init(Context context) {
        Cxt._cxt = context.getApplicationContext();
    }

    public static Context get() {
        return _cxt;
    }

    public static Resources getRes() {
        if (_res == null) {
            _res = _cxt.getResources();
        }
        return _res;
    }

    public static <T> T get_sys_service(String name) {
        return (T) _cxt.getSystemService(name);
    }

    public static String getStr(int resId) {
        return _cxt.getString(resId);
    }

    public static String getStr(int resId, Object... fmtArgs) {
        return _cxt.getString(resId, fmtArgs);
    }

    public static int getColor(int colorId) {
        return getRes().getColor(colorId);
    }
}
