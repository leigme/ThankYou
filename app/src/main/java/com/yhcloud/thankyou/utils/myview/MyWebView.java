package com.yhcloud.thankyou.utils.myview;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.yhcloud.thankyou.R;

/**
 * Created by cjm on 2016/6/23.
 */
public class MyWebView extends WebView {

    private ProgressBar mProgressBar;
    private ProgressDialog mProgressDialog;
    private Context mContext;

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext= context;
        initView();
    }

    private void initView() {
        mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5, 0, 0));
        addView(mProgressBar);
        setWebViewClient(new WebViewClient());
        setWebChromeClient(new WebChromeClient());
        //隐藏滚动条
        setVerticalScrollBarEnabled(false);
        //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件
        requestFocus();
        //获取手势焦点
        requestFocusFromTouch();
        //支持JavaScript
        getSettings().setJavaScriptEnabled(true);
        //支持访问文件
        getSettings().setAllowFileAccess(true);
        //将所有HTML放入webview组件中,固定宽度
        getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //支持缩放
//        getSettings().setBuiltInZoomControls(true);
        //支持自动加载图片
        getSettings().setLoadsImagesAutomatically(true);
        //支持自适应屏幕尺寸
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        //优先使用缓存:
        getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //不使用缓存:
//        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //设置默认编码:
        getSettings().setDefaultTextEncodingName("utf-8");
        //设置字体大小
//        getSettings().setTextZoom(140);
    }

    public class WebViewClient extends android.webkit.WebViewClient {
/*        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }*/

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showLoading(R.string.loading_data);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            hiddenLoading();
        }
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                if (mProgressBar.getVisibility() == GONE) {
                    mProgressBar.setVisibility(VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        }
    }

    private void showLoading(int msgId) {
        hiddenLoading();
        mProgressDialog = ProgressDialog.show(mContext, null, mContext.getString(msgId));
    }

    private void hiddenLoading() {
        if (null != mProgressDialog) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
