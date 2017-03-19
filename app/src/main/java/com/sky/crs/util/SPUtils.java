package com.sky.crs.util;


import android.content.Context;
import android.content.SharedPreferences;


public class SPUtils {

    private static boolean defavalues = false;

    private static SharedPreferences sp;

    private static SharedPreferences getSharedPreferences(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("crs", 0);
        }

        return sp;
    }

    /**
     * @param context
     * @param key
     * @param value   boolean 标记
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = getSharedPreferences(context);

        sp.edit().putBoolean(key, value).apply();

    }

    /**
     * @param context
     * @param key
     * @param defavalue 默认值 false
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defavalue) {
        SharedPreferences sp = getSharedPreferences(context);

        return sp.getBoolean(key, defavalue);

    }

    public static boolean getBoolean(Context context, String key) {

        return getBoolean(context, key, defavalues);

    }

    /**
     * @param context
     * @param key
     * @return
     * @Description 保存字符串
     * @author Jrking
     */

    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = getSharedPreferences(context);

        sp.edit().putString(key, value).apply();

    }

    public static String getString(Context context, String key, String defavalue) {
        SharedPreferences sp = getSharedPreferences(context);

        return sp.getString(key, defavalue);

    }

    public static String getString(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);

        return sp.getString(key, "");

    }

    public static void putInt(Context context, String key, int value) {

        SharedPreferences sp = getSharedPreferences(context);

        sp.edit().putInt(key, value).apply();

    }

    public static int getInt(Context context, String key, int defavalue) {
        SharedPreferences sp = getSharedPreferences(context);

        return sp.getInt(key, defavalue);

    }


    public static boolean remove(String KEY) {
        SharedPreferences sp = getSharedPreferences(Cxt.get());
        return sp.edit().remove(KEY).commit();

    }

}
