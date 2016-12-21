package com.yhcloud.thankyou.manage;

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
import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.view.ILoginView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LoginManage {

    private String TAG = getClass().getSimpleName();

    private ILoginView mILoginView;
    private Activity mActivity;
    private LogicService mService;
    private String username, password;
    private UserInfo userInfo;

    public LoginManage(ILoginView loginView) {
        this.mILoginView = loginView;
        this.mActivity = (Activity) loginView;
        SharedPreferences mPreferences = mActivity.getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        username = mPreferences.getString(Constant.USER_NAME, "");
        password = mPreferences.getString(Constant.USER_PWD, "");
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
                mILoginView.initView();
                if (!"".equals(username) && !"".equals(password)) {
                    mILoginView.initData(username, password);
                }
                mILoginView.initEvent();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void login() {
        mILoginView.showLoading(R.string.logging);
        mService.login(mILoginView.getUserName(), mILoginView.getPassWord(), new ICallListener<String>() {
            @Override
            public void callSuccess(String s) {
                userInfo = new UserInfo();
                userInfo.setUsername(mILoginView.getUserName());
                userInfo.setPassword(mILoginView.getPassWord());
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (!jsonObject.getBoolean("errorFlag")) {
                        String key = jsonObject.getString("key");
                        if (null != key && !"".equals(key)) {
                            userInfo.setKey(key);
                        }
                        String jsonUserInfo = jsonObject.getString("userinfo");
                        String jsonClassInfos = jsonObject.getString("classlist");
                        if (null != jsonUserInfo && !"".equals(jsonUserInfo) && null != jsonClassInfos && !"".equals(jsonClassInfos)) {
                            Gson gson = new Gson();
                            UserInfoBean userInfoBean = gson.fromJson(jsonUserInfo, UserInfoBean.class);
                            if (null != userInfoBean) {
                                userInfo.setUserInfoBean(userInfoBean);
                            }
                            ArrayList<ClassInfoBean> classInfoBeen = gson.fromJson(jsonClassInfos, new TypeToken<ArrayList<ClassInfoBean>>(){}.getType());
                            if (null != classInfoBeen) {
                                userInfo.setClassInfoBeen(classInfoBeen);
                            }
                        }
                        mService.setUserInfo(userInfo);
                        mService.saveUserInfo(userInfo);
                        mILoginView.pushMainActivity(userInfo.getClassInfoBeen());
                        mILoginView.closeActivity();
                    } else {
                        mILoginView.showMsg(R.string.error_login);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mILoginView.showMsg(R.string.error_connection);
                }
                mILoginView.hiddenLoading();
            }
            @Override
            public void callFailed() {
                mILoginView.hiddenLoading();
                mILoginView.showMsg(R.string.error_connection);
            }
        });
    }
}
