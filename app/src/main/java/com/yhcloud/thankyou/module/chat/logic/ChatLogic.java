package com.yhcloud.thankyou.module.chat.logic;

import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.utils.Constant;
import com.yhcloud.thankyou.utils.Tools;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


/**
 * Created by leig on 2017/2/6.
 */

public class ChatLogic {

    private String TAG = getClass().getSimpleName();

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
}
