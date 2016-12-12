package com.yhcloud.thankyou.manage;

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
    private LogicService mService;

    public ClassManage(IClassView iClassView) {
        this.mIClassView = iClassView;
        this.mFragment = (Fragment) mIClassView;
        Intent intent = new Intent(mFragment.getActivity(), LogicService.class);
        mFragment.getActivity().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }
}
