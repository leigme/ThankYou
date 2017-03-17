package com.yhcloud.thankyou.module.homework.logic;

import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/11.
 */

public class HomeworkLogic {
    private String TAG = getClass().getSimpleName();

    public void getTeacherHomeworkList(String userId, String page, final ICallBackListener<String> iCallBackListener) {
        Tools.print(TAG, MessageFormat.format("getTeacherHomeworkList-请求的接口是:{0}/userId/{1}", Constant.GETTEACHERHOMEWORKLIST, userId));
        OkHttpUtils.post()
                .url(Constant.GETTEACHERHOMEWORKLIST)
                .addParams("userId", userId)
                .addParams("page", page)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getTeacherHomeworkList-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getTeacherHomeworkList-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }

    public void getStudentHomeworkList(String userId, String page, final ICallBackListener<String> iCallBackListener) {
        Tools.print(TAG, MessageFormat.format("getTeacherHomeworkList-请求的接口是:{0}/userId{1}", Constant.GETSTUDENTHOMEWORKLIST, userId));
        OkHttpUtils.post()
                .url(Constant.GETSTUDENTHOMEWORKLIST)
                .addParams("userId", userId)
                .addParams("page", page)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getStudentHomeworkList-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getStudentHomeworkList-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }

    public void getStudentHomeworkInfo(String userId, String workBookId, final ICallBackListener<String> iCallBackListener) {
        Tools.print(TAG, MessageFormat.format("getStudentHomeworkInfo-请求的接口是:{0}/workBookId/15513/userId/{1}", Constant.GETSTUDENTHOMEWORKLIST, userId));
        OkHttpUtils.post()
                .url(Constant.GETSTUDENTHOMEWORKINFO)//15513 15982
                .addParams("userId", userId)
                .addParams("workBookId", workBookId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getStudentHomeworkInfo-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getStudentHomeworkInfo-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }

    public void sendObjectiveHomeworkToService(String userId, String jsonData, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.SENDHOMEWORKOBANSWER)
                .addParams("UserId", userId)
                .addParams("QuestionAnswer", jsonData)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "sendObjectiveHomeworkToService-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "sendObjectiveHomeworkToService-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }

    public void sendImagesToService(String userId, String workId, String questionId, String content,
                                    String score, String startTime, String endTime,
                                    List<String> images, final ICallBackListener<String> iCallBackListener) {
        Tools.print(TAG, MessageFormat.format("请求连接是:{0}/userId/{1}/workId/{2}/questionId/{3}/content/{4}/score/{5}/startTime/{6}/endTime/{7}", Constant.SENDHOMEWORKSUBANSWER, userId, workId, questionId, content, score, startTime, endTime));

        PostFormBuilder postFormBuilder = OkHttpUtils.post();
        for (int i = 0; i < images.size(); i++) {
            if (null != images.get(i) && !"".equals(images.get(i))) {
                File file = new File(images.get(i)); // 如果文件没有扩展名, 最好设置contentType参数.
                postFormBuilder.addFile(String.valueOf(i), i + ".jpg", file);
            }
        }

        Map<String, String> params = new HashMap<>();
        params.put("UserId", userId);
        params.put("WorkId", workId);
        params.put("QuestionId", questionId);
        params.put("Content", content);
        params.put("score", score);
        params.put("StartTime", startTime);
        params.put("EndTime", endTime);

        postFormBuilder.url(Constant.SENDHOMEWORKSUBANSWER)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "sendImagesToService-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "sendImagesToService-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }

    public void updateStudentHomework(String workId, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.UPDATESTUDENTHOMEWORK)
                .addParams("WorkId", workId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "updateStudentHomework-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "updateStudentHomework-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }

    public void getTeacherHomeworkInfo(String homeworkId, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.GETTEACHERHOMEWORKINFO)
                .addParams("workBookId", homeworkId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getTeacherHomeworkInfo-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getTeacherHomeworkInfo-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }
}
