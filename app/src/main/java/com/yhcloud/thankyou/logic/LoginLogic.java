package com.yhcloud.thankyou.logic;

import android.util.Log;

import com.yhcloud.thankyou.bean.UserInfo;

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

        if ("abc".equals(username) && "123".equals(password)) {
            iCallListener.callSuccess(userInfo);
        } else {
            iCallListener.callFailed();
        }
        //登录逻辑
//        OkHttpUtils.post()
//                .url("")
//                .addParams("user", username)
//                .addParams("pwd", password)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.e(TAG, "请求失败..." + e);
//                       iCallListener.callFailed();
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e(TAG, "请求成功!!!" + response);
//                       iCallListener.callSuccess(userInfo);
//                    }
//                });
    }
}
