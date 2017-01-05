package com.yhcloud.thankyou.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.SparseArray;

import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.logic.ClassLogic;
import com.yhcloud.thankyou.module.aboutus.logic.AboutUsLogic;
import com.yhcloud.thankyou.module.classcadre.logic.ClassCadreLogic;
import com.yhcloud.thankyou.module.classteachers.logic.ClassTeacherListLogic;
import com.yhcloud.thankyou.module.detailinfo.logic.DetailPeopleLogic;
import com.yhcloud.thankyou.logic.HomeLogic;
import com.yhcloud.thankyou.logic.IClassLogic;
import com.yhcloud.thankyou.module.classteachers.logic.IClassTeacherListLogic;
import com.yhcloud.thankyou.module.detailinfo.logic.IDetailPeopleLogic;
import com.yhcloud.thankyou.logic.IHomeLogic;
import com.yhcloud.thankyou.logic.ILoginLogic;
import com.yhcloud.thankyou.logic.LoginLogic;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.module.curriculum.logic.CurriculumLogic;
import com.yhcloud.thankyou.module.dutystudent.logic.DutyStudentLogic;
import com.yhcloud.thankyou.module.propslist.logic.PropsListLogic;
import com.yhcloud.thankyou.utils.Constant;

import java.util.ArrayList;

public class LogicService extends Service {

    private String TAG = getClass().getSimpleName();

    private MyBinder mBinder = new MyBinder();
    private UserInfo mUserInfo;
    private SparseArray<FunctionBean> mBeen;

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

    public SparseArray<FunctionBean> getBeen() {
        return mBeen;
    }

    public void setBeen(SparseArray<FunctionBean> been) {
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
    public void getImageUrls(String updateTime, ICallListener<String> iCallListener) {
        String flag = "";
        switch (mUserInfo.getUserInfoBean().getUserRoleId()) {
            case 1004:
                flag = "2";
                break;
            case 1010:
                flag = "2";
                break;
            case 1011:
                flag = "1";
                break;
            case 1012:
                flag = "4";
                break;
        }
        IHomeLogic homeLogic = new HomeLogic();
        homeLogic.getSpreadList("16", flag, updateTime, iCallListener);
    }

    //获取推广列表
    public void getSpreadList(String updateTime, ICallListener<String> iCallListener) {
        String flag = "";
        switch (mUserInfo.getUserInfoBean().getUserRoleId()) {
            case 1004:
                flag = "2";
                break;
            case 1010:
                flag = "2";
                break;
            case 1011:
                flag = "1";
                break;
            case 1012:
                flag = "4";
                break;
        }
        IHomeLogic homeLogic = new HomeLogic();
        homeLogic.getSpreadList("2", flag, updateTime, iCallListener);
    }


    //获取班干部
    public void getClassCadreList(ICallListener<String> iCallListener) {
        ClassCadreLogic cadreLogic = new ClassCadreLogic();
        cadreLogic.getClassCadreList(mUserInfo.getUserInfoBean().getDefaultClassId(), iCallListener);
    }

    //获取值日生
    public void getDutyStudentList(ICallListener<String> iCallListener) {
        DutyStudentLogic dutyStudentLogic = new DutyStudentLogic();
        dutyStudentLogic.getDutyStudentList(mUserInfo.getUserInfoBean().getDefaultClassId(), iCallListener);
    }

    //获取班级课表
    public void getClassCurriculum(ICallListener<String> iCallListener) {
        CurriculumLogic curriculumLogic = new CurriculumLogic();
        curriculumLogic.getClassCurriculum(mUserInfo.getUserInfoBean().getUserId(), mUserInfo.getUserInfoBean().getDefaultClassId(), iCallListener);
    }

    //获取班级用户列表
    public void getClassPeopleList(String classId, String updateTime, ICallListener<String> iCallListener) {
        IClassLogic classLogic = new ClassLogic();
        classLogic.getClassPeopleListForService(mUserInfo.getUserInfoBean().getUserId(), classId, updateTime, iCallListener);
    }

    //获取本班老师
    public void getClassTeacherList(ICallListener<String> iCallListener) {
        IClassTeacherListLogic classTeacherListLogic = new ClassTeacherListLogic();
        classTeacherListLogic.getClassTeacherList(mUserInfo.getUserInfoBean().getDefaultClassId(), iCallListener);
    }

    //获取用户详情
    public void getDetailInfo(String uId, ICallListener<String> iCallListener) {
        IDetailPeopleLogic detailPeopleLogic = new DetailPeopleLogic();
        detailPeopleLogic.getDetailInfo(mUserInfo.getUserInfoBean().getUserId(), uId, iCallListener);
    }

    //获取关于我们的信息
    public void getAboutUsInfo(ICallListener<String> iCallListener) {
        AboutUsLogic aboutUsLogic = new AboutUsLogic();
        aboutUsLogic.getAboutUsInfo(iCallListener);
    }

    //获取道具流转列表
    public void getPropsList(int typeId, int pageNum, ICallListener<String> iCallListener) {
        PropsListLogic propsListLogic = new PropsListLogic();
        propsListLogic.getPropsListData(mUserInfo.getUserInfoBean().getUserId(), typeId, pageNum, iCallListener);
    }
}
