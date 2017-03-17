package com.yhcloud.thankyou.module.todayrecipes.logic;

import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by leig on 2017/2/14.
 */

public class TodayRecipesLogic {

    private String TAG = getClass().getSimpleName();

    public void getRecipesDataForService(String userId, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.GETTODAYRECIPESLIST)
                .addParams("userId", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getRecipesList-请求失败: " + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getRecipesList-请求成功: " + response);
                        iCallBackListener.callSuccess(response);
                    }
                });

    }

    public void getRecipesDataForService(String userId, String termId, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.GETTODAYRECIPESLIST)
                .addParams("userId", userId)
                .addParams("termId", termId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getRecipesList-请求失败: " + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getRecipesList-请求成功: " + response);
                        iCallBackListener.callSuccess(response);
                    }
                });

    }

    public void getRecipesDataForService(String userId, String termId, String WeekIndex, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.GETTODAYRECIPESLIST)
                .addParams("userId", userId)
                .addParams("termId", termId)
                .addParams("WeekIndex", WeekIndex)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getRecipesList-请求失败: " + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getRecipesList-请求成功: " + response);
                        iCallBackListener.callSuccess(response);
                    }
                });

    }
}
