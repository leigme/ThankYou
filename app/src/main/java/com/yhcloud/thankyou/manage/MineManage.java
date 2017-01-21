package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.view.IMineView;
import com.yhcloud.thankyou.view.LoginActivity;

import java.util.ArrayList;

/**
 * Created by leig on 2016/12/4.
 */

public class MineManage {

    private String TAG = getClass().getSimpleName();

    private IMineView mIMineView;
    private Fragment mFragment;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<FunctionBean> mBeen;
    private int RESULT_MESSAGE = 101;
    private boolean logouting = false;

    public MineManage(IMineView mineView, LogicService service) {
        this.mIMineView = mineView;
        this.mFragment = (Fragment) mIMineView;
        this.mActivity = mFragment.getActivity();
        this.mService = service;
        mBeen = new ArrayList<>();
        mIMineView.initView();
        initMineFunction();
    }

    public void initMineFunction() {
        SparseArray<FunctionBean> list = Tools.initFunction(mActivity);
        mBeen.add(list.get(1));
        mBeen.add(list.get(2));
        mBeen.add(list.get(20));
        mBeen.add(list.get(4));
        mBeen.add(list.get(5));
        mBeen.add(list.get(6));
        mBeen.add(list.get(7));
        mBeen.add(list.get(8));
        mBeen.add(list.get(9));
        mBeen.add(2, new FunctionBean());
        mBeen.add(4, new FunctionBean());
        mBeen.add(9, new FunctionBean());
        mIMineView.showList(mBeen);
    }

    public void goFunction(int i) {
        FunctionBean fb = mBeen.get(i);
        if (9 == fb.getId()) {
            if (!logouting) {
                logout();
            }
        } else {
            if (null != fb.getIntent()) {
                mActivity.startActivity(fb.getIntent());
            }
        }
    }

    public void logout() {
        mIMineView.showLoading(R.string.logouting);
        logouting = true;
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Log.e(TAG, "退出环信成功");
                Intent goLogin = new Intent(mActivity, LoginActivity.class);
                mActivity.startActivity(goLogin);
                mIMineView.hiddenLoading();
                mActivity.finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
                Log.e(TAG, "退出环信失败");
                logouting = false;
                mIMineView.hiddenLoading();
                mIMineView.showToastMsg("登出失败");
            }
        });
    }
}
