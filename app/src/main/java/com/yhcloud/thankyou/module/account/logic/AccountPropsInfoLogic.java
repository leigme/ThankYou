package com.yhcloud.thankyou.module.account.logic;

import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/5.
 */

public class AccountPropsInfoLogic {

    private String TAG = getClass().getSimpleName();

    public void buyProps(String userId, String propId, String propNum, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.BUYPROPS)
                .addParams("userId", userId)
                .addParams("propId", propId)
                .addParams("propNum", propNum)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "buyProps-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "buyProps-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }
}
