package com.yhcloud.thankyou.module.classnotification.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.mInterfacea.ICallListener;
import com.yhcloud.thankyou.module.classnotification.bean.ClassNotificationBean;
import com.yhcloud.thankyou.module.classnotification.view.ClassNotificationDetailActivity;
import com.yhcloud.thankyou.module.classnotification.view.IClassNotificationActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/6.
 */

public class ClassNotificationManage {

    private String TAG = getClass().getSimpleName();

    private IClassNotificationActivityView mIClassNotificationView;
    private Activity mActivity;
    private LogicService mService;
    private UserInfo mUserInfo;
    private int pageNum = 1, pageCount = -1;
    private ArrayList<ClassNotificationBean> mBeen;
    private boolean refreshing;

    public ClassNotificationManage(IClassNotificationActivityView iClassNotificationView) {
        this.mIClassNotificationView = iClassNotificationView;
        this.mActivity = (Activity) mIClassNotificationView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mBeen = new ArrayList<>();
                mUserInfo = mService.getUserInfo();
                mIClassNotificationView.initView();
                mIClassNotificationView.initEvent();
                mIClassNotificationView.setTitle("班级通知");
                switch (mUserInfo.getUserInfoBean().getUserRoleId()) {
                    case 1004:
                        showRightMenu();
                        break;
                    case 1010:
                        showRightMenu();
                        break;
                    case 1011:
                        break;
                    case 1012:
                        break;
                }
                getClassNotificationData(pageNum);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void showRightMenu() {
        mIClassNotificationView.showRightMenu();
    }

    public void goAddClassNotification() {
        Tools.print(TAG, "去添加的页面。。。");
    }

    public void goClassNotificationDetail(int position) {
        Intent intent = new Intent(mActivity, ClassNotificationDetailActivity.class);
        intent.putExtra("record", position);
        intent.putExtra("pageCount", pageCount);
        intent.putExtra("pageNum", pageNum - 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", mBeen);
        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, 101);
    }

    //获取数据
    public void getClassNotificationData(int page) {
        mIClassNotificationView.showLoading(R.string.loading_data);
        mService.getClassNotificationData(page, new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        pageCount = jsonObject.getInt("pageCount");
                        if (0 < pageCount) {
                            String jsonResult = jsonObject.getString("noticeList");
                            if (null != jsonResult && !"".equals(jsonResult) && !"[]".equals(jsonResult)) {
                                if (1 == pageNum) {
                                    mBeen = new ArrayList<>();
                                }
                                Gson gson = new Gson();
                                ArrayList<ClassNotificationBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<ClassNotificationBean>>(){}.getType());
                                for (ClassNotificationBean cnb: list) {
                                    if (!"2".equals(cnb.getIsRead())) {
                                        mBeen.add(cnb);
                                    }
                                }
                                mIClassNotificationView.showList(mBeen);
                                pageNum += 1;
                            }
                        } else {
                            mIClassNotificationView.showToastMsg(R.string.no_more_data);
                        }
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mIClassNotificationView.showToastMsg(msg);
                        } else {
                            mIClassNotificationView.showToastMsg(R.string.error_connection);
                        }
                    }
                    if (refreshing) {
                        mIClassNotificationView.completeRefreshList();
                        refreshing = false;
                    }
                    mIClassNotificationView.hiddenLoading();
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (refreshing) {
                        mIClassNotificationView.completeRefreshList();
                        refreshing = false;
                    }
                    mIClassNotificationView.hiddenLoading();
                }
            }

            @Override
            public void callFailed() {
                if (refreshing) {
                    mIClassNotificationView.completeRefreshList();
                    refreshing = false;
                }
                mIClassNotificationView.hiddenLoading();
                mIClassNotificationView.showToastMsg(R.string.error_connection);
            }
        });
    }

    //自动加载更多数据
    public void getMoreData() {
        if (-1 != pageCount) {
            if (pageNum > pageCount) {
                mIClassNotificationView.showToastMsg(R.string.no_more_data);
            } else {
                if (!refreshing) {

                    Tools.print(TAG, "又加载更多。。。");
                    getClassNotificationData(pageNum);
                }
            }
        }
    }

    //刷新列表显示
    public void refreshData(ArrayList<ClassNotificationBean> list) {
        this.mBeen = list;
        mIClassNotificationView.showList(mBeen);
    }

    //下拉刷新
    public void refreshList() {
        refreshing = true;
        Tools.print(TAG, "开始下拉刷新。。。");
        pageNum = 1;
        getClassNotificationData(pageNum);
    }
}
