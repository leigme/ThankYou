package com.yhcloud.thankyou.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;

import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.logic.HomeLogic;
import com.yhcloud.thankyou.logic.IHomeLogic;
import com.yhcloud.thankyou.logic.ILoginLogic;
import com.yhcloud.thankyou.logic.LoginLogic;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.utils.Constant;

import java.util.ArrayList;

public class LogicService extends Service {

    private MyBinder mBinder = new MyBinder();

    private UserInfo mUserInfo;


    public LogicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        public LogicService getService() {
            return LogicService.this;
        }
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
    }

    //登录
    public void login(String username, String password, ICallListener<String> iCallListener) {
        ILoginLogic loginLogic = new LoginLogic();
        loginLogic.login(username, password, iCallListener);
    }

    //存储用户登录信息
    public void saveUserInfo(UserInfo userInfo) {
        SharedPreferences mPreferences = this.getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Constant.USER_NAME, userInfo.getUsername());
        editor.putString(Constant.USER_PWD, userInfo.getPassword());
        editor.putInt(Constant.USER_FLAG, userInfo.getUserInfoBean().getUserRoleId());
        editor.putString(Constant.USER_HXNAME, userInfo.getUserInfoBean().getHXUserName());
        editor.putString(Constant.USER_HXPWD, userInfo.getUserInfoBean().getHXPwd());
        editor.commit();
    }
    //清除用户登录信息

    //获取轮播图
    public void getImageUrls(String updateTime, ICallListener<ArrayList<SpreadBean>> iCallListener) {
        IHomeLogic homeLogic = new HomeLogic();
        homeLogic.getImageUrls(updateTime, iCallListener);
    }


}
