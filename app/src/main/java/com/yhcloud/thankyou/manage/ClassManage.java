package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;

import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.IClassView;

/**
 * Created by Administrator on 2016/12/12.
 */

public class ClassManage {
    private IClassView mIClassView;
    private Fragment mFragment;
    private Activity mActivity;
    private LogicService mService;

    public ClassManage(IClassView iClassView, LogicService service) {
        this.mIClassView = iClassView;
        this.mFragment = (Fragment) mIClassView;
        this.mActivity = mFragment.getActivity();
        this.mService = service;
    }

}
