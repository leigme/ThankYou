package com.yhcloud.thankyou.module.curriculum.logic;

import android.util.Log;

import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.ServiceAPI;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/14.
 */

public class CurriculumLogic {

    private String TAG = getClass().getSimpleName();

    public void getClassCurriculum(String userId, String classId, final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(Constant.GETCLASSCURRICULUM)
                .addParams("uid", userId)
                .addParams("classid", classId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "getClassCurriculum-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "getClassCurriculum-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }
}