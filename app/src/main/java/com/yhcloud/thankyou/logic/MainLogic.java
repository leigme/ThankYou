package com.yhcloud.thankyou.logic;

import android.util.Log;

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
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
    public void getClassInfoList(String userId, final ICallListener<ArrayList<ClassInfoBean>> iCallListener) {
        OkHttpUtils.post()
                .url("")
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "getClassInfoList-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "getClassInfoList-请求成功:" + response);
                        ArrayList<ClassInfoBean> classInfoBeen = new ArrayList<>();
                        ClassInfoBean classInfoBean = new ClassInfoBean();
                        classInfoBeen.add(classInfoBean);
                        iCallListener.callSuccess(classInfoBeen);
                    }
                });
    }

    public void getFriendList(String userId, String updateTime, final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(Constant.GETFRIENDLIST)
                .addParams("id", userId)
                .addParams("updateTime", updateTime)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getFriendList-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getFriendList-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }

    public void getTermListForService(String userId, final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(Constant.GETTERMLIST)
                .addParams("userid", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getTermListForService-请求失败: " + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getTermListForService-请求成功: " + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }

    public void getWeekListForService(String termId, final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(Constant.GETWEEKLIST)
                .addParams("termId", termId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getWeekListForService-请求失败: " + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getWeekListForService-请求成功: " + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }
}
