package com.yhcloud.thankyou.manage;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.view.IMineView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public class MineManage {

    private IMineView mIMineView;
    private LogicService mService;
    private Fragment mFragment;
    private ArrayList<FunctionBean> mBeen;

    public MineManage(IMineView mineView) {
        this.mIMineView = mineView;
        this.mFragment = (Fragment) mineView;
        mIMineView.showLoading();
        Intent intent = new Intent(mFragment.getActivity(), LogicService.class);
        mFragment.getActivity().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
                mBeen = new ArrayList<>();
                mIMineView.initView();
                initMineFunction();
                mIMineView.hiddenLoading();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mIMineView.hiddenLoading();
            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void initMineFunction() {
        SparseArray<FunctionBean> sparseArray = Tools.initFunction();
        for (int i = 0; i < sparseArray.size(); i++) {
            FunctionBean functionBean = new FunctionBean();
            switch (i) {
                case 0:
                    mBeen.add(0, new FunctionBean());
                    break;
                case 2:
                    mBeen.add(new FunctionBean());
                    break;
                case 4:
                    mBeen.add(new FunctionBean());
                    break;

            }
            mBeen.add(sparseArray.get(i));
        }
        mIMineView.showList(mBeen);
    }
}
