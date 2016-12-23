package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.IWebView;

/**
 * Created by Administrator on 2016/12/23.
 */

public class WebManage {

    private IWebView mIWebView;
    private Activity mActivity;
    private LogicService mService;

    public WebManage(IWebView iWebView) {
        this.mIWebView = iWebView;
        this.mActivity = (Activity) mIWebView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIWebView.initView();
                mIWebView.initEvent();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }
}