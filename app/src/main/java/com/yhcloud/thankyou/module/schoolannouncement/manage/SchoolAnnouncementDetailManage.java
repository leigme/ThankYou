package com.yhcloud.thankyou.module.schoolannouncement.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.module.schoolannouncement.bean.SchoolAnnouncementBean;
import com.yhcloud.thankyou.module.schoolannouncement.view.SchoolAnnouncementDetailActivityView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/8.
 */

public class SchoolAnnouncementDetailManage {

    private String TAG = getClass().getSimpleName();

    private SchoolAnnouncementDetailActivityView mISchoolAnnouncementDetailView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<SchoolAnnouncementBean> mBeen;
    private int record, pageNum = 1, pageCount = -1;

    public SchoolAnnouncementDetailManage(SchoolAnnouncementDetailActivityView iSchoolAnnouncementDetailView) {
        this.mISchoolAnnouncementDetailView = iSchoolAnnouncementDetailView;
        this.mActivity = (Activity) mISchoolAnnouncementDetailView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
//                mService = ((LogicService.MyBinder)service).getService();
                mBeen = new ArrayList<>();
                mISchoolAnnouncementDetailView.initView();
                mISchoolAnnouncementDetailView.initEvent();
                mISchoolAnnouncementDetailView.setTitle("公告详情");
                mISchoolAnnouncementDetailView.setRightTitle("下一条");
                if (null != mActivity.getIntent()) {
                    Intent schoolAnnouncementListIntent = mActivity.getIntent();
                    record = schoolAnnouncementListIntent.getIntExtra("record", 0);
                    pageNum = schoolAnnouncementListIntent.getIntExtra("pageNum", 1);
                    pageCount = schoolAnnouncementListIntent.getIntExtra("pageCount", -1);
                    mBeen = (ArrayList<SchoolAnnouncementBean>) schoolAnnouncementListIntent.getSerializableExtra("list");
                    showWeb();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    private void showWeb() {
        mISchoolAnnouncementDetailView.showWeb(mBeen.get(record).getContentURL());
    }

    public void nextAnnouncement() {
        if (null != mBeen && 0 != mBeen.size()) {
            Tools.print(TAG, "当前页码是:" + pageNum + "   总页码是:" + pageCount);
            record += 1;
            if (record < mBeen.size()) {
                showWeb();
            } else {
                pageNum += 1;
                Tools.print(TAG, "当前页码是:" + pageNum + "   总页码是:" + pageCount);
                if (pageNum < pageCount) {
                    getSchoolAnnouncementData();
                } else {
                    mISchoolAnnouncementDetailView.showToastMsg(R.string.no_more_data);
                }
            }
        }
    }

    private void getSchoolAnnouncementData() {
        mService.getSchoolAnnouncementData(pageNum, new ResponseCallBack<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        pageCount = jsonObject.getInt("page");
                        if (-1 != pageCount || 0 != pageCount) {
                            String jsonResult = jsonObject.getString("list");
                            if (null != jsonResult && !"".equals(jsonResult) && !"[]".equals(jsonResult)) {
                                Gson gson = new Gson();
                                ArrayList<SchoolAnnouncementBean> list = gson.fromJson(jsonResult, new TypeToken<ArrayList<SchoolAnnouncementBean>>(){}.getType());
                                mBeen.addAll(list);
                                showWeb();
                                pageNum += 1;
                                return;
                            }
                        }
                    }
                    mISchoolAnnouncementDetailView.showToastMsg(R.string.error_connection);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void callFailure() {
                mISchoolAnnouncementDetailView.showToastMsg(R.string.error_connection);
            }
        });
    }
}
