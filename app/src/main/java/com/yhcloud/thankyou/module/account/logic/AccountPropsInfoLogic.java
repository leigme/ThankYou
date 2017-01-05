package com.yhcloud.thankyou.module.account.logic;

import android.util.Log;

import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.utils.ServiceAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/5.
 */

public class AccountPropsInfoLogic {

    private String TAG = getClass().getSimpleName();

//    public void buyProps(String userId, String propId, String propNum, final ICallListener<String> iCallListener) {
//        OkHttpUtils.post()
//                .url(ServiceAPI.BUYPROPS)
//                .addParams("userId", userId)
//                .addParams("propId", propId)
//                .addParams("propNum", propNum)
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.e(TAG, "请求失败:" + e);
//                        iCallListener.callFailed();
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.e(TAG, "请求成功:" + response);
//                        iCallListener.callSuccess(response);
//                    }
//                });
//    }
}
