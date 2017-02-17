package com.yhcloud.thankyou.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.bean.FunctionBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.logic.ClassLogic;
import com.yhcloud.thankyou.logic.HomeLogic;
import com.yhcloud.thankyou.logic.IClassLogic;
import com.yhcloud.thankyou.logic.IHomeLogic;
import com.yhcloud.thankyou.logic.ILoginLogic;
import com.yhcloud.thankyou.logic.LoginLogic;
import com.yhcloud.thankyou.logic.MainLogic;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.module.aboutus.logic.AboutUsLogic;
import com.yhcloud.thankyou.module.account.logic.AccountIntegralLogic;
import com.yhcloud.thankyou.module.account.logic.AccountLogic;
import com.yhcloud.thankyou.module.account.logic.AccountMyPropsLogic;
import com.yhcloud.thankyou.module.account.logic.AccountPropsInfoLogic;
import com.yhcloud.thankyou.module.account.logic.AccountPropsLogic;
import com.yhcloud.thankyou.module.account.logic.AccountRechargeLogic;
import com.yhcloud.thankyou.module.account.logic.AccountRecordingLogic;
import com.yhcloud.thankyou.module.classcadre.logic.ClassCadreLogic;
import com.yhcloud.thankyou.module.classnotification.logic.ClassNotificationLogic;
import com.yhcloud.thankyou.module.classteachers.logic.ClassTeacherListLogic;
import com.yhcloud.thankyou.module.classteachers.logic.IClassTeacherListLogic;
import com.yhcloud.thankyou.module.curriculum.logic.CurriculumLogic;
import com.yhcloud.thankyou.module.detailinfo.logic.DetailPeopleLogic;
import com.yhcloud.thankyou.module.detailinfo.logic.IDetailPeopleLogic;
import com.yhcloud.thankyou.module.dutystudent.logic.DutyStudentLogic;
import com.yhcloud.thankyou.module.homework.logic.HomeworkLogic;
import com.yhcloud.thankyou.module.propslist.logic.PropsListLogic;
import com.yhcloud.thankyou.module.schoolannouncement.logic.SchoolAnnouncementLogic;
import com.yhcloud.thankyou.module.todayrecipes.logic.TodayRecipesLogic;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LogicService extends Service {

    private String TAG = getClass().getSimpleName();

    private MyBinder mBinder = new MyBinder();
    private UserInfo mUserInfo;
    private ArrayList<UserInfoBean> mUserInfoBeen;
    private HashMap<String, String[]> mMap;
    private SparseArray<FunctionBean> mBeanSparseArray;
    private ArrayList<FunctionBean> mAddFunctionBeen, mNoneFunctionBeen;
    private SharedPreferences mPreferences;
    private boolean canMessage;
    private String shortcut;

    public LogicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        mPreferences = this.getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
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

    public ArrayList<UserInfoBean> getUserInfoBeen() {
        return mUserInfoBeen;
    }

    public void setUserInfoBeen(ArrayList<UserInfoBean> userInfoBeen) {
        mUserInfoBeen = userInfoBeen;
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
        shortcut = mUserInfo.getUserInfoBean().getUserId() + "的快捷方式";
    }

    public HashMap<String, String[]> getMap() {
        return mMap;
    }

    public void setMap(HashMap<String, String[]> map) {
        mMap = map;
    }

    public SparseArray<FunctionBean> getBeanSparseArray() {
        return mBeanSparseArray;
    }

    public void setBeanSparseArray(SparseArray<FunctionBean> beanSparseArray) {
        mBeanSparseArray = beanSparseArray;
    }

    public ArrayList<FunctionBean> getAddFunctionBeen() {
        return mAddFunctionBeen;
    }

    public void setAddFunctionBeen(ArrayList<FunctionBean> addFunctionBeen) {
        mAddFunctionBeen = addFunctionBeen;
    }

    public ArrayList<FunctionBean> getNoneFunctionBeen() {
        return mNoneFunctionBeen;
    }

    public void setNoneFunctionBeen(ArrayList<FunctionBean> noneFunctionBeen) {
        mNoneFunctionBeen = noneFunctionBeen;
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }

    public void setPreferences(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    public boolean isCanMessage() {
        return canMessage;
    }

    public void setCanMessage(boolean canMessage) {
        this.canMessage = canMessage;
    }


    //==========================//

    //登录
    public void login(String username, String password, ICallListener<String> iCallListener) {
        ILoginLogic loginLogic = new LoginLogic();
        loginLogic.login(username, password, iCallListener);
    }

    //存储用户登录信息
    public void saveUserInfo(UserInfo userInfo) {
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
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(Constant.USER_LOGINED, false);
        editor.commit();
    }

    //清除用户登录信息
    public void cleanUserInfo() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void getUserAddFuncation() {
        mAddFunctionBeen = new ArrayList<>();
        String allFuncations = mPreferences.getString(shortcut, "");
        if (null != allFuncations && !"".equals(allFuncations)) {
            Tools.print(TAG, "保存的jsonList是:" + allFuncations);
            Gson gson = new Gson();
            String[] list = gson.fromJson(allFuncations, new TypeToken<String[]>(){}.getType());
            Tools.print(TAG, "保存的数组是:" + list);
            for (String s: list) {
                mAddFunctionBeen.add(mBeanSparseArray.get(Integer.parseInt(s)));
            }
        } else {
            switch (mUserInfo.getUserInfoBean().getUserRoleId()) {
                //初始化校长角色应用
                case 1004:
                    mAddFunctionBeen.add(mBeanSparseArray.get(10));
                    mAddFunctionBeen.add(mBeanSparseArray.get(4));
                    mAddFunctionBeen.add(mBeanSparseArray.get(11));
                    mAddFunctionBeen.add(mBeanSparseArray.get(12));
                    mAddFunctionBeen.add(mBeanSparseArray.get(13));
                    mAddFunctionBeen.add(mBeanSparseArray.get(14));
                    mAddFunctionBeen.add(mBeanSparseArray.get(3));
                    break;
                //初始化老师角色应用
                case 1010:
                    mAddFunctionBeen.add(mBeanSparseArray.get(10));
                    mAddFunctionBeen.add(mBeanSparseArray.get(4));
                    mAddFunctionBeen.add(mBeanSparseArray.get(11));
                    mAddFunctionBeen.add(mBeanSparseArray.get(12));
                    mAddFunctionBeen.add(mBeanSparseArray.get(13));
                    mAddFunctionBeen.add(mBeanSparseArray.get(14));
                    mAddFunctionBeen.add(mBeanSparseArray.get(3));
                    break;
                //初始化学生角色应用
                case 1011:
                    mAddFunctionBeen.add(mBeanSparseArray.get(10));
                    mAddFunctionBeen.add(mBeanSparseArray.get(4));
                    mAddFunctionBeen.add(mBeanSparseArray.get(11));
                    mAddFunctionBeen.add(mBeanSparseArray.get(12));
                    mAddFunctionBeen.add(mBeanSparseArray.get(14));
                    mAddFunctionBeen.add(mBeanSparseArray.get(3));
                    mAddFunctionBeen.add(mBeanSparseArray.get(21));
                    break;
                //初始化家长角色应用
                case 1012:
                    mAddFunctionBeen.add(mBeanSparseArray.get(10));
                    mAddFunctionBeen.add(mBeanSparseArray.get(4));
                    mAddFunctionBeen.add(mBeanSparseArray.get(11));
                    mAddFunctionBeen.add(mBeanSparseArray.get(12));
                    mAddFunctionBeen.add(mBeanSparseArray.get(15));
                    mAddFunctionBeen.add(mBeanSparseArray.get(14));
                    mAddFunctionBeen.add(mBeanSparseArray.get(3));
                    break;
            }
        }
    }

    public void getUserNoneFuncation() {
        Tools.print(TAG, "进入方法...");
        mNoneFunctionBeen = new ArrayList<>();
        switch (mUserInfo.getUserInfoBean().getUserRoleId()) {
            //初始化校长角色应用
            case 1004:
                mNoneFunctionBeen.add(mBeanSparseArray.get(10));
                mNoneFunctionBeen.add(mBeanSparseArray.get(4));
                mNoneFunctionBeen.add(mBeanSparseArray.get(11));
                mNoneFunctionBeen.add(mBeanSparseArray.get(12));
                mNoneFunctionBeen.add(mBeanSparseArray.get(13));
                mNoneFunctionBeen.add(mBeanSparseArray.get(14));
                mNoneFunctionBeen.add(mBeanSparseArray.get(3));
                mNoneFunctionBeen.add(mBeanSparseArray.get(22));
                break;
            //初始化老师角色应用
            case 1010:
                mNoneFunctionBeen.add(mBeanSparseArray.get(10));
                mNoneFunctionBeen.add(mBeanSparseArray.get(4));
                mNoneFunctionBeen.add(mBeanSparseArray.get(11));
                mNoneFunctionBeen.add(mBeanSparseArray.get(12));
                mNoneFunctionBeen.add(mBeanSparseArray.get(13));
                mNoneFunctionBeen.add(mBeanSparseArray.get(14));
                mNoneFunctionBeen.add(mBeanSparseArray.get(3));
                mNoneFunctionBeen.add(mBeanSparseArray.get(22));
                break;
            //初始化学生角色应用
            case 1011:
                mNoneFunctionBeen.add(mBeanSparseArray.get(10));
                mNoneFunctionBeen.add(mBeanSparseArray.get(4));
                mNoneFunctionBeen.add(mBeanSparseArray.get(11));
                mNoneFunctionBeen.add(mBeanSparseArray.get(12));
                mNoneFunctionBeen.add(mBeanSparseArray.get(14));
                mNoneFunctionBeen.add(mBeanSparseArray.get(3));
                mNoneFunctionBeen.add(mBeanSparseArray.get(21));
                mNoneFunctionBeen.add(mBeanSparseArray.get(22));
                break;
            //初始化家长角色应用
            case 1012:
                mNoneFunctionBeen.add(mBeanSparseArray.get(10));
                mNoneFunctionBeen.add(mBeanSparseArray.get(4));
                mNoneFunctionBeen.add(mBeanSparseArray.get(11));
                mNoneFunctionBeen.add(mBeanSparseArray.get(12));
                mNoneFunctionBeen.add(mBeanSparseArray.get(15));
                mNoneFunctionBeen.add(mBeanSparseArray.get(14));
                mNoneFunctionBeen.add(mBeanSparseArray.get(3));
                mNoneFunctionBeen.add(mBeanSparseArray.get(22));
                break;
        }
        String addFuncations = mPreferences.getString(shortcut, "");
        if (null != addFuncations && !"".equals(addFuncations)) {
            Tools.print(TAG, "保存的jsonList是:" + addFuncations);
            Gson gson = new Gson();
            String[] list = gson.fromJson(addFuncations, new TypeToken<String[]>(){}.getType());
            Tools.print(TAG, "保存的数组是:" + list);
            for (String s: list) {
                Iterator<FunctionBean> iterator = mNoneFunctionBeen.iterator();
                while (iterator.hasNext()) {
                    FunctionBean functionBean = iterator.next();
                    if (s.equals(String.valueOf(functionBean.getId()))) {
                        iterator.remove();
                    }
                }
            }
        } else {
            for (FunctionBean fb: mAddFunctionBeen) {
                Iterator<FunctionBean> functionBeanIterator = mNoneFunctionBeen.iterator();
                while (functionBeanIterator.hasNext()) {
                    FunctionBean functionBean = functionBeanIterator.next();
                    if (fb.getId() == functionBean.getId()) {
                        functionBeanIterator.remove();
                    }
                }
            }
        }
    }

    //保存用户应用列表
    public void saveFuncations(ArrayList<Integer> arrayList) {
        Gson gson = new Gson();
        String jsonList = gson.toJson(arrayList);
        Tools.print(TAG, MessageFormat.format("用户保存的是: {0}", jsonList));
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(shortcut, jsonList);
        editor.commit();
    }


    //获取轮播图
    public void getBannerImageUrls(String updateTime, ICallListener<String> iCallListener) {
        String flag;
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
            default:
                flag = "";
                break;
        }
        IHomeLogic homeLogic = new HomeLogic();
        homeLogic.getSpreadList("16", flag, updateTime, iCallListener);
    }

    //获取推广列表
    public void getSpreadList(String updateTime, ICallListener<String> iCallListener) {
        String flag;
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
            default:
                flag = "";
                break;
        }
        IHomeLogic homeLogic = new HomeLogic();
        homeLogic.getSpreadList("2", flag, updateTime, iCallListener);
    }

    //获取用户好友列表
    public void getFriendList(String updateTime, ICallListener<String> iCallListener) {
        MainLogic mainLogic = new MainLogic();
        mainLogic.getFriendList(mUserInfo.getUserInfoBean().getUserId(), updateTime, iCallListener);
    }

    //获取学校公告
    public void getSchoolAnnouncementData(int pageNum, ICallListener<String> iCallListener) {
        SchoolAnnouncementLogic schoolAnnouncementLogic = new SchoolAnnouncementLogic();
        schoolAnnouncementLogic.getSchoolAnnouncementData(mUserInfo.getUserInfoBean().getSchoolId(), pageNum, iCallListener);
    }

    //获取班级通知
    public void getClassNotificationData(int pageNum, ICallListener<String> iCallListener) {
        ClassNotificationLogic classNotificationLogic = new ClassNotificationLogic();
        classNotificationLogic.getClassNotificationData(mUserInfo.getUserInfoBean().getUserId(),
                mUserInfo.getUserInfoBean().getDefaultClassId(), pageNum, "-1", iCallListener);
    }

    //更新班级通知阅读状态
    public void updateClassNotificationReadState(String noticeId, ICallListener<String> iCallListener) {
        ClassNotificationLogic classNotificationLogic = new ClassNotificationLogic();
        classNotificationLogic.updateReadState(noticeId, mUserInfo.getUserInfoBean().getUserId(), iCallListener);
    }

    //获取老师端作业列表
    public void getTeacherHomeworkList(int page, ICallListener<String> iCallListener) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.getTeacherHomeworkList(mUserInfo.getUserInfoBean().getUserId(), String.valueOf(page), iCallListener);
    }

    //获取老师端作业详情
    public void getTeacherHomeworkInfo(String homewrokId, ICallListener<String> iCallListener) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.getTeacherHomeworkInfo(homewrokId, iCallListener);
    }

    //获取学生作业列表
    public void getStudentHomeworkList(int page, ICallListener<String> iCallListener) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.getStudentHomeworkList(mUserInfo.getUserInfoBean().getUserId(), String.valueOf(page), iCallListener);
    }

    //获取学生作业详情
    public void getStudentHomeworkInfo(String workBookId, ICallListener<String> iCallListener) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.getStudentHomeworkInfo(mUserInfo.getUserInfoBean().getUserId(), workBookId, iCallListener);
    }

    //学生发送客观题作业
    public void sendStudentObjectiveHomework(String jsonObject, ICallListener<String> iCallListener) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.sendObjectiveHomeworkToService(mUserInfo.getUserInfoBean().getUserId(), jsonObject, iCallListener);
    }

    //发送学生主观题作业
    public void sendStudentSubjectiveHomework(String workId, String questionId, String content,
                                              String score, String startTime, String endTime,
                                              List<String> images, ICallListener<String> iCallListener) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.sendImagesToService(mUserInfo.getUserInfoBean().getUserId(), workId, questionId, content, score, startTime, endTime, images, iCallListener);
    }

    //更新提交学生作业状态
    public void updateStudentHomework(String workId, ICallListener<String> iCallListener) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.updateStudentHomework(workId, iCallListener);
    }

    //获取学期数列表
    public void getTermList(ICallListener<String> iCallListener) {
        MainLogic mainLogic = new MainLogic();
        mainLogic.getTermListForService(mUserInfo.getUserInfoBean().getUserId(), iCallListener);
    }

    //获取周次列表
    public void getWeekList(String termId, ICallListener<String> iCallListener) {
        MainLogic mainLogic = new MainLogic();
        mainLogic.getWeekListForService(termId, iCallListener);
    }

    //获取一周菜谱数据
    public void getRecipesData(ICallListener<String> iCallListener) {
        TodayRecipesLogic todayRecipesLogic = new TodayRecipesLogic();
        todayRecipesLogic.getRecipesDataForService(mUserInfo.getUserInfoBean().getUserId(), iCallListener);
    }

    public void getRecipesData(String termId, String weekId, ICallListener<String> iCallListener) {
        TodayRecipesLogic todayRecipesLogic = new TodayRecipesLogic();
        todayRecipesLogic.getRecipesDataForService(mUserInfo.getUserInfoBean().getUserId(), termId, weekId, iCallListener);
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

    //获取用户账户轮播图
    public void getBannerImages(String updateTime, ICallListener<String> iCallListener) {
        String flag;
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
            default:
                flag = "";
                break;
        }
        AccountLogic accountLogic = new AccountLogic();
        accountLogic.getImageUrlsForService("16", flag, updateTime, iCallListener);
    }

    //获取用户货币信息
    public void getUserCurrency(ICallListener<String> iCallListener) {
        AccountLogic accountLogic = new AccountLogic();
        accountLogic.getUserCurrency(mUserInfo.getUserInfoBean().getUserId(), iCallListener);
    }


    //用户充值
    //获取充值列表
    public void getRechargeList(ICallListener<String> iCallListener) {
        AccountRechargeLogic accountRechargeLogic = new AccountRechargeLogic();
        accountRechargeLogic.getRechargeList(iCallListener);
    }

    //获取支付列表
    public void getPayList(ICallListener<String> iCallListener) {
        AccountRechargeLogic accountRechargeLogic = new AccountRechargeLogic();
        accountRechargeLogic.getPayList(iCallListener);
    }

    //得到支付订单
    public void getOrderNum(String productId, String payId, ICallListener<String> iCallListener) {
        AccountRechargeLogic accountRechargeLogic = new AccountRechargeLogic();
        accountRechargeLogic.getOrderNumForService(mUserInfo.getUserInfoBean().getUserId(), productId, payId, mUserInfo.getUserInfoBean().getRealName(), iCallListener);
    }

    //获取积分兑换列表
    public void getIntegralExchangeList(ICallListener<String> iCallListener) {
        AccountIntegralLogic accountIntegralLogic = new AccountIntegralLogic();
        accountIntegralLogic.getIntegralList(iCallListener);
    }

    //兑换积分
    public void getUserCoin(String uCoin, String coin, ICallListener<String> iCallListener) {
        AccountIntegralLogic accountIntegralLogic = new AccountIntegralLogic();
        accountIntegralLogic.getUserCoin(mUserInfo.getUserInfoBean().getUserId(), uCoin, coin, iCallListener);
    }

    //获取商城道具列表
    public void getPropsStoreList(ICallListener<String> iCallListener) {
        AccountPropsLogic accountPropsLogic = new AccountPropsLogic();
        accountPropsLogic.getPropsListForService(mUserInfo.getUserInfoBean().getUserId(), iCallListener);
    }

    //购买道具
    public void buyProps(String propId, String propNum, ICallListener<String> iCallListener) {
        AccountPropsInfoLogic accountPropsInfoLogic = new AccountPropsInfoLogic();
        accountPropsInfoLogic.buyProps(mUserInfo.getUserInfoBean().getUserId(), propId, propNum, iCallListener);
    }

    //获取用户道具列表
    public void getUserPropsList(ICallListener<String> iCallListener) {
        AccountMyPropsLogic accountMyPropsLogic= new AccountMyPropsLogic();
        accountMyPropsLogic.getUserPropsForService(mUserInfo.getUserInfoBean().getUserId(), iCallListener);
    }

    //赠送道具
    public void givePropsToPeople(String recvUserId, String propId, int propNum, ICallListener<String> iCallListener) {
        AccountMyPropsLogic accountMyPropsLogic = new AccountMyPropsLogic();
        accountMyPropsLogic.givePropsToPeople(mUserInfo.getUserInfoBean().getUserId(), recvUserId, propId, propNum, iCallListener);
    }


    //获取账户往来列表
    public void getUserRecordingList(int pageNow, ICallListener<String> iCallListener) {
        AccountRecordingLogic accountRecordingLogic = new AccountRecordingLogic();
        accountRecordingLogic.getUserRecordingList(mUserInfo.getUserInfoBean().getUserId(), pageNow, iCallListener);
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
