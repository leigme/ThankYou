package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.view.ILoginView;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LoginManage {

    private String TAG = getClass().getSimpleName();

    private ILoginView mILoginView;
    private Activity mActivity;
    private LogicService mService;
    private SharedPreferences mPreferences;

    public LoginManage(ILoginView loginView) {
        this.mILoginView = loginView;
        this.mActivity = (Activity) loginView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                mService = ((LogicService.MyBinder)binder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void login() {
        mILoginView.showDialog();
        mService.login(mILoginView.getUserName(), mILoginView.getPassWord(), new ICallListener() {
            @Override
            public void callSuccess(Object o) {
                //实现登录成功的回调,如:传递对象到下一个activity或者存储对象到数据库
                Log.e(TAG, "登录成功...");
                UserInfo userInfo = (UserInfo) o;
                mService.setUserInfo(userInfo);
                saveUserInfo(userInfo);
                mILoginView.pushMainActivity(userInfo.getClassInfoBeen());
                mILoginView.hideDialog();
                mILoginView.closeActivity();
            }

            @Override
            public void callFailed() {
                Log.e(TAG, "登录失败...");
                mILoginView.hideDialog();
            }
        });
    }

    public void saveUserInfo(UserInfo userInfo) {
        mPreferences = mActivity.getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        mPreferences.edit().putString(Constant.USER_NAME, userInfo.getUsername());
        mPreferences.edit().putString(Constant.USER_PWD, userInfo.getPassword());
        mPreferences.edit().putInt(Constant.USER_FLAG, userInfo.getUserInfoBean().getUserRoleId());
        mPreferences.edit().putString(Constant.USER_HXNAME, userInfo.getUserInfoBean().getHXUserName());
        mPreferences.edit().putString(Constant.USER_HXPWD, userInfo.getUserInfoBean().getHXPwd());
        mPreferences.edit().commit();
    }
}
