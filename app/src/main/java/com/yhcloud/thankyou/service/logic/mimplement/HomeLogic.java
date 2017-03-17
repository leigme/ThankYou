package com.yhcloud.thankyou.service.logic.mimplement;

import android.util.Log;

import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.service.logic.minterface.IHomeLogic;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.MessageFormat;

import okhttp3.Call;

/**
 * Created by leig on 2016/11/20.
 */

public class HomeLogic implements IHomeLogic {

    private String TAG = getClass().getSimpleName();

    public void getSpreadList(String type, String flag, String updateTime, final ICallBackListener<String> iCallBackListener) {
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
                        Log.e(TAG, "getSpreadList-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "getSpreadList-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }
}
