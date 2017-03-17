package com.yhcloud.thankyou.service.logic.mimplement;

import android.util.Log;

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.service.logic.minterface.IMainLogic;
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
    public void getClassInfoList(String userId, final ICallBackListener<ArrayList<ClassInfoBean>> iCallBackListener) {
        OkHttpUtils.post()
                .url("")
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "getClassInfoList-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "getClassInfoList-请求成功:" + response);
                        ArrayList<ClassInfoBean> classInfoBeen = new ArrayList<>();
                        ClassInfoBean classInfoBean = new ClassInfoBean();
                        classInfoBeen.add(classInfoBean);
                        iCallBackListener.callSuccess(classInfoBeen);
                    }
                });
    }

    public void getFriendList(String userId, String updateTime, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.GETFRIENDLIST)
                .addParams("id", userId)
                .addParams("updateTime", updateTime)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getFriendList-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getFriendList-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }

    public void getTermListForService(String userId, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.GETTERMLIST)
                .addParams("userid", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getTermListForService-请求失败: " + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getTermListForService-请求成功: " + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }

    public void getWeekListForService(String termId, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.GETWEEKLIST)
                .addParams("termId", termId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getWeekListForService-请求失败: " + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getWeekListForService-请求成功: " + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }
}
