package com.yhcloud.thankyou.module.account.logic;

import android.util.Log;

import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/5.
 */

public class AccountMyPropsLogic {

    private String TAG = getClass().getSimpleName();

    public void getUserPropsForService(String userId, final ResponseCallBack<String> responseCallBack) {
        OkHttpUtils.post()
                .url(Constant.GETUSERPROPSLIST)
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "getUserPropsForService-请求失败: " + e);
                        responseCallBack.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "getUserPropsForService-请求成功: " + response);
                        responseCallBack.callSuccess(response);
                    }
                });
    }

    public void givePropsToPeople(String userId, String recvUserId, String propsId, int propsNum, final ResponseCallBack<String> responseCallBack) {
        OkHttpUtils.post()
                .url(Constant.GIVEPROPSTOPEOPLE)
                .addParams("userId", userId)
                .addParams("recvUserId", recvUserId)
                .addParams("propId", propsId)
                .addParams("propNum", String.valueOf(propsNum))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "givePropsToPeople-请求失败:" + e);
                        responseCallBack.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "givePropsToPeople-请求成功:" + response);
                        responseCallBack.callSuccess(response);
                    }
                });
    }
}
