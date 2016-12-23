package com.yhcloud.thankyou.module.curriculum.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.module.curriculum.bean.CouresBean;
import com.yhcloud.thankyou.module.curriculum.bean.CurriculumBean;
import com.yhcloud.thankyou.module.curriculum.bean.CurriculumListBean;
import com.yhcloud.thankyou.module.curriculum.bean.DayIndexBean;
import com.yhcloud.thankyou.module.curriculum.view.ICurriculumView;
import com.yhcloud.thankyou.service.LogicService;


import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/14.
 */

public class CurriculumManage {

    private String TAG = getClass().getSimpleName();

    private ICurriculumView mICurriculumView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<CurriculumBean> mBeen;

    public CurriculumManage(ICurriculumView iCurriculumView) {
        this.mICurriculumView = iCurriculumView;
        this.mActivity = (Activity) mICurriculumView;
        mICurriculumView.showLoading(R.string.loading_data);
        mBeen = new ArrayList<>();
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mICurriculumView.initView();
                mICurriculumView.initEvent();
                mICurriculumView.setTitle("班级课表");
                getClassCurriculum();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mICurriculumView.hiddenLoading();
            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getClassCurriculum() {
        mService.getClassCurriculum(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonList = jsonObject.getString("list");
                        if (null != jsonList && !"".equals(jsonList)) {
                            CurriculumBean curriculumBean;
                            for (int i = 0; i < 6; i++) {
                                curriculumBean = new CurriculumBean();
                                curriculumBean.setHeader(true);
                                switch (i) {
                                    case 0:
                                        curriculumBean.setDayTitle("");
                                        break;
                                    case 1:
                                        curriculumBean.setDayTitle("星期一");
                                        break;
                                    case 2:
                                        curriculumBean.setDayTitle("星期二");
                                        break;
                                    case 3:
                                        curriculumBean.setDayTitle("星期三");
                                        break;
                                    case 4:
                                        curriculumBean.setDayTitle("星期四");
                                        break;
                                    case 5:
                                        curriculumBean.setDayTitle("星期五");
                                        break;
                                }
                                mBeen.add(curriculumBean);
                            }
                            Gson gson = new Gson();
                            Type type = new TypeToken<ArrayList<CurriculumListBean>>() {}.getType();
                            ArrayList<CurriculumListBean> list = gson.fromJson(jsonList, type);
                            for (CurriculumListBean curriculumListBean: list) {
                                DayIndexBean dayIndexBean = curriculumListBean.getDayIndex();
                                curriculumBean = new CurriculumBean();
                                curriculumBean.setStartTime(dayIndexBean.getStartTime());
                                curriculumBean.setTitle(dayIndexBean.getName());
                                curriculumBean.setEndTime(dayIndexBean.getEndTime());
                                mBeen.add(curriculumBean);
                                ArrayList<CouresBean> couresBeen = curriculumListBean.getCoures();
                                for (int i = 0; i < 5; i++) {
                                    CouresBean couresBean = couresBeen.get(i);
                                    curriculumBean = new CurriculumBean();
                                    curriculumBean.setStartTime("");
                                    curriculumBean.setTitle(couresBean.getName());
                                    curriculumBean.setDayTitle(couresBean.getWeekdayname());
                                    curriculumBean.setEndTime("");
                                    mBeen.add(curriculumBean);
                                }
                            }
                            mBeen.get(36).setTitle("");
                            mBeen.get(38).setTitle("午");
                            mBeen.get(39).setTitle("休");
                            mICurriculumView.showList(mBeen);
                            mICurriculumView.hiddenLoading();
                            return;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mICurriculumView.initViewStub("本班没有排课");
                mICurriculumView.hiddenLoading();
            }

            @Override
            public void callFailed() {
                mICurriculumView.initViewStub("本班没有排课");
                mICurriculumView.hiddenLoading();
            }
        });
    }
}
