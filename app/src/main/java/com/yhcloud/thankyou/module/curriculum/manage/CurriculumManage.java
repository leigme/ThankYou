package com.yhcloud.thankyou.module.curriculum.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.module.curriculum.bean.CurriculumDataBean;
import com.yhcloud.thankyou.module.curriculum.bean.CurriculumItemBean;
import com.yhcloud.thankyou.module.curriculum.view.ICurriculumView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */

public class CurriculumManage {

    private String TAG = getClass().getSimpleName();

    private ICurriculumView mICurriculumView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<CurriculumItemBean> mBeen;

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
                            CurriculumItemBean curriculumItemBean;
                            Gson gson = new Gson();
                            ArrayList<CurriculumDataBean> list = gson.fromJson(jsonList, new TypeToken<ArrayList<CurriculumDataBean>>(){}.getType());
                            List<CurriculumDataBean.CouresBean> couresBeanArrayList;
                            CurriculumDataBean.DayIndexBean dayIndexBean;
                            //处理表格头部
                            for (int j = 0; j < 6; j++) {
                                curriculumItemBean = new CurriculumItemBean();
                                curriculumItemBean.setType(1);
                                switch (j) {
                                    case 1:
                                        curriculumItemBean.setTitle("星期一");
                                        break;
                                    case 2:
                                        curriculumItemBean.setTitle("星期二");
                                        break;
                                    case 3:
                                        curriculumItemBean.setTitle("星期三");
                                        break;
                                    case 4:
                                        curriculumItemBean.setTitle("星期四");
                                        break;
                                    case 5:
                                        curriculumItemBean.setTitle("星期五");
                                        break;
                                    default:
                                        curriculumItemBean.setTitle("");
                                        break;
                                }
                                mBeen.add(curriculumItemBean);
                            }
                            for (int i = 0; i < list.size(); i++) {
                                switch (i) {
                                    //处理午休数据
                                    case 5:
                                        curriculumItemBean = new CurriculumItemBean();
                                        curriculumItemBean.setTitle("午 休");
                                        curriculumItemBean.setType(2);
                                        mBeen.add(curriculumItemBean);
                                        break;
                                    //处理其他列显示数据
                                    default:
                                        dayIndexBean = list.get(i).getDayIndex();
                                        curriculumItemBean = new CurriculumItemBean();
                                        curriculumItemBean.setStartTime(dayIndexBean.getStartTime());
                                        curriculumItemBean.setTitle(dayIndexBean.getName());
                                        curriculumItemBean.setEndTime(dayIndexBean.getEndTime());
                                        mBeen.add(curriculumItemBean);
                                        couresBeanArrayList = list.get(i).getCoures();
                                        couresBeanArrayList = couresBeanArrayList.subList(0, 5);
                                        for (CurriculumDataBean.CouresBean couresBean: couresBeanArrayList) {
                                            curriculumItemBean = new CurriculumItemBean();
                                            curriculumItemBean.setTitle(couresBean.getName());
                                            mBeen.add(curriculumItemBean);
                                        }
                                        break;
                                }
                            }
                            Tools.print(TAG, "课程长度是:" + mBeen.size());
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
