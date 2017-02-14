package com.yhcloud.thankyou.module.account.logic;

import android.util.Log;

import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountRechargeLogic {

    private String TAG = getClass().getSimpleName();

    public void getRechargeList(final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(Constant.GETEXCHANGELIST)
                .addParams("type", "2")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "getRechargeList-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "getRechargeList-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }

//    public void getUserInfoForLocal(Context context, String userId, ICallListener<UserInfoBean> iCallListener) {
//        UserInfoBean userInfoBean = new UserInfoBean();
//        userInfoBean.setUserId(userId);
//        iCallListener.callSuccess(DbSelect.yhSelect(context, userInfoBean));
//    }

    public void getPayList(final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(Constant.GETPAYLIST)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getPayList-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getPayList-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }

    public void getOrderNumForService(String userId, String productId, String payId, String userName, final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(Constant.PAYMONEY)
                .addParams("userId", userId)
                .addParams("cz_id", productId)
                .addParams("czfs", payId)
                .addParams("userName", userName)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getOrderNumForService-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getOrderNumForService-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }
}
