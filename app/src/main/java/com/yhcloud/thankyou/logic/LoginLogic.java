package com.yhcloud.thankyou.logic;

import android.util.Log;

import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LoginLogic implements ILoginLogic {

    private String TAG = getClass().getSimpleName();

    @Override
    public void login(String username, String password, final ICallListener<String> iCallListener) {
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
                        iCallListener.callSuccess(response);
                    }
                });
    }
}
