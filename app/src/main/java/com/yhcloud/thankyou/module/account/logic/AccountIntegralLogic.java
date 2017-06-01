package com.yhcloud.thankyou.module.account.logic;

import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/9.
 */

public class AccountIntegralLogic {

    private String TAG = getClass().getSimpleName();

    public void getIntegralList(final ResponseCallBack<String> responseCallBack) {
        OkHttpUtils.post()
                .url(Constant.GETEXCHANGELIST)
                .addParams("type", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getIntegralList-请求失败:" + e);
                        responseCallBack.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getIntegralList-请求成功:" + response);
                        responseCallBack.callSuccess(response);
                    }
                });
    }

    public void getUserCoin(final String userId, String uCoin, String coin, final ResponseCallBack<String> responseCallBack) {
        OkHttpUtils.post()
                .url(Constant.GETUSERCOIN)
                .addParams("userId", userId)
                .addParams("uCoin", uCoin)
                .addParams("coin", coin)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getUserCoin-请求失败:" + e);
                        responseCallBack.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getUserCoin-请求成功:" + response);
                        responseCallBack.callSuccess(response);
                    }
                });
    }
}
