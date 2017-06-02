package com.yhcloud.thankyou.module.schoolannouncement.manage;

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
import com.yhcloud.thankyou.module.schoolannouncement.bean.SchoolAnnouncementBean;
import com.yhcloud.thankyou.module.schoolannouncement.view.SchoolAnnouncementActivityView;
import com.yhcloud.thankyou.module.schoolannouncement.view.SchoolAnnouncementDetailActivity;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/8.
 */

public class SchoolAnnouncementManage {

    private String TAG = getClass().getSimpleName();

    private SchoolAnnouncementActivityView mISchoolAnnouncementView;
    private Activity mActivity;
    private LogicService mService;
    private int pageNum = 1, pageCount = -1;
    private ArrayList<SchoolAnnouncementBean> mBeen;
    private boolean refreshing;

    public SchoolAnnouncementManage(SchoolAnnouncementActivityView iSchoolAnnouncementView) {
        this.mISchoolAnnouncementView = iSchoolAnnouncementView;
        this.mActivity = (Activity) mISchoolAnnouncementView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
//                mService = ((LogicService.MyBinder)service).getService();
                mBeen = new ArrayList<>();
                mISchoolAnnouncementView.initView();
                mISchoolAnnouncementView.initEvent();
                mISchoolAnnouncementView.setTitle("学校公告");
                getSchoolAnnouncementData(pageNum);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getSchoolAnnouncementData(int page) {
        mISchoolAnnouncementView.showLoading(R.string.loading_data);
        mService.getSchoolAnnouncementData(page, new ResponseCallBack<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        pageCount = jsonObject.getInt("page");
                        if (0 < pageCount) {
                            String jsonResult = jsonObject.getString("list");
                            if (null != jsonResult && !"".equals(jsonResult) && !"[]".equals(jsonResult)) {
                                if (1 == pageNum) {
                                    mBeen = new ArrayList<>();
                                }
                                Gson gson = new Gson();
                                ArrayList<SchoolAnnouncementBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<SchoolAnnouncementBean>>(){}.getType());
                                mBeen.addAll(list);
                                mISchoolAnnouncementView.showList(mBeen);
                                pageNum += 1;
                            }
                        } else {
                            mISchoolAnnouncementView.showToastMsg(R.string.no_more_data);
                        }
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mISchoolAnnouncementView.showToastMsg(msg);
                        } else {
                            mISchoolAnnouncementView.showToastMsg(R.string.error_connection);
                        }
                    }
                    mISchoolAnnouncementView.completeRefreshList();
                    mISchoolAnnouncementView.hiddenLoading();
                } catch (JSONException e) {
                    e.printStackTrace();
                    mISchoolAnnouncementView.completeRefreshList();
                    mISchoolAnnouncementView.hiddenLoading();
                }
            }

            @Override
            public void callFailure() {
                mISchoolAnnouncementView.completeRefreshList();
                mISchoolAnnouncementView.hiddenLoading();
                mISchoolAnnouncementView.showToastMsg(R.string.error_connection);
            }
        });
    }

    public void goSchoolAnnouncementDetail(int position) {
        Intent intent = new Intent(mActivity, SchoolAnnouncementDetailActivity.class);
        intent.putExtra("record", position);
        intent.putExtra("pageCount", pageCount);
        intent.putExtra("pageNum", pageNum - 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", mBeen);
        intent.putExtras(bundle);
        mActivity.startActivityForResult(intent, 101);
    }

    public void getMoreData() {
        if (-1 != pageCount) {
            if (pageNum > pageCount) {
                mISchoolAnnouncementView.showToastMsg(R.string.no_more_data);
            } else {
                if (!refreshing) {
                    Tools.print(TAG, "加载更多。。。");
                    getSchoolAnnouncementData(pageNum);
                }
            }
        }
    }

    public void refreshData() {
        refreshing = true;
        pageNum = 1;
        getSchoolAnnouncementData(pageNum);
    }
}
