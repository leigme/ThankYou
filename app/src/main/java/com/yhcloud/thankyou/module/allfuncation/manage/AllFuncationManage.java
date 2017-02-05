package com.yhcloud.thankyou.module.allfuncation.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.module.allfuncation.view.IAllFuncationActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

import static com.huawei.android.pushagent.plugin.tools.BLocation.TAG;

/**
 * Created by Administrator on 2017/1/10.
 */

public class AllFuncationManage {

    private IAllFuncationActivityView mIAllFuncationView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<FunctionBean> mBeen;
    private boolean edited;

    public AllFuncationManage(IAllFuncationActivityView iAllFuncationView) {
        this.mIAllFuncationView = iAllFuncationView;
        this.mActivity = (Activity) mIAllFuncationView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIAllFuncationView.initView();
                mIAllFuncationView.initEvent();
                mIAllFuncationView.setTitle("全部应用");
                mIAllFuncationView.setRightTitle("编辑");
                initAllFuncation();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void edit() {
        if (!edited) {
            mIAllFuncationView.setRightTitle("保存");
            this.edited = true;
        } else {
            mIAllFuncationView.setRightTitle("编辑");
            this.edited = false;
        }
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public void closePage() {
        mActivity.finish();
    }

    public void initAllFuncation() {
        if (null == mBeen) {
            mBeen = mService.getBeen();
        }
        for (FunctionBean fb: mBeen) {
            if (0 == fb.getId()) {
                mBeen.remove(fb);
            }
        }
        mIAllFuncationView.showList(mBeen);
    }

    public void saveFunctionList() {
        Tools.print(TAG, "保存程序集合视图");
        mService.saveFuncations();
    }

    public void goFunction(int position) {
        FunctionBean functionBean = mBeen.get(position);
        if (null != functionBean.getIntent()) {
            if (0 == functionBean.getId()) {
                mActivity.startActivityForResult(functionBean.getIntent(), Constant.ALLFUNCATION_REQUEST);
            } else if (4 == functionBean.getId()){
                if (mService.isCanMessage()) {
                    mActivity.startActivity(functionBean.getIntent());
                }
            } else {
                mActivity.startActivity(functionBean.getIntent());
            }
        }
    }
}
