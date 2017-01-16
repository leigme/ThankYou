package com.yhcloud.thankyou.module.account.logic;

import android.util.Log;

import com.yhcloud.thankyou.mInterface.ICallListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountLogic {
    private String TAG = getClass().getSimpleName();

    public void getImageUrlsForService(String userId, final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url("")
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }

}
