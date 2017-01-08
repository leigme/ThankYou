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
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.module.classnotification.bean.ClassNotificationBean;
import com.yhcloud.thankyou.module.classnotification.view.ClassNotificationDetailActivity;
import com.yhcloud.thankyou.module.classnotification.view.IClassNotificationView;
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

    private IClassNotificationView mIClassNotificationView;
    private Activity mActivity;
    private LogicService mService;
    private UserInfo mUserInfo;
    private int pageNum = 1, pageCount = -1;
    private ArrayList<ClassNotificationBean> mBeen;

    public ClassNotificationManage(IClassNotificationView iClassNotificationView) {
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
        updateClassNotificationReadState(position);
        Intent intent = new Intent(mActivity, ClassNotificationDetailActivity.class);
        intent.putExtra("record", position);
        intent.putExtra("pageCount", pageCount);
        intent.putExtra("pageNum", pageNum - 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", mBeen);
        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, 101);
    }

    private void updateClassNotificationReadState(int position) {
        mService.updateClassNotificationReadState(mBeen.get(position).getNoticeId(), new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {

            }

            @Override
            public void callFailed() {

            }
        });
    }

    public void getClassNotificationData(int page) {
        mService.getClassNotificationData(page, new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        pageCount = jsonObject.getInt("PageCount");
                        String jsonResult = jsonObject.getString("noticeList");
                        if (null != jsonResult && !"".equals(jsonResult) && !"[]".equals(jsonResult)) {
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

    public void getMoreData() {
        if (-1 != pageCount) {
            if (pageNum > pageCount) {
                mIClassNotificationView.showToastMsg(R.string.no_more_data);
            } else {
                Tools.print(TAG, "加载更多。。。");
                getClassNotificationData(pageNum);
            }
        }
    }

    public void refreshData(ArrayList<ClassNotificationBean> list) {
        this.mBeen = list;
        mIClassNotificationView.showList(mBeen);
    }
}
