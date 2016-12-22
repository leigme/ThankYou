package com.yhcloud.thankyou.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.logic.ClassLogic;
import com.yhcloud.thankyou.logic.DetailPeopleLogic;
import com.yhcloud.thankyou.logic.HomeLogic;
import com.yhcloud.thankyou.logic.IClassLogic;
import com.yhcloud.thankyou.logic.IDetailPeopleLogic;
import com.yhcloud.thankyou.logic.IHomeLogic;
import com.yhcloud.thankyou.logic.ILoginLogic;
import com.yhcloud.thankyou.logic.LoginLogic;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.utils.Constant;

import java.util.ArrayList;

public class LogicService extends Service {

    private String TAG = getClass().getSimpleName();

    private MyBinder mBinder = new MyBinder();
    private UserInfo mUserInfo;
    private ArrayList<FunctionBean> mBeen;

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

    public ArrayList<FunctionBean> getBeen() {
        return mBeen;
    }

    public void setBeen(ArrayList<FunctionBean> been) {
        mBeen = been;
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
        editor.putBoolean(Constant.USER_LOGINED, true);
        editor.commit();
    }

    //退出登录
    public void loginOut() {
        SharedPreferences mPreferences = this.getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(Constant.USER_LOGINED, false);
        editor.commit();
    }

    //清除用户登录信息
    public void cleanUserInfo() {
        SharedPreferences mPreferences = this.getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.commit();
    }

    //获取轮播图
    public void getImageUrls(String updateTime, ICallListener<ArrayList<SpreadBean>> iCallListener) {
        IHomeLogic homeLogic = new HomeLogic();
        homeLogic.getImageUrls(updateTime, iCallListener);
    }

    //获取班级用户列表
    public void getClassPeopleList(String classId, String updateTime, ICallListener<String> iCallListener) {
        IClassLogic classLogic = new ClassLogic();
        classLogic.getClassPeopleListForService(mUserInfo.getUserInfoBean().getUserId(), classId, updateTime, iCallListener);
    }

    //获取用户详情
    public void getDetailInfo(String userId, String uId, ICallListener<String> iCallListener) {
        IDetailPeopleLogic detailPeopleLogic = new DetailPeopleLogic();
        detailPeopleLogic.getDetailInfo(userId, uId, iCallListener);
    }
}
