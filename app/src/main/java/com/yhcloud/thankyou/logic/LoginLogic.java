package com.yhcloud.thankyou.logic;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.bean.ClassInfo;
import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.mInterface.MyCallListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LoginLogic implements ILoginLogic {

    private String TAG = getClass().getSimpleName();

    @Override
    public void login(String username, String password, final MyCallListener myCallListener) {
        final UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);

//        登录逻辑
        OkHttpUtils.post()
                .url("http://www.k12chn.com/m17/M1708I/M1708I001")
                .addParams("user", username)
                .addParams("pwd", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "请求失败..." + e);
                        myCallListener.callFailed();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "请求成功!!!" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("errorFlag")) {
                                String jsonClassInfos = jsonObject.getString("classlist");
                                if (null != jsonClassInfos && !"".equals(jsonClassInfos)) {
                                    Gson gson = new Gson();
                                    ArrayList<ClassInfo> classInfos = gson.fromJson(jsonClassInfos, new TypeToken<ArrayList<ClassInfo>>(){}.getType());
                                    if (null != classInfos) {
                                        userInfo.setClassInfos(classInfos);
                                        myCallListener.callSuccess(userInfo);
                                    }
                                } else {
                                    myCallListener.callFailed();
                                }
                            } else {
                                myCallListener.callFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            myCallListener.callFailed();
                        }
                    }
                });
    }
}
