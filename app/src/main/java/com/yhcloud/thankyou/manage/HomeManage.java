package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.view.IHomeView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/20.
 */

public class HomeManage {

    private String TAG = getClass().getSimpleName();
    private IHomeView mIHomeView;
    private Activity mActivity;
    private Fragment mFragment;
    private LogicService mService;
    private ArrayList<FunctionBean> mBeen;

    public HomeManage(IHomeView homeView, LogicService service) {
        this.mIHomeView = homeView;
        this.mFragment = (Fragment) mIHomeView;
        this.mActivity = mFragment.getActivity();
        this.mService = service;
        mBeen = new ArrayList<>();
        switch (mService.getUserInfo().getUserInfoBean().getUserRoleId()) {
            case 1004:
                break;
            case 1010:
                initTeacher();
                break;
            case 1011:
                initStudent();
                break;
            case 1012:
                initParent();
                break;
            default:
                break;
        }
        mIHomeView.initView();
        showBanner("-1");
        showFunctionList();
    }

    public void showBanner(String updateTime) {
        mService.getImageUrls(updateTime, new ICallListener<ArrayList<SpreadBean>>() {
            @Override
            public void callSuccess(ArrayList<SpreadBean> been) {
                ArrayList<String> arrayList = new ArrayList<>();
                for (SpreadBean sb: been) {
                    arrayList.add(sb.getSummaryPicLink());
                }
                mIHomeView.showBanner(arrayList);
            }

            @Override
            public void callFailed() {

            }
        });
    }

    public void initTeacher() {
        ArrayList<FunctionBean> list = Tools.initFunction(mActivity);
        for (FunctionBean functionBean: list) {
            switch (functionBean.getTitle()) {
                case "学校公告":
                    mBeen.add(functionBean);
                    break;
                case "班级通知":
                    mBeen.add(functionBean);
                    break;
                case "教学资源":
                    mBeen.add(functionBean);
                    break;
                case "工作日程":
                    mBeen.add(functionBean);
                    break;
                case "我的消息":
                    mBeen.add(functionBean);
                    break;
                case "课后作业":
                    mBeen.add(functionBean);
                    break;
                case "鲜花榜":
                    mBeen.add(functionBean);
                    break;
            }
        }
        mBeen.add(list.get(0));
    }

    public void initStudent() {
        ArrayList<FunctionBean> list = Tools.initFunction(mActivity);
        for (FunctionBean functionBean: list) {
            switch (functionBean.getTitle()) {
                case "学校公告":
                    mBeen.add(functionBean);
                    break;
                case "班级通知":
                    mBeen.add(functionBean);
                    break;
                case "教学资源":
                    mBeen.add(functionBean);
                    break;
                case "工作日程":
                    mBeen.add(functionBean);
                    break;
                case "我的消息":
                    mBeen.add(functionBean);
                    break;
                case "课后作业":
                    mBeen.add(functionBean);
                    break;
                case "鲜花榜":
                    mBeen.add(functionBean);
                    break;
            }
        }
        mBeen.add(list.get(0));
    }

    public void initParent() {
        ArrayList<FunctionBean> list = Tools.initFunction(mActivity);
        for (FunctionBean functionBean: list) {
            switch (functionBean.getTitle()) {
                case "学校公告":
                    mBeen.add(functionBean);
                    break;
                case "班级通知":
                    mBeen.add(functionBean);
                    break;
                case "教学资源":
                    mBeen.add(functionBean);
                    break;
                case "工作日程":
                    mBeen.add(functionBean);
                    break;
                case "我的消息":
                    mBeen.add(functionBean);
                    break;
                case "课后作业":
                    mBeen.add(functionBean);
                    break;
                case "鲜花榜":
                    mBeen.add(functionBean);
                    break;
            }
        }
        mBeen.add(list.get(0));
    }

    public void showFunctionList() {
        mIHomeView.showFunction(mBeen);
    }
}
