package com.yhcloud.thankyou;

import android.app.Application;
import android.util.Log;

/**
 * Created by Administrator on 2016/11/1.
 */

public class App extends Application {

    private String TAG = getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "初始化APP开始...");



        Log.e(TAG, "初始化APP结束...");
    }
}
