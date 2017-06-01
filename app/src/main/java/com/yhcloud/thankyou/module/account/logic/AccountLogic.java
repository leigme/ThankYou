package com.yhcloud.thankyou.module.account.logic;

import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.MessageFormat;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/3.
 */

public class AccountLogic {

    private String TAG = getClass().getSimpleName();

    public void getImageUrlsForService(String type, String flag, String updateTime, final ResponseCallBack<String> responseCallBack) {
        Tools.print(TAG, MessageFormat.format("请求地址:{0}/promotionType/{1}/scopeCrowd/{2}/updateTime/{3}", Constant.GETSPREADLIST, type, flag, updateTime));
        OkHttpUtils.post()
                .url(Constant.GETSPREADLIST)
                .addParams("promotionType", type)
                .addParams("scopeCrowd", flag)
                .addParams("updateTime", updateTime)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getSpreadList-请求失败:" + e);
                        responseCallBack.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getSpreadList-请求成功:" + response);
                        responseCallBack.callSuccess(response);
                    }
                });
    }

    public void getUserCurrency(String userId, final ResponseCallBack<String> responseCallBack) {
        OkHttpUtils.post()
                .url(Constant.GETUSERCURRENCY)
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getUserCurrency-请求失败:" + e);
                        responseCallBack.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getUserCurrency-请求成功:" + response);
                        responseCallBack.callSuccess(response);
                    }
                });
    }

}
