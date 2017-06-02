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
import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.module.classnotification.bean.ClassNotificationBean;
import com.yhcloud.thankyou.module.classnotification.view.ClassNotificationDetailActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/6.
 */

public class ClassNotificationDetailManage {

    private String TAG = getClass().getSimpleName();

    private ClassNotificationDetailActivityView mIClassNotificationDetailView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<ClassNotificationBean> mBeen;
    private int record = 0, pageNum = 1, pageCount = -1;

    public ClassNotificationDetailManage(ClassNotificationDetailActivityView iClassNotificationDetailView) {
        this.mIClassNotificationDetailView = iClassNotificationDetailView;
        this.mActivity = (Activity) mIClassNotificationDetailView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
//                mService = ((LogicService.MyBinder)service).getService();
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
                showWeb(record);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void nextNotification() {
        //当前记录数等于总记录数，并且当前页数小于总页数，请求新数据
        record += 1;
        Tools.print(TAG, MessageFormat.format("班级通知记录数是:{0}，总记录数是:{1}，当前页码数是:{2}，总页码是:{3}", record, mBeen.size(), pageNum, pageCount));
        if (record < mBeen.size()) {
            showWeb(record);
        } else if (record == mBeen.size() && pageNum < pageCount) {
            pageNum += 1;
            getClassNotificationData(record);
        } else {
            mIClassNotificationDetailView.showToastMsg(R.string.no_more_data);
        }
    }

    public void getClassNotificationData(final int record) {
        mService.getClassNotificationData(pageNum, new ResponseCallBack<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        pageCount = jsonObject.getInt("pageCount");
                        String jsonReslut = jsonObject.getString("noticeList");
                        if (null != jsonReslut && !"".equals(jsonReslut) && !"[]".equals(jsonReslut)) {
                            Gson gson = new Gson();
                            ArrayList<ClassNotificationBean> list = gson.fromJson(jsonReslut, new TypeToken<ArrayList<ClassNotificationBean>>(){}.getType());
                            for (ClassNotificationBean cnb: list) {
                                if (!"2".equals(cnb.getIsRead())) {
                                    Tools.print(TAG, "添加一条记录");
                                    mBeen.add(cnb);
                                }
                            }
//                            mBeen.addAll(list);
                            Tools.print(TAG, "获得记录长度是:" + mBeen.size());
                            showWeb(record);
                            return;
                        }
                    }
                    mIClassNotificationDetailView.showToastMsg(R.string.error_connection);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callFailure() {
                mIClassNotificationDetailView.showToastMsg(R.string.error_connection);
            }
        });
    }

    public void showWeb(int record) {
        Tools.print(TAG, "请求的页面是:" + Constant.SERVICEADDRESS + mBeen.get(record).getUrl());
        mIClassNotificationDetailView.showWeb(Constant.SERVICEADDRESS + mBeen.get(record).getUrl());
        mBeen.get(record).setIsRead("1");
        updateClassNotificationReadState(record);
    }

    //更新阅读状态
    private void updateClassNotificationReadState(int position) {
        mService.updateClassNotificationReadState(mBeen.get(position).getNoticeId(), new ResponseCallBack<String>() {
            @Override
            public void callSuccess(String s) {

            }

            @Override
            public void callFailure() {

            }
        });
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
