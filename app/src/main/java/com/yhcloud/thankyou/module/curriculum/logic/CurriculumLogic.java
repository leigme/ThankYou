package com.yhcloud.thankyou.module.curriculum.logic;

import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/14.
 */

public class CurriculumLogic {

    private String TAG = getClass().getSimpleName();

    public void getClassCurriculum(String userId, String classId, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.GETCLASSCURRICULUM)
                .addParams("uid", userId)
                .addParams("classid", classId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getClassCurriculum-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getClassCurriculum-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }
}
