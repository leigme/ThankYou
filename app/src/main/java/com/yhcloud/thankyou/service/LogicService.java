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
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.module.aboutus.logic.AboutUsLogic;
import com.yhcloud.thankyou.module.chat.logic.ChatLogic;
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
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class LogicService extends Service {

    private String TAG = getClass().getSimpleName();

    private MyBinder mBinder = new MyBinder();
    private UserInfo mUserInfo;
    private ArrayList<UserInfoBean> mUserInfoBeen;
    private SparseArray<FunctionBean> mBeanSparseArray;
    private ArrayList<FunctionBean> mBeen;
    private SharedPreferences mPreferences;
    private boolean canMessage;

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
    }

    public SparseArray<FunctionBean> getBeanSparseArray() {
        return mBeanSparseArray;
    }

    public void setBeanSparseArray(SparseArray<FunctionBean> beanSparseArray) {
        mBeanSparseArray = beanSparseArray;
    }

    public ArrayList<FunctionBean> getBeen() {
        return mBeen;
    }

    public void setBeen(ArrayList<FunctionBean> been) {
        mBeen = been;
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

    public void getUserAllFuncation() {
        mBeen = new ArrayList<>();
        String allFuncations = mPreferences.getString(mUserInfo.getUserInfoBean().getUserId(), "");
        if (null != allFuncations && !"".equals(allFuncations)) {
            Tools.print(TAG, "保存的jsonList是:" + allFuncations);
            Gson gson = new Gson();
            String[] list = gson.fromJson(allFuncations, new TypeToken<String[]>(){}.getType());
            Tools.print(TAG, "保存的数组是:" + list);
            for (String s: list) {
                mBeen.add(mBeanSparseArray.get(Integer.parseInt(s)));
            }
        } else {
            switch (mUserInfo.getUserInfoBean().getUserRoleId()) {
                //初始化校长角色应用
                case 1004:
                    mBeen.add(mBeanSparseArray.get(10));
                    mBeen.add(mBeanSparseArray.get(4));
                    mBeen.add(mBeanSparseArray.get(11));
                    mBeen.add(mBeanSparseArray.get(12));
                    mBeen.add(mBeanSparseArray.get(13));
                    mBeen.add(mBeanSparseArray.get(14));
                    mBeen.add(mBeanSparseArray.get(3));
                    break;
                //初始化老师角色应用
                case 1010:
                    mBeen.add(mBeanSparseArray.get(10));
                    mBeen.add(mBeanSparseArray.get(4));
                    mBeen.add(mBeanSparseArray.get(11));
                    mBeen.add(mBeanSparseArray.get(12));
                    mBeen.add(mBeanSparseArray.get(13));
                    mBeen.add(mBeanSparseArray.get(14));
                    mBeen.add(mBeanSparseArray.get(3));
                    break;
                //初始化学生角色应用
                case 1011:
                    mBeen.add(mBeanSparseArray.get(10));
                    mBeen.add(mBeanSparseArray.get(4));
                    mBeen.add(mBeanSparseArray.get(11));
                    mBeen.add(mBeanSparseArray.get(12));
                    mBeen.add(mBeanSparseArray.get(14));
                    mBeen.add(mBeanSparseArray.get(3));
                    mBeen.add(mBeanSparseArray.get(21));
                    break;
                //初始化家长角色应用
                case 1012:
                    mBeen.add(mBeanSparseArray.get(10));
                    mBeen.add(mBeanSparseArray.get(4));
                    mBeen.add(mBeanSparseArray.get(11));
                    mBeen.add(mBeanSparseArray.get(12));
                    mBeen.add(mBeanSparseArray.get(15));
                    mBeen.add(mBeanSparseArray.get(14));
                    mBeen.add(mBeanSparseArray.get(3));
                    break;
            }
        }
    }

    //保存用户应用列表
    public void saveFuncations() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (FunctionBean fb: mBeen) {
            if (0 != fb.getId()) {
                arrayList.add(fb.getId());
            }
        }
        Gson gson = new Gson();
        String jsonList = gson.toJson(arrayList);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(mUserInfo.getUserInfoBean().getUserId(), jsonList);
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

    //获取用户好友列表
    public void getFriendList(String updateTime, ICallListener<String> iCallListener) {
        ChatLogic chatLogic = new ChatLogic();
        chatLogic.getFriendList(mUserInfo.getUserInfoBean().getUserId(), updateTime, iCallListener);
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
    public void getTeacherHomeworkList(ICallListener<String> iCallListener) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.getTeacherHomeworkList(mUserInfo.getUserInfoBean().getUserId(), iCallListener);
    }

    //获取老师端作业详情
    public void getTeacherHomeworkInfo(String homewrokId, ICallListener<String> iCallListener) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.getTeacherHomeworkInfo(homewrokId, iCallListener);
    }

    //获取学生作业列表
    public void getStudentHomeworkList(ICallListener<String> iCallListener) {
        HomeworkLogic homeworkLogic = new HomeworkLogic();
        homeworkLogic.getStudentHomeworkList(mUserInfo.getUserInfoBean().getUserId(), iCallListener);
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
