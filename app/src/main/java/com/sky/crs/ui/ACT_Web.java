package com.sky.crs.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sky.crs.R;
import com.sky.crs.listener.NavigationFinishClickListener;
import com.sky.crs.util.UIUtil;
import com.sky.crs.widget.WebViewProgressBar;

public class ACT_Web extends AppCompatActivity {

    private WebViewProgressBar mProgressBar;

    private static final String KEY_URL = "URL";
    private String mUrl;
    private Toolbar mToolbar;
    private ProgressBar mBar;
    private WebView mWebView;
    private View mEmpty;

    public static void launch(Context cxt, String url) {
        Intent intent = new Intent(cxt, ACT_Web.class);
        intent.putExtra(KEY_URL, url);
        cxt.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_web);
        mUrl = getIntent().getStringExtra(KEY_URL);

        initAppBar();
        initWebView();
        initWebSettings();
        initWebViewClient();
        initWebChromeClient();

        mWebView.loadUrl(mUrl);
    }

    private void initAppBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(R.string.loading);
        mToolbar.setNavigationOnClickListener(new NavigationFinishClickListener(this));
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.webview);
        mBar = (ProgressBar) findViewById(R.id.progress_bar);
        mEmpty = findViewById(R.id.empty);
        mProgressBar = new WebViewProgressBar(mBar);
    }

    private void initWebSettings() {
        WebSettings settings = mWebView.getSettings();
        //支持获取手势焦点
        mWebView.requestFocusFromTouch();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(false);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.supportMultipleWindows();
        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCachePath(mWebView.getContext().getCacheDir().getAbsolutePath());

        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");
    }

    private void initWebViewClient() {
        mWebView.setWebViewClient(new WebViewClient() {

            //页面开始加载时
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if ("http://sky.crs/".equals(url)) {
                    finish();
                    return;
                }
                mProgressBar.onProgressStart();
                mEmpty.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
            }

            //页面完成加载时
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            //是否在WebView内加载新页面
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //网络错误时回调的方法
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mEmpty.setVisibility(View.VISIBLE);
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
            }
        });
    }

    private void initWebChromeClient() {

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setToolbarTitle(title);
            }

            @Override
            public Bitmap getDefaultVideoPoster() {
                return super.getDefaultVideoPoster();
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mProgressBar != null)
                    mProgressBar.onProgressChange(newProgress);
            }
        });
    }

    /**
     * 设置Toolbar标题
     *
     * @param title
     */
    private void setToolbarTitle(final String title) {
        Log.d("setToolbarTitle", " WebDetailActivity " + title);
        if (mToolbar != null) {
            mToolbar.post(new Runnable() {
                @Override
                public void run() {
                    mToolbar.setTitle(TextUtils.isEmpty(title) ? getString(R.string.loading) : title);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UIUtil.safeDestroyWebView(mWebView);
    }

    @Override
    public void finish() {
        super.finish();
        if (mProgressBar != null) {
            mProgressBar.destroy();
            mProgressBar = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //如果按下的是回退键且历史记录里确实还有页面
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
