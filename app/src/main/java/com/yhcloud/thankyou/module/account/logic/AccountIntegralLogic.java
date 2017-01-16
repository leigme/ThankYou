package com.yhcloud.thankyou.module.account.logic;

/**
 * Created by Administrator on 2016/12/9.
 */

public class AccountIntegralLogic {

    private String TAG = getClass().getSimpleName();

/*
    public void getIntegralList(final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(ServiceAPI.GETEXCHANGELIST)
                .addParams("type", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "getIntegralList-请求失败:" + e);
                        iCallListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "getIntegralList-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }

    public void getUserCoin(final String userId, String uCoin, String coin, final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(ServiceAPI.GETUSERCOIN)
                .addParams("userId", userId)
                .addParams("uCoin", uCoin)
                .addParams("coin", coin)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        iCallListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        iCallListener.callSuccess(response);
                    }
                });
    }
*/
}
