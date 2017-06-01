package com.yhcloud.thankyou.service.logic.mimplement;

import android.util.Log;

import com.yhcloud.thankyou.comm.ResponseCallBack;
import com.yhcloud.thankyou.service.logic.minterface.IClassLogic;
import com.yhcloud.thankyou.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ClassLogic implements IClassLogic {

    private String TAG = getClass().getSimpleName();

    @Override
    public void getClassPeopleListForService(String userId, String classId, String updateTime, final ResponseCallBack<String> responseCallBack) {
        OkHttpUtils.post()
                .url(Constant.GETCLASSPEOPLELIST)
                .addParams("userId", userId)
                .addParams("classId", classId)
                .addParams("updateTime", updateTime)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "getClassPeopleListForService-请求失败:" + e);
                        responseCallBack.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "getClassPeopleListForService-请求成功:" + response);
                        responseCallBack.callSuccess(response);
                    }
                });
    }
}
