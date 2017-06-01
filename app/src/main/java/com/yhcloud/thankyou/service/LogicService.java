package com.yhcloud.thankyou.service;

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
import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.service.logic.mimplement.ClassLogic;
import com.yhcloud.thankyou.service.logic.mimplement.HomeLogic;
import com.yhcloud.thankyou.service.logic.minterface.IClassLogic;
import com.yhcloud.thankyou.service.logic.minterface.IHomeLogic;
import com.yhcloud.thankyou.service.logic.minterface.ILoginLogic;
import com.yhcloud.thankyou.service.logic.mimplement.LoginLogic;
import com.yhcloud.thankyou.service.logic.mimplement.MainLogic;
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

public class LogicService extends BaseService {

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

    //↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    public ILoginLogic getLoginLogic() {
        ILoginLogic iLoginLogic = new LoginLogic();
        return iLoginLogic;
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
    public void login(String username, String password, ResponseCallBack<String> responseCallBack) {
        ILoginLogic loginLogic = new LoginLogic();
        loginLogic.login(username, password, responseCallBack);
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
    public void getBannerImageUrls(String updateTime, ResponseCallBack<String> responseCallBack) {
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
        homeLogic.getSpreadList("16", flag, updateTime, responseCallBack);
    }

    //获取推广列表
    public void getSpreadList(String updateTime, ResponseCallBack<String> responseCallBack) {
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
        homeLogic.getSpreadList("2", flag, updateTime, responseCallBack);
    }

    //获取用户好友列表
    public void getFriendList(String updateTime, ResponseCallBack<String> responseCallBack) {
        MainLogic mainLogic = new MainLogic();
        mainLogic.getFriendList(mUserInfo.getUserInfoBean().getUserId(), updateTime, responseCallBack);
    }

    //获取学校公告
    public void getSchoolAnnouncementData(int pageNum, ResponseCallBack<String> responseCallBack) {
        SchoolAnnouncementLogic schoolAnnouncementLogic = new SchoolAnnouncementLogic();
        schoolAnnouncementLogic.getSchoolAnnouncementData(mUserInfo.getUserInfoBean().getSchoolId(), pageNum, responseCallBack);
    }

    //获取班级通知
    public void getClassNotificationData(int pageNum, ResponseCallBack<String> responseCallBack) {
        ClassNotificationLogic classNotificationLogic = new ClassNotificationLogic();
        classNotificationLogic.getClassNotificationData(mUserInfo.getUserInfoBean().getUserId(),
                mUserInfo.getUserInfoBean().getDefaultClassId(), pageNum, "-1", responseCallBack);
    }

    //更新班级通知阅读状态
    public void updateClassNotificationReadState(String noticeId, ResponseCallBack<String> responseCallBack) {
        ClassNotificationLogic classNotificationLogic = new ClassNotificationLogic();
        classNotificationLogic.updateReadState(noticeId, mUserInfo.getUserInfoBean().getUserId(), responseCallBack);
    }

    //获取老师端作业列表
    public void getTeacherHomeworkList(int page, ResponseCallBack<String> responseCallBack) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.getTeacherHomeworkList(mUserInfo.getUserInfoBean().getUserId(), String.valueOf(page), responseCallBack);
    }

