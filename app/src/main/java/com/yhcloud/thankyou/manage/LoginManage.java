package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.yhcloud.thankyou.R;
import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.comm.BaseService;
import com.yhcloud.thankyou.comm.BindServiceCallBack;
import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.service.logic.mimplement.LoginLogic;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.view.LoginActivityView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LoginManage implements BindServiceCallBack, ResponseCallBack<String>, EMCallBack {

    private String TAG = LoginManage.class.getName();

    private LoginActivityView mILoginView;
    private Activity mActivity;
    private LogicService mService;
    private LoginLogic mLoginLogic;
    private String username, password;
    private UserInfo userInfo;

    public LoginManage(LoginActivityView loginView) {
        this.mILoginView = loginView;
        this.mActivity = (Activity) loginView;
        SharedPreferences mPreferences = mActivity.getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        username = mPreferences.getString(Constant.USER_NAME, "");
        password = mPreferences.getString(Constant.USER_PWD, "");
        Tools.bingBaseService(mActivity, this);
    }

    public void login() {
        mILoginView.showLoading(R.string.logging);
        mLoginLogic = (LoginLogic) mService.getLoginLogic();
        mLoginLogic.login(mILoginView.getUserName(), mILoginView.getPassWord(), this);
    }

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
                EMClient.getInstance().login(userInfo.getUserInfoBean().getHXUserName(), userInfo.getUserInfoBean().getHXPwd(), this);
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
    public void callFailure() {
        mILoginView.hiddenLoading();
        mILoginView.showMsg(R.string.error_connection);
    }

    @Override
    public void bindBaseServiceSuccess(BaseService baseService) {
        mService = (LogicService) baseService;
        mILoginView.initView();
        if (!"".equals(username) && !"".equals(password)) {
            mILoginView.initData(username, password);
        }
        mILoginView.initEvent();
    }

    @Override
    public void bindBaseServiceFailure() {
        mActivity.finish();
    }

    @Override
    public void onSuccess() {
        Tools.print(TAG, "环信登录成功!");
        mService.setCanMessage(true);
    }

    @Override
    public void onError(int i, String s) {
        Tools.print(TAG, MessageFormat.format("环信登录失败... 错误码:{0}, 错误信息:{1}", i, s));
        mService.setCanMessage(false);
    }

    @Override
    public void onProgress(int i, String s) {

    }
}
