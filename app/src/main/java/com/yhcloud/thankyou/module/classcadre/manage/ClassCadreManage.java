package com.yhcloud.thankyou.module.classcadre.manage;

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
import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.module.classcadre.view.IClassCadreActivityView;
import com.yhcloud.thankyou.service.LogicService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 */

public class ClassCadreManage {

    private IClassCadreActivityView mIClassCadreView;
    private Activity mActivity;
    private LogicService mService;

    public ClassCadreManage(IClassCadreActivityView iClassCadreView) {
        this.mIClassCadreView = iClassCadreView;
        this.mActivity = (Activity) mIClassCadreView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIClassCadreView.initView();
                mIClassCadreView.initEvent();
                mIClassCadreView.setTitle("班干部");
                getClassCadreList();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void getClassCadreList() {
        mService.getClassCadreList(new ICallBackListener<String>() {
            @Override
            public void callSuccess(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String jsonData = jsonObject.getString("data");
                        if (null != jsonData && !"".equals(jsonData)) {
                            mIClassCadreView.showDefault(false);
                            Gson gson = new Gson();
                            ArrayList<TeacherBean> list = gson.fromJson(jsonData, new TypeToken<ArrayList<TeacherBean>>(){}.getType());
                            mIClassCadreView.showList(list);
                        } else {
                            String msg = jsonObject.getString("errorMsg");
                            if (null != msg && !"".equals(msg)) {
                                mIClassCadreView.showToastMsg(msg);
                            } else {
                                mIClassCadreView.showToastMsg(R.string.error_connection);
                            }
                            mIClassCadreView.showDefault(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mIClassCadreView.showDefault(true);
                }
            }

            @Override
            public void callFailure() {
                mIClassCadreView.showDefault(true);
            }
        });
    }
}
