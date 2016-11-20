package com.yhcloud.thankyou.logic;

import android.util.Log;

import com.yhcloud.thankyou.bean.ClassInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by leig on 2016/11/19.
 */

public class MainLogic implements IMainLogic {

    private String TAG = getClass().getSimpleName();

    @Override
    public void getClassInfoList(String userId, final ICallListener iCallListener) {
        OkHttpUtils.post()
                .url("")
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "请求成功:" + response);
                        ArrayList<ClassInfo> classInfos = new ArrayList<>();
                        ClassInfo classInfo = new ClassInfo();
                        classInfos.add(classInfo);
                        iCallListener.callSuccess(classInfos);
                    }
                });
    }
}
