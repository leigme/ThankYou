package com.yhcloud.thankyou.logic;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LoginLogic implements ILoginLogic {

    private String TAG = getClass().getSimpleName();

    @Override
    public void login(String username, String password, final ICallListener iCallListener) {
        final UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);

//        登录逻辑
       OkHttpUtils.post()
                .url(Constant.LOGIN)
                .addParams("user", username)
                .addParams("pwd", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "请求失败..." + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "请求成功!!!" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
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
                                    iCallListener.callSuccess(userInfo);
                                } else {
                                    iCallListener.callFailed();
                                }
                            } else {
                                iCallListener.callFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            iCallListener.callFailed();
                        }
                    }
                });
    }
}
