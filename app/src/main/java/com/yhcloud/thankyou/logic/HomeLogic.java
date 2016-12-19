package com.yhcloud.thankyou.logic;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by leig on 2016/11/20.
 */

public class HomeLogic implements IHomeLogic {

    private String TAG = getClass().getSimpleName();

    private ArrayList<SpreadBean> mLists;

    @Override
    public void getImageUrls(String updateTime, final ICallListener iCallListener) {
        OkHttpUtils.post()
                .url("http://www.k12chn.com/m01/M0108I/M0108I07")
                .addParams("promotionType", "16")
                .addParams("scopeCrowd", "2")
                .addParams("updateTime", updateTime)
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
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("errorFlag")) {
                                String jsonResult = jsonObject.getString("dataList");
                                if (null != jsonResult && !"".equals(jsonResult)) {
                                    Gson gson = new Gson();
                                    mLists = gson.fromJson(jsonResult, new TypeToken<ArrayList<SpreadBean>>(){}.getType());
                                    iCallListener.callSuccess(mLists);
                                } else {
                                    iCallListener.callFailed();
                                }
                            } else {
                                iCallListener.callFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
