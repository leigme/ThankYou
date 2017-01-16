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

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.logic.IMainLogic;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.view.ClassFragment;
import com.yhcloud.thankyou.view.HomeFragment;
import com.yhcloud.thankyou.view.IClassView;
import com.yhcloud.thankyou.view.IMainView;
import com.yhcloud.thankyou.view.MineFragment;

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
    private SparseArray<FunctionBean> mSparseArray;
    private ArrayList<Fragment> mFragments;
    private ArrayList<ClassInfoBean> mClassInfoBeen;
    private ArrayList<FunctionBean> mMenuBeen;

    public MainManage(IMainView mainView) {
        this.mIMainView = mainView;
        this.mActivity = (Activity) mainView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
                mUserInfo = mService.getUserInfo();
                mSparseArray = Tools.initFunction(mActivity);
                mService.setBeanSparseArray(mSparseArray);
                mService.getUserAllFuncation();
                mIMainView.initView();
                mIMainView.initData();
                mIMainView.initEvent();
                initViewPages();
                mIMainView.showFragment(0);
                mIMainView.setHeaderLeftImage(mUserInfo.getUserInfoBean().getHeadImageURL());
                mIMainView.setTitle(mUserInfo.getUserInfoBean().getSchoolName());
                mIMainView.setDrawerHeadImg(mUserInfo.getUserInfoBean().getHeadImageURL());
                mIMainView.setDrawerUsername(mUserInfo.getUserInfoBean().getRealName());
                if (null != mActivity.getIntent()) {
                    Bundle bundle = mActivity.getIntent().getExtras();
                    mClassInfoBeen = (ArrayList<ClassInfoBean>) bundle.getSerializable("ClassInfos");
                    for (ClassInfoBean classInfoBean: mClassInfoBeen) {
                        if (classInfoBean.getClassId().equals(mUserInfo.getUserInfoBean().getDefaultClassId())) {
                            classInfoBean.setSelected(true);
                            mIMainView.setDrawerClassname(classInfoBean.getClassName());
                        }
                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void initViewPages() {
        mFragments = new ArrayList<>();
        HomeFragment homeFragment = HomeFragment.newInstance(mService);
        mFragments.add(homeFragment);
        ClassFragment classFragment = ClassFragment.newInstance(mService);
        mFragments.add(classFragment);
        MineFragment mineFragment = MineFragment.newInstance(mService);
        mFragments.add(mineFragment);
        mIMainView.initFragments(mFragments);
    }

    public void setTitle(int i) {
        switch (i) {
            case 0:
                mIMainView.setTitle(mUserInfo.getUserInfoBean().getSchoolName());
                break;
            case 1:
                if (null != mClassInfoBeen) {
                    for (ClassInfoBean classInfoBean: mClassInfoBeen) {
                        if (classInfoBean.getClassId().equals(mUserInfo.getUserInfoBean().getDefaultClassId())) {
                            mIMainView.setTitle(classInfoBean.getClassName());
                        }
                    }
                }
                break;
            case 2:
                mIMainView.setTitle("我的");
                break;
        }
    }

    public void showDrawer() {
        if (null != mClassInfoBeen) {
            mIMainView.showDrawer(mClassInfoBeen);
        }
    }

    public void setDefaultClassId(String classId) {
        mUserInfo.getUserInfoBean().setDefaultClassId(classId);
        for (ClassInfoBean classInfoBean: mClassInfoBeen) {
            if (classInfoBean.getClassId().equals(mUserInfo.getUserInfoBean().getDefaultClassId())) {
                mIMainView.setDrawerClassname(classInfoBean.getClassName());
            }
        }
    }

    public void setRightButton(boolean showed) {
        mIMainView.initHeaderRightButton(showed);
        if (showed) {
            if (null == mMenuBeen || 0 == mMenuBeen.size()) {
                mMenuBeen = new ArrayList<>();
                mMenuBeen.add(mSparseArray.get(16));
                mMenuBeen.add(mSparseArray.get(17));
                mMenuBeen.add(mSparseArray.get(18));
            }
        }
    }

    public void setClassPeopleList(String classId) {
        IClassView iClassView = (IClassView) mFragments.get(1);
        iClassView.getClassManage().getClassPeopleList(classId);
    }

    public void showTrm() {
        mIMainView.showTrm(mMenuBeen);
    }

    public void refreshFuncations(ArrayList<Integer> list) {
        HomeFragment homeFragment = (HomeFragment) mFragments.get(0);
        ArrayList<FunctionBean> arrayList = new ArrayList<>();
        for (int i: list) {
            arrayList.add(mSparseArray.get(i));
        }
        homeFragment.showFunction(arrayList);
    }
}
