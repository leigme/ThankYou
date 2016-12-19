package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.yhcloud.thankyou.R;
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
    private Fragment mFragment;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<FunctionBean> mBeen;
    private int RESULT_MESSAGE = 101;

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
        ArrayList<FunctionBean> arrayList = Tools.initFunction(mActivity);
        for (FunctionBean functionBean: arrayList) {
            switch (functionBean.getTitle()) {
                case "我的资料":
                    mBeen.add(functionBean);
                    break;
                case "我的朋友":
                    mBeen.add(functionBean);
                    break;
                case "我的鲜花":
                    mBeen.add(functionBean);
                    break;
                case "我的消息":
                    mBeen.add(functionBean);
                    break;
                case "我的账户":
                    mBeen.add(functionBean);
                    break;
                case "每日签到":
                    mBeen.add(functionBean);
                    break;
                case "我的下载":
                    mBeen.add(functionBean);
                    break;
                case "关于我们":
                    mBeen.add(functionBean);
                    break;
                case "切换账户":
                    mBeen.add(functionBean);
                    break;
            }
        }
        mBeen.add(0, new FunctionBean());
        mBeen.add(2, new FunctionBean());
        mBeen.add(4, new FunctionBean());
        mBeen.add(10, new FunctionBean());
        mIMineView.showList(mBeen);
    }

    public void goFunction(int i) {
        if ("我的消息".equals(mBeen.get(i).getTitle())) {
            mActivity.startActivityForResult(mBeen.get(i).getIntent(), RESULT_MESSAGE);
        }
    }
}
