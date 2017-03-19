package com.sky.crs.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dt on 13/9/14.
 */
public class UIUtil {

    private UIUtil() {
    }

    public static void toastShort(int messageResId) {
        toastShort(Cxt.getStr(messageResId));
    }

    public static void toastShort(String message) {
        toast(message, Toast.LENGTH_SHORT);
    }

    public static void toastLong(int messageResId) {
        toastLong(Cxt.getStr(messageResId));
    }

    public static void toastLong(String message) {
        toast(message, Toast.LENGTH_LONG);
    }

    private static void toast(String message, int length) {
        Toast.makeText(Cxt.get(), message, length).show();
    }

    public static int dp2px(float dpValue) {
        return (int) (getMetrics().density * dpValue + 0.5f);
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    private static DisplayMetrics displayMetrics;

    private static DisplayMetrics getMetrics() {
        if (displayMetrics == null) {
            WindowManager wm = (WindowManager) Cxt.get().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);
            displayMetrics = metrics;
        }
        return displayMetrics;
    }

    /**
     * @return screen width and height
     */
    public static int[] getScreenWidthAndHeight() {
        DisplayMetrics metrics = getMetrics();
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    public static boolean setViewInvisible(boolean invisible, View... views) {
        int visibility = invisible ? View.INVISIBLE : View.VISIBLE;
        for (View view : views) {
            if (view != null) {
                view.setVisibility(visibility);
            }
        }
        return invisible;
    }

    public static boolean setViewGone(boolean gone, View... views) {
        int visibility = gone ? View.GONE : View.VISIBLE;
        for (View view : views) {
            if (view != null) {
                view.setVisibility(visibility);
            }
        }
        return gone;
    }

    public static boolean setViewVisibleOrGone(boolean visibleCondition, View... views) {
        int visibility = visibleCondition ? View.VISIBLE : View.GONE;
        for (View view : views) {
            if (view != null) {
                view.setVisibility(visibility);
            }
        }
        return visibleCondition;
    }

    public static void showSoftKeyboard(View view) {
        InputMethodManager mgr = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager mgr = (InputMethodManager) Cxt.get().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void safeDestroyWebView(WebView webView) {
        try {
            webView.removeAllViews();
            webView.clearCache(false);
            webView.destroyDrawingCache();
            webView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float getStatusBarHeight(Activity activity, boolean inOnCreate) {
        if (inOnCreate) {
            int result = 0;
            int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = activity.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        } else {
            Rect rect = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            return rect.top;
        }
    }

    public static void setTextIfNotEmpty(TextView textView, CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            textView.setText(charSequence);
        }
    }

    public static void setChildText(View parentView, int textViewId, CharSequence charSequence) {
        ((TextView) parentView.findViewById(textViewId)).setText(charSequence);
    }

    public static boolean isVisible(View view) {
        if (view != null) {
            return view.getVisibility() == View.VISIBLE;
        } else {
            return false;
        }
    }

    public static void setEnable(boolean enable, View... views) {
        for (View view : views) {
            if (view != null) {
                view.setEnabled(enable);
            }
        }
    }

    /**
     * 单项选择
     *
     * @param selectedView 选中的view
     * @param views
     */
    public static void setSingleSelected(View selectedView, View... views) {
        selectedView.setSelected(true);
        for (View view : views) {
            if (view != null) {
                view.setSelected(false);
            }
        }
    }
}
