package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.WebActivityView;

/**
 * Created by Administrator on 2016/12/23.
 */

public class WebManage {

    private WebActivityView mIWebView;
    private Activity mActivity;
    private LogicService mService;
    private String title, url;

    public WebManage(WebActivityView iWebView) {
        this.mIWebView = iWebView;
        this.mActivity = (Activity) mIWebView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIWebView.initView();
                mIWebView.initEvent();
                Intent comeIntent = mActivity.getIntent();
                if (null != comeIntent) {
                    title = comeIntent.getStringExtra("Title");
                    if (null != title && !"".equals(title)) {
                        mIWebView.setTitle(title);
                    }
                    url = comeIntent.getStringExtra("Url");
                    if (null != url && !"".equals(url)) {
                        mIWebView.showWeb(url);
                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }
}
