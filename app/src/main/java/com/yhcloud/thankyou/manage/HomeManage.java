package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.view.IHomeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<SpreadBean> mSpreadBeen;

    public HomeManage(IHomeView homeView, LogicService service) {
        this.mIHomeView = homeView;
        this.mFragment = (Fragment) mIHomeView;
        this.mActivity = mFragment.getActivity();
        this.mService = service;
        mIHomeView.initView();
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
        showBanner("-1");
        showFunctionList();
        showSpreadList("-1");
    }

    public void showBanner(String updateTime) {
        mService.getImageUrls(updateTime, new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonList = jsonObject.getString("dataList");
                        if (null != jsonList && !"".equals(jsonList)) {
                            Gson gson = new Gson();
                            Type jsonType =new TypeToken<ArrayList<SpreadBean>>(){}.getType();
                            ArrayList<SpreadBean> been = gson.fromJson(jsonList, jsonType);
                            ArrayList<String> arrayList = new ArrayList<>();
                            for (SpreadBean sb: been) {
                                arrayList.add(sb.getSummaryPicLink());
                            }
                            mIHomeView.showBanner(arrayList);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void showSpreadList(String updateTime) {
        mService.getSpreadList(updateTime, new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonList = jsonObject.getString("dataList");
                        if (null != jsonList && !"".equals(jsonList)) {
                            Gson gson = new Gson();
                            Type jsonType = new TypeToken<ArrayList<SpreadBean>>(){}.getType();
                            mSpreadBeen = gson.fromJson(jsonList, jsonType);
                            mIHomeView.showSpread(mSpreadBeen);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callFailed() {

            }
        });
    }

    public void goSpreadInfo(int position) {
        Log.e(TAG, MessageFormat.format("点击了第{0}个推广链接", mSpreadBeen.get(position).getTitle()));
    }
}
