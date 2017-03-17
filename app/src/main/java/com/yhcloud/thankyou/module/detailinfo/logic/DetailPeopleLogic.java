package com.yhcloud.thankyou.module.detailinfo.logic;

import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/22.
 */

public class DetailPeopleLogic implements IDetailPeopleLogic {

    private String TAG = getClass().getSimpleName();

    @Override
    public void getDetailInfo(String userId, String uId, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.GETDETAILINFO)
                .addParams("uid", userId)
                .addParams("id", uId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getDetailInfo-请求失败：" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getDetailInfo-请求成功：" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }
}
