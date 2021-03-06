package com.yhcloud.thankyou.module.aboutus.logic;

import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/26.
 */

public class AboutUsLogic {

    private String TAG = getClass().getSimpleName();

    public void getAboutUsInfo(final ResponseCallBack<String> responseCallBack) {
        OkHttpUtils.post()
                .url(Constant.GETABOUTUSINFO)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getAboutUsInfo-请求失败:" + e);
                        responseCallBack.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getAboutUsInfo-请求成功:" + response);
                        responseCallBack.callSuccess(response);
                    }
                });
    }
}
