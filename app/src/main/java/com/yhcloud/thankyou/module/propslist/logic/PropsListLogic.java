package com.yhcloud.thankyou.module.propslist.logic;

import com.yhcloud.thankyou.minterface.ICallListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/5.
 */

public class PropsListLogic {

    private String TAG = getClass().getSimpleName();

    public void getPropsListData(String userId, int typeId, int pageNum, final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(Constant.GETPROPSLIST)
                .addParams("userId", userId)
                .addParams("type", String.valueOf(typeId))
                .addParams("page", String.valueOf(pageNum))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getPropsListData-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getPropsListData-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }
}
