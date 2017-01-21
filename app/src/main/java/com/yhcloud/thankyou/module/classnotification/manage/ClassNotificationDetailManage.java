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
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.module.classnotification.bean.ClassNotificationBean;
import com.yhcloud.thankyou.module.classnotification.view.IClassNotificationDetailActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/6.
 */

public class ClassNotificationDetailManage {

    private String TAG = getClass().getSimpleName();

    private IClassNotificationDetailActivityView mIClassNotificationDetailView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<ClassNotificationBean> mBeen;
    private int record = 0, pageNum = 1, pageCount = -1;

    public ClassNotificationDetailManage(IClassNotificationDetailActivityView iClassNotificationDetailView) {
        this.mIClassNotificationDetailView = iClassNotificationDetailView;
        this.mActivity = (Activity) mIClassNotificationDetailView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIClassNotificationDetailView.initView();
                mIClassNotificationDetailView.initEvent();
                mIClassNotificationDetailView.setTitle("通知详情");
                mIClassNotificationDetailView.setRightTitle("下一条");
                Intent classNotificationList = mActivity.getIntent();
                record = classNotificationList.getIntExtra("record", 0);
                pageCount = classNotificationList.getIntExtra("pageCount", -1);
                pageNum = classNotificationList.getIntExtra("pageNum", 1);
                mBeen = (ArrayList<ClassNotificationBean>) classNotificationList.getSerializableExtra("list");
                Tools.print(TAG, mBeen.get(record).getUrl());
                showWeb();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void nextNotification() {
        if (null != mBeen && 0 != mBeen.size()) {
            Tools.print(TAG, "当前页码是:" + pageNum + "   总页码是:" + pageCount);
            record += 1;
            if (record < mBeen.size()) {
                showWeb();
            } else {
                pageNum += 1;
                Tools.print(TAG, "当前页码是:" + pageNum + "   总页码是:" + pageCount);
                if (pageNum < pageCount) {
                    getClassNotificationData();
                } else {
                    mIClassNotificationDetailView.showToastMsg(R.string.no_more_data);
                }
            }
        }
    }

    public void getClassNotificationData() {
        mService.getClassNotificationData(pageNum, new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        pageCount = jsonObject.getInt("PageCount");
                        String jsonReslut = jsonObject.getString("noticeList");
                        if (null != jsonReslut && !"".equals(jsonReslut) && !"[]".equals(jsonReslut)) {
                            Gson gson = new Gson();
                            ArrayList<ClassNotificationBean> list = gson.fromJson(jsonReslut, new TypeToken<ArrayList<ClassNotificationBean>>(){}.getType());
                            for (ClassNotificationBean cnb: list) {
                                if (!"2".equals(cnb.getIsRead())) {
                                    mBeen.add(cnb);
                                }
                            }
//                            mBeen.addAll(list);
                            showWeb();
                            return;
                        }
                    }
                    mIClassNotificationDetailView.showToastMsg(R.string.error_connection);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callFailed() {
                mIClassNotificationDetailView.showToastMsg(R.string.error_connection);
            }
        });
    }

    public void showWeb() {
        Tools.print(TAG, "请求的页面是:" + mBeen.get(record).getUrl());
        mIClassNotificationDetailView.showWeb(mBeen.get(record).getUrl());
        mBeen.get(record).setIsRead("1");
    }

    public void closePage() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", mBeen);
        intent.putExtras(bundle);
        mActivity.setResult(Activity.RESULT_OK, intent);
        mActivity.finish();
    }
}
