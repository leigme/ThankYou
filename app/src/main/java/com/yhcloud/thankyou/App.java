package com.yhcloud.thankyou;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.yhcloud.thankyou.service.LogicService;

/**
 * Created by Administrator on 2016/11/1.
 */

public class App extends Application {

    private String TAG = getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "初始化APP开始...");
        Intent intent = new Intent(this, LogicService.class);
        startService(intent);
        Log.e(TAG, "初始化APP结束...");
    }
}
