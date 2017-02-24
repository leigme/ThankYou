package com.yhcloud.thankyou.module.classteachers.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.bean.TeacherBean;
import com.yhcloud.thankyou.minterface.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.module.classteachers.view.IClassTeacherListActivityView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 */

public class ClassTeacherListManage {

    private IClassTeacherListActivityView mIClassTeacherListView;
    private Activity mActivity;
    private LogicService mService;

    public ClassTeacherListManage(IClassTeacherListActivityView iClassTeacherListView) {
        this.mIClassTeacherListView = iClassTeacherListView;
        this.mActivity = (Activity) mIClassTeacherListView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIClassTeacherListView.initView();
                mIClassTeacherListView.initEvent();
                getClassTeacherList();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getClassTeacherList() {
        mService.getClassTeacherList(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonData = jsonObject.getString("data");
                        if (null != jsonData && !"".equals(jsonData)) {
                            Gson gson = new Gson();
                            ArrayList<TeacherBean> list = gson.fromJson(jsonData, new TypeToken<ArrayList<TeacherBean>>(){}.getType());
                            mIClassTeacherListView.showList(list);
                            mIClassTeacherListView.setTitle(MessageFormat.format("本班老师({0})", list.size()));
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
}
