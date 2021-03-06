package com.yhcloud.thankyou.module.load.manage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

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
import com.yhcloud.thankyou.module.load.view.LoadActivity;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.yhcloud.thankyou.utils.myview.MyToast;
import com.yhcloud.thankyou.module.index.view.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author leig
 * @version 20170301
 */

public class LoadManage extends BaseManager implements BindServiceCallBack{

    private String TAG = LoadManage.class.getSimpleName();

    private LoadActivity mLoadActivity;
    private LogicService mService;
    private UserInfo mUserInfo;

    public LoadManage(LoadActivity loadActivity) {
        super(loadActivity);
        mBaseActivity.bindBaseService(LogicService.class, this);
        this.mLoadActivity = loadActivity;
    }

    @Override
    public void bindBaseServiceSuccess(BaseService baseService) {
        Tools.print(TAG, "绑定服务成功...");
        mService = (LogicService) baseService;
        SharedPreferences preferences = mLoadActivity.getSharedPreferences(Constant.USER_INFO, MODE_PRIVATE);
        boolean logined = preferences.getBoolean(Constant.USER_LOGINED, false);
        if (logined) {
            String username = preferences.getString(Constant.USER_NAME, "");
            String password = preferences.getString(Constant.USER_PWD, "");
            if (null != username && !"".equals(username) && null != password && !"".equals(password)) {
                mUserInfo = new UserInfo();
                mUserInfo.setUsername(username);
                mUserInfo.setPassword(password);
                mService.setUserInfo(mUserInfo);
                mService.login(username, password, new ResponseCallBack<String>() {
                    @Override
                    public void callSuccess(String s) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (!jsonObject.getBoolean("errorFlag")) {
                                String key = jsonObject.getString("key");
                                if (null != key && !"".equals(key)) {
                                    mUserInfo.setKey(key);
                                }
                                String jsonUserInfo = jsonObject.getString("userinfo");
                                String jsonClassInfos = jsonObject.getString("classlist");
                                if (null != jsonUserInfo && !"".equals(jsonUserInfo) && null != jsonClassInfos && !"".equals(jsonClassInfos)) {
                                    Gson gson = new Gson();
                                    UserInfoBean userInfoBean = gson.fromJson(jsonUserInfo, UserInfoBean.class);
                                    if (null != userInfoBean) {
                                        mUserInfo.setUserInfoBean(userInfoBean);
                                    }
                                    ArrayList<ClassInfoBean> classInfoBeen = gson.fromJson(jsonClassInfos, new TypeToken<ArrayList<ClassInfoBean>>(){}.getType());
                                    if (null != classInfoBeen) {
                                        mUserInfo.setClassInfoBeen(classInfoBeen);
                                    }
                                    mService.saveUserInfo(mUserInfo);
                                    mService.setUserInfo(mUserInfo);
                                    EMClient.getInstance().login(userInfoBean.getHXUserName(), userInfoBean.getHXPwd(), new EMCallBack() {
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
                                    });
                                    Intent intent = new Intent(mLoadActivity, MainActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("ClassInfos", classInfoBeen);
                                    intent.putExtras(bundle);
                                    mLoadActivity.startActivity(intent);
                                    mLoadActivity.finish();
                                }
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mLoadActivity.goLoginActivity();
                        }
                    }

                    @Override
                    public void callFailure() {
                        MyToast.showToast(mLoadActivity, R.string.error_connection);
                        mLoadActivity.goLoginActivity();
                    }
                });
            } else {
                MyToast.showToast(mLoadActivity, R.string.error_connection);
                mLoadActivity.goLoginActivity();
            }
        } else {
            MyToast.showToast(mLoadActivity, R.string.error_connection);
            mLoadActivity.goLoginActivity();
        }
    }

    @Override
    public void bindBaseServiceFailure() {
        Tools.print(TAG, "基础服务绑定失败。。。");
        mLoadActivity.finish();
    }
}
