package com.yhcloud.thankyou.manage;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;

import com.yhcloud.thankyou.bean.MineFunctionBean;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.IMineView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public class MineManage {

    private IMineView mIMineView;
    private LogicService mService;
    private Fragment mFragment;
    private ArrayList<MineFunctionBean> mBeen;

    public MineManage(IMineView mineView) {
        this.mIMineView = mineView;
        this.mFragment = (Fragment) mineView;
        mIMineView.showLoading();
        Intent intent = new Intent(mFragment.getActivity(), LogicService.class);
        mFragment.getActivity().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
                mIMineView.initView();
                initFunction();
                mIMineView.hiddenLoading();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIMineView.hiddenLoading();
            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void initFunction() {
        mBeen = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            MineFunctionBean mineFunctionBean = new MineFunctionBean();
            mBeen.add(mineFunctionBean);
        }
        mIMineView.showList(mBeen);
    }
}
