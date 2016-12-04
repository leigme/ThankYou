package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.logic.IMainLogic;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.IMainView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/19.
 */

public class MainManage {

    private String TAG = getClass().getSimpleName();
    private IMainLogic mIMainLogic;
    private IMainView mIMainView;
    private Activity mActivity;
    private LogicService mService;
    private UserInfo mUserInfo;


    public MainManage(IMainView mainView) {
        this.mIMainView = mainView;
        this.mActivity = (Activity) mainView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
                mIMainView.initView();
                mIMainView.initData();
                mIMainView.initEvent();
                mIMainView.showFragment(0);
                mUserInfo = mService.getUserInfo();
                mIMainView.setHeaderLeftImage(mUserInfo.getUserInfoBean().getHeadImageURL());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getClassInfo(String userId) {
        mIMainLogic.getClassInfoList("3237", new CallListener());
    }

    public class CallListener implements ICallListener<ArrayList<ClassInfoBean>> {

        @Override
        public void callSuccess(ArrayList<ClassInfoBean> infos) {
            mIMainView.showDrawer(infos);
        }

        @Override
        public void callFailed() {

        }
    }
}
