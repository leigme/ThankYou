package com.yhcloud.thankyou.module.classnotification.logic;

import android.util.Log;

import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.MessageFormat;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/6.
 */

public class ClassNotificationLogic {
    private String TAG = getClass().getSimpleName();

    public void getClassNotificationData(String userId, String classId, int pageNum, String updateTime, final ICallListener<String> iCallListener) {
        /*Tools.print(TAG, MessageFormat.format("请求连接:{0}/userId/{1}/classId/{2}/pageNum/{3}/updateTime/{4}", Constant.GETCLASSNOTIFICATIONLIST, userId, classId, String.valueOf(pageNum), updateTime));
        OkHttpUtils.post()
                .url(Constant.GETCLASSNOTIFICATIONLIST)
                .addParams("userId", userId)
                .addParams("classId", classId)
                .addParams("pageNum", String.valueOf(pageNum))
                .addParams("updateTime", updateTime)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getClassNotificationData-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getClassNotificationData-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });*/
        OkHttpUtils.post()
                .url(Constant.SERVICEADDRESS + "/m17/ClassNotice/GetClassNotice")
                .addParams("uid", userId)
                .addParams("page", String.valueOf(pageNum))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getClassNotificationData-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getClassNotificationData-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }

    public void updateReadState(String noticeId, String userId, final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url(Constant.UPDATEREADSTATE)
                .addParams("noticeId", noticeId)
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "updateReadStatu-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "updateReadStatu-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }
}