    //获取老师端作业详情
    public void getTeacherHomeworkInfo(String homewrokId, ResponseCallBack<String> responseCallBack) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.getTeacherHomeworkInfo(homewrokId, responseCallBack);
    }

    //获取学生作业列表
    public void getStudentHomeworkList(int page, ResponseCallBack<String> responseCallBack) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.getStudentHomeworkList(mUserInfo.getUserInfoBean().getUserId(), String.valueOf(page), responseCallBack);
    }

    //获取学生作业详情
    public void getStudentHomeworkInfo(String workBookId, ResponseCallBack<String> responseCallBack) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.getStudentHomeworkInfo(mUserInfo.getUserInfoBean().getUserId(), workBookId, responseCallBack);
    }

    //学生发送客观题作业
    public void sendStudentObjectiveHomework(String jsonObject, ResponseCallBack<String> responseCallBack) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.sendObjectiveHomeworkToService(mUserInfo.getUserInfoBean().getUserId(), jsonObject, responseCallBack);
    }

    //发送学生主观题作业
    public void sendStudentSubjectiveHomework(String workId, String questionId, String content,
                                              String score, String startTime, String endTime,
                                              List<String> images, ResponseCallBack<String> responseCallBack) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.sendImagesToService(mUserInfo.getUserInfoBean().getUserId(), workId, questionId, content, score, startTime, endTime, images, responseCallBack);
    }

    //更新提交学生作业状态
    public void updateStudentHomework(String workId, ResponseCallBack<String> responseCallBack) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.updateStudentHomework(workId, responseCallBack);
    }

    //获取学期数列表
    public void getTermList(ResponseCallBack<String> responseCallBack) {
        MainLogic mainLogic = new MainLogic();
        mainLogic.getTermListForService(mUserInfo.getUserInfoBean().getUserId(), responseCallBack);
    }

    //获取周次列表
    public void getWeekList(String termId, ResponseCallBack<String> responseCallBack) {
        MainLogic mainLogic = new MainLogic();
        mainLogic.getWeekListForService(termId, responseCallBack);
    }

    //获取一周菜谱数据
    public void getRecipesData(ResponseCallBack<String> responseCallBack) {
        TodayRecipesLogic todayRecipesLogic = new TodayRecipesLogic();
        todayRecipesLogic.getRecipesDataForService(mUserInfo.getUserInfoBean().getUserId(), responseCallBack);
    }

    public void getRecipesData(String termId, String weekId, ResponseCallBack<String> responseCallBack) {
        TodayRecipesLogic todayRecipesLogic = new TodayRecipesLogic();
        todayRecipesLogic.getRecipesDataForService(mUserInfo.getUserInfoBean().getUserId(), termId, weekId, responseCallBack);
    }


    //获取班干部
    public void getClassCadreList(ResponseCallBack<String> responseCallBack) {
        ClassCadreLogic cadreLogic = new ClassCadreLogic();
        cadreLogic.getClassCadreList(mUserInfo.getUserInfoBean().getDefaultClassId(), responseCallBack);
    }

    //获取值日生
    public void getDutyStudentList(ResponseCallBack<String> responseCallBack) {
        DutyStudentLogic dutyStudentLogic = new DutyStudentLogic();
        dutyStudentLogic.getDutyStudentList(mUserInfo.getUserInfoBean().getDefaultClassId(), responseCallBack);
    }

    //获取班级课表
    public void getClassCurriculum(ResponseCallBack<String> responseCallBack) {
        CurriculumLogic curriculumLogic = new CurriculumLogic();
        curriculumLogic.getClassCurriculum(mUserInfo.getUserInfoBean().getUserId(), mUserInfo.getUserInfoBean().getDefaultClassId(), responseCallBack);
    }

    //获取班级用户列表
    public void getClassPeopleList(String classId, String updateTime, ResponseCallBack<String> responseCallBack) {
        IClassLogic classLogic = new ClassLogic();
        classLogic.getClassPeopleListForService(mUserInfo.getUserInfoBean().getUserId(), classId, updateTime, responseCallBack);
    }

    //获取本班老师
    public void getClassTeacherList(ResponseCallBack<String> responseCallBack) {
        IClassTeacherListLogic classTeacherListLogic = new ClassTeacherListLogic();
        classTeacherListLogic.getClassTeacherList(mUserInfo.getUserInfoBean().getDefaultClassId(), responseCallBack);
    }

    //获取用户详情
    public void getDetailInfo(String uId, ResponseCallBack<String> responseCallBack) {
        IDetailPeopleLogic detailPeopleLogic = new DetailPeopleLogic();
        detailPeopleLogic.getDetailInfo(mUserInfo.getUserInfoBean().getUserId(), uId, responseCallBack);
    }

    //获取用户账户轮播图
    public void getBannerImages(String updateTime, ResponseCallBack<String> responseCallBack) {
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
        accountLogic.getImageUrlsForService("16", flag, updateTime, responseCallBack);
    }

    //获取用户货币信息
    public void getUserCurrency(ResponseCallBack<String> responseCallBack) {
        AccountLogic accountLogic = new AccountLogic();
        accountLogic.getUserCurrency(mUserInfo.getUserInfoBean().getUserId(), responseCallBack);
    }


    //用户充值
    //获取充值列表
    public void getRechargeList(ResponseCallBack<String> responseCallBack) {
        AccountRechargeLogic accountRechargeLogic = new AccountRechargeLogic();
        accountRechargeLogic.getRechargeList(responseCallBack);
    }

    //获取支付列表
    public void getPayList(ResponseCallBack<String> responseCallBack) {
        AccountRechargeLogic accountRechargeLogic = new AccountRechargeLogic();
        accountRechargeLogic.getPayList(responseCallBack);
    }

    //得到支付订单
    public void getOrderNum(String productId, String payId, ResponseCallBack<String> responseCallBack) {
        AccountRechargeLogic accountRechargeLogic = new AccountRechargeLogic();
        accountRechargeLogic.getOrderNumForService(mUserInfo.getUserInfoBean().getUserId(), productId, payId, mUserInfo.getUserInfoBean().getRealName(), responseCallBack);
    }

    //获取积分兑换列表
    public void getIntegralExchangeList(ResponseCallBack<String> responseCallBack) {
        AccountIntegralLogic accountIntegralLogic = new AccountIntegralLogic();
        accountIntegralLogic.getIntegralList(responseCallBack);
    }

    //兑换积分
    public void getUserCoin(String uCoin, String coin, ResponseCallBack<String> responseCallBack) {
        AccountIntegralLogic accountIntegralLogic = new AccountIntegralLogic();
        accountIntegralLogic.getUserCoin(mUserInfo.getUserInfoBean().getUserId(), uCoin, coin, responseCallBack);
    }

    //获取商城道具列表
    public void getPropsStoreList(ResponseCallBack<String> responseCallBack) {
        AccountPropsLogic accountPropsLogic = new AccountPropsLogic();
        accountPropsLogic.getPropsListForService(mUserInfo.getUserInfoBean().getUserId(), responseCallBack);
    }

    //购买道具
    public void buyProps(String propId, String propNum, ResponseCallBack<String> responseCallBack) {
        AccountPropsInfoLogic accountPropsInfoLogic = new AccountPropsInfoLogic();
        accountPropsInfoLogic.buyProps(mUserInfo.getUserInfoBean().getUserId(), propId, propNum, responseCallBack);
    }

    //获取用户道具列表
    public void getUserPropsList(ResponseCallBack<String> responseCallBack) {
        AccountMyPropsLogic accountMyPropsLogic= new AccountMyPropsLogic();
        accountMyPropsLogic.getUserPropsForService(mUserInfo.getUserInfoBean().getUserId(), responseCallBack);
    }

    //赠送道具
    public void givePropsToPeople(String recvUserId, String propId, int propNum, ResponseCallBack<String> responseCallBack) {
        AccountMyPropsLogic accountMyPropsLogic = new AccountMyPropsLogic();
        accountMyPropsLogic.givePropsToPeople(mUserInfo.getUserInfoBean().getUserId(), recvUserId, propId, propNum, responseCallBack);
    }


    //获取账户往来列表
    public void getUserRecordingList(int pageNow, ResponseCallBack<String> responseCallBack) {
        AccountRecordingLogic accountRecordingLogic = new AccountRecordingLogic();
        accountRecordingLogic.getUserRecordingList(mUserInfo.getUserInfoBean().getUserId(), pageNow, responseCallBack);
    }

    //获取关于我们的信息
    public void getAboutUsInfo(ResponseCallBack<String> responseCallBack) {
        AboutUsLogic aboutUsLogic = new AboutUsLogic();
        aboutUsLogic.getAboutUsInfo(responseCallBack);
    }

    //获取道具流转列表
    public void getPropsList(int typeId, int pageNum, ResponseCallBack<String> responseCallBack) {
        PropsListLogic propsListLogic = new PropsListLogic();
        propsListLogic.getPropsListData(mUserInfo.getUserInfoBean().getUserId(), typeId, pageNum, responseCallBack);
    }
}
