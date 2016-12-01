package com.yhcloud.thankyou.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.logic.HomeLogic;
import com.yhcloud.thankyou.logic.IHomeLogic;
import com.yhcloud.thankyou.logic.ILoginLogic;
import com.yhcloud.thankyou.logic.LoginLogic;
import com.yhcloud.thankyou.mInterface.ICallListener;

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
    public void login(String username, String password, ICallListener iCallListener) {
        ILoginLogic loginLogic = new LoginLogic();
        loginLogic.login(username, password, iCallListener);
    }

    //获取轮播图
    public void getImageUrls(String updateTime, ICallListener iCallListener) {
        IHomeLogic homeLogic = new HomeLogic();
        homeLogic.getImageUrls(updateTime, iCallListener);
    }
}
