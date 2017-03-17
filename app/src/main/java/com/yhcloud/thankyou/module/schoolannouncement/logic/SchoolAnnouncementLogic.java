package com.yhcloud.thankyou.module.schoolannouncement.logic;

import com.yhcloud.thankyou.minterface.ICallBackListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/1/6.
 */

public class SchoolAnnouncementLogic {
    private String TAG = getClass().getSimpleName();

    public void getSchoolAnnouncementData(String schoolId, int pageNum, final ICallBackListener<String> iCallBackListener) {
        OkHttpUtils.post()
                .url(Constant.GETSHCHOOLANNOUNCEMENTLIST)
                .addParams("schoolId", schoolId)
                .addParams("page", String.valueOf(pageNum))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Tools.print(TAG, "getSchoolAnnouncementData-请求失败:" + e);
                        iCallBackListener.callFailure();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Tools.print(TAG, "getSchoolAnnouncementData-请求成功:" + response);
                        iCallBackListener.callSuccess(response);
                    }
                });
    }
}
