package com.yhcloud.thankyou.module.homework.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yhcloud.thankyou.module.homework.view.IAddHomeworkActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

/**
 * Created by Administrator on 2017/1/20.
 */

public class AddHomeworkManage {

    private String TAG = getClass().getSimpleName();

    private IAddHomeworkActivityView mIAddHomeworkView;
    private Activity mActivity;
    private LogicService mService;

    public AddHomeworkManage(IAddHomeworkActivityView iAddHomeworkView) {
        this.mIAddHomeworkView = iAddHomeworkView;
        this.mActivity = (Activity) iAddHomeworkView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void closePage() {
        mActivity.finish();
    }

    public void save() {
        Tools.print(TAG, "保存作业");
    }
}
