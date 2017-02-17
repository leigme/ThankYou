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
import com.yhcloud.thankyou.utils.Tools;

import java.text.MessageFormat;
import java.util.ArrayList;

import static com.huawei.android.pushagent.plugin.tools.BLocation.TAG;

/**
 * Created by Administrator on 2017/1/10.
 */

public class AllFuncationManage {

    private IAllFuncationActivityView mIAllFuncationView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<FunctionBean> mAddFunctionBeen, mNoneFunctionBeen;
    private boolean edited;

    public AllFuncationManage(IAllFuncationActivityView iAllFuncationView) {
        this.mIAllFuncationView = iAllFuncationView;
        this.mActivity = (Activity) mIAllFuncationView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mAddFunctionBeen = mService.getAddFunctionBeen();
                mNoneFunctionBeen = mService.getNoneFunctionBeen();
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
            mIAllFuncationView.setEditMode(edited);
            saveFunctionList();
        } else {
            mIAllFuncationView.setRightTitle("编辑");
            this.edited = false;
            mIAllFuncationView.setEditMode(edited);
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
        Tools.print(TAG, MessageFormat.format("保存的数据长度是:{0},未添加的数据长度是:{1}", mAddFunctionBeen.size(), mNoneFunctionBeen.size()));
        mIAllFuncationView.showAddList(mAddFunctionBeen);
        mIAllFuncationView.showNoneList(mNoneFunctionBeen);
    }

    public void saveFunctionList() {
        Tools.print(TAG, "保存程序集合视图");
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (FunctionBean fb: mAddFunctionBeen) {
            if (0 != fb.getId()) {
                arrayList.add(fb.getId());
            }
        }
        mService.saveFuncations(arrayList);
    }

    public void goAddFunction(int position) {
        FunctionBean functionBean = mAddFunctionBeen.get(position);
        if (null != functionBean.getIntent()) {
            if (null != functionBean.getIntent()) {
                switch (functionBean.getId()) {
                    case 0:
//                        mActivity.startActivityForResult(functionBean.getIntent(), Constant.ALLFUNCATION_REQUEST);
                        break;
                    case 4:
                        if (mService.isCanMessage()) {
                            mActivity.startActivity(functionBean.getIntent());
                        } else {
                            mIAllFuncationView.showToastMsg("好友数据初始化中,请稍候...");
                        }
                        break;
                    default:
                        mActivity.startActivity(functionBean.getIntent());
                        break;
                }
            }
        }
    }

    public void goNoneFunction(int position) {
        FunctionBean functionBean = mNoneFunctionBeen.get(position);
        if (null != functionBean.getIntent()) {
            if (null != functionBean.getIntent()) {
                switch (functionBean.getId()) {
                    case 0:
//                        mActivity.startActivityForResult(functionBean.getIntent(), Constant.ALLFUNCATION_REQUEST);
                        break;
                    case 4:
                        if (mService.isCanMessage()) {
                            mActivity.startActivity(functionBean.getIntent());
                        } else {
                            mIAllFuncationView.showToastMsg("好友数据初始化中,请稍候...");
                        }
                        break;
                    default:
                        mActivity.startActivity(functionBean.getIntent());
                        break;
                }
            }
        }
    }

    public void editFunctionList(int code, int position) {
        switch (code) {
            case 0:
                if (7 > mAddFunctionBeen.size()) {
                    FunctionBean noneFB = mNoneFunctionBeen.get(position);
                    mNoneFunctionBeen.remove(position);
                    mAddFunctionBeen.add(noneFB);
                    mIAllFuncationView.showAddList(mAddFunctionBeen);
                    mIAllFuncationView.showNoneList(mNoneFunctionBeen);
                } else {
                    mIAllFuncationView.showToastMsg("已经添加满咯~~");
                }
                break;
            case 1:
                FunctionBean addNB = mAddFunctionBeen.get(position);
                mAddFunctionBeen.remove(position);
                mNoneFunctionBeen.add(addNB);
                mIAllFuncationView.showAddList(mAddFunctionBeen);
                mIAllFuncationView.showNoneList(mNoneFunctionBeen);
                break;
            default:
                break;
        }
    }
}
