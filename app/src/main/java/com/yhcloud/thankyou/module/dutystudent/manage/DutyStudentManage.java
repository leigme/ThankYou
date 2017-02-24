package com.yhcloud.thankyou.module.dutystudent.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.TeacherBean;
import com.yhcloud.thankyou.minterface.ICallListener;
import com.yhcloud.thankyou.module.dutystudent.view.IDutyStudentActivityView;
import com.yhcloud.thankyou.service.LogicService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 */

public class DutyStudentManage {

    private IDutyStudentActivityView mIDutyStudentView;
    private Activity mActivity;
    private LogicService mService;

    public DutyStudentManage(IDutyStudentActivityView iDutyStudentView) {
        this.mIDutyStudentView = iDutyStudentView;
        this.mActivity = (Activity) mIDutyStudentView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIDutyStudentView.initView();
                mIDutyStudentView.initEvent();
                mIDutyStudentView.setTitle("值日生");
                getDutyStudentList();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getDutyStudentList() {
        mService.getDutyStudentList(new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonData = jsonObject.getString("data");
                        if (null != jsonData && !"".equals(jsonData)) {
                            mIDutyStudentView.showDefault(false);
                            Gson gson = new Gson();
                            ArrayList<TeacherBean> list = gson.fromJson(jsonData, new TypeToken<ArrayList<TeacherBean>>(){}.getType());
                            mIDutyStudentView.showList(list);
                        }
                    } else {
                        String msg = jsonObject.getString("errorMsg");
                        if (null != msg && !"".equals(msg)) {
                            mIDutyStudentView.showToastMsg(msg);
                        } else {
                            mIDutyStudentView.showToastMsg(R.string.error_connection);
                        }
                        mIDutyStudentView.showDefault(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mIDutyStudentView.showDefault(true);
                }
            }

            @Override
            public void callFailed() {
                mIDutyStudentView.showToastMsg(R.string.error_connection);
                mIDutyStudentView.showDefault(true);
            }
        });
    }
}
