package com.yhcloud.thankyou.module.login.manage;

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
import com.yhcloud.thankyou.comm.BaseManager;
import com.yhcloud.thankyou.comm.BaseService;
import com.yhcloud.thankyou.comm.BindServiceCallBack;
import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.module.login.view.LoginActivity;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.service.logic.mimplement.LoginLogic;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LoginManage extends BaseManager implements BindServiceCallBack, ResponseCallBack<String>, EMCallBack {

    private String TAG = LoginManage.class.getName();

    private LoginActivity mLoginActivity;
    private LogicService mService;
    private LoginLogic mLoginLogic;
    private String username, password;
    private UserInfo userInfo;

    public LoginManage(LoginActivity loginActivity) {
        super(loginActivity);
        this.mLoginActivity = loginActivity;
        mLoginActivity.bindBaseService(LogicService.class, this);
        SharedPreferences mPreferences = loginActivity.getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
        username = mPreferences.getString(Constant.USER_NAME, "");
        password = mPreferences.getString(Constant.USER_PWD, "");
    }

    public void login() {
        mLoginActivity.showLoading(R.string.logging);
        mLoginLogic = (LoginLogic) mService.getLoginLogic();
        mLoginLogic.login(mLoginActivity.getUserName(), mLoginActivity.getPassWord(), this);
    }

    @Override
    public void callSuccess(String s) {
        userInfo = new UserInfo();
        userInfo.setUsername(mLoginActivity.getUserName());
        userInfo.setPassword(mLoginActivity.getPassWord());
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
                mLoginActivity.pushMainActivity(userInfo.getClassInfoBeen());
                mLoginActivity.closeActivity();
            } else {
                mLoginActivity.showMsg(R.string.error_login);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mLoginActivity.showMsg(R.string.error_connection);
        }
        mLoginActivity.hiddenLoading();
    }

    @Override
    public void callFailure() {
        mLoginActivity.hiddenLoading();
        mLoginActivity.showMsg(R.string.error_connection);
    }

    @Override
    public void bindBaseServiceSuccess(BaseService baseService) {
        mService = (LogicService) baseService;
        mLoginActivity.initView();
        if (!"".equals(username) && !"".equals(password)) {
            mLoginActivity.initData(username, password);
        }
        mLoginActivity.initEvent();
    }

    @Override
    public void bindBaseServiceFailure() {
        mLoginActivity.finish();
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
