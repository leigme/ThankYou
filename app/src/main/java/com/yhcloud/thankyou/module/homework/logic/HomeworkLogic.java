package com.yhcloud.thankyou.module.homework.logic;

import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/11.
 */

public class HomeworkLogic {
    private String TAG = getClass().getSimpleName();

    public void getTeacherHomeworkData(String userId, final ICallListener<String> iCallListener) {
        Tools.print(TAG, MessageFormat.format("getTeacherHomeworkData-请求的接口是:{0}/userId/{1}", Constant.GETTEACHERHOMEWORKLIST, userId));
        OkHttpUtils.post()
                .url(Constant.GETTEACHERHOMEWORKLIST)
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getTeacherHomeworkData-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getTeacherHomeworkData-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }

    public void getStudentHomeworkData(String userId, final ICallListener<String> iCallListener) {
        Tools.print(TAG, MessageFormat.format("getTeacherHomeworkData-请求的接口是:{0}/userId{1}", Constant.GETSTUDENTHOMEWORKLIST, userId));
        OkHttpUtils.post()
                .url(Constant.GETSTUDENTHOMEWORKLIST)
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getStudentHomeworkData-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getStudentHomeworkData-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }

    public void getStudentHomeworkInfo(String userId, String workBookId, final ICallListener<String> iCallListener) {
        Tools.print(TAG, MessageFormat.format("getStudentHomeworkInfo-请求的接口是:{0}/workBookId/15513/userId/{1}", Constant.GETSTUDENTHOMEWORKLIST, userId));
        OkHttpUtils.post()
                .url("http://192.168.0.139/edu/m17/M1722I/M1722I02/workBookId/15513")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getStudentHomeworkInfo-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getStudentHomeworkInfo-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }

    public void sendHomeworkToService(String userId, final ICallListener<String> iCallListener) {
        OkHttpUtils.post()
                .url("")
                .addParams("UserId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "sendHomeworkToService-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "sendHomeworkToService-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }

    public void sendImagesToService(String userId, String workId, String questionId, String content,
                                    String score, String startTime, String endTime,
                                    List<String> images, final ICallListener<String> iCallListener) {
        Map<String, File> fileMap = new LinkedHashMap<>();
        for (int i = 0; i < images.size(); i++) {
            if (null != images.get(i) && !"".equals(images.get(i))) {
                File file = new File(images.get(i)); // 如果文件没有扩展名, 最好设置contentType参数.
                fileMap.put(String.valueOf(i), file);
            }
        }
        OkHttpUtils.post()
                .url(Constant.SENDHOMEWORKSUBANSWER)
                .addParams("UserId", userId)
                .addParams("WorkId", workId)
                .addParams("QuestionId", questionId)
                .addParams("Content", content)
                .addParams("score", score)
                .addParams("StartTime", startTime)
                .addParams("EndTime", endTime)
                .files("image", fileMap)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "sendImagesToService-请求失败:" + e);
                        iCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "sendImagesToService-请求成功:" + response);
                        iCallListener.callSuccess(response);
                    }
                });
    }
}
