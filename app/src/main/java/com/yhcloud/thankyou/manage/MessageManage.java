package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.IMessageView;

/**
 * Created by Administrator on 2016/12/20.
 */

public class MessageManage {

    private IMessageView mIMessageView;
    private Activity mActivity;
    private LogicService mService;

    public MessageManage(IMessageView iMessageView) {
        this.mIMessageView = iMessageView;
        this.mActivity = (Activity) mIMessageView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIMessageView.initMessageFragment();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }
}
