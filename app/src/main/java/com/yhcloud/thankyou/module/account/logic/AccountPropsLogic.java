package com.yhcloud.thankyou.module.account.logic;

import com.yhcloud.thankyou.mInterfacea.ICallListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/5.
 */

public class AccountPropsLogic {

    private String TAG = getClass().getSimpleName();

    public void getPropsListForService(String userId, final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(Constant.GETPROPSSTORELIST)
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getPropsListForService-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getPropsListForService-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }
}
