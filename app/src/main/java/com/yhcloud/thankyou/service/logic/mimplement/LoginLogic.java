package com.yhcloud.thankyou.service.logic.mimplement;

import android.util.Log;

import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.service.logic.minterface.ILoginLogic;
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
    public void login(String username, String password, final ICallBackListener<String> iCallBackListener) {
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
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "请求成功!!!" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }
}
