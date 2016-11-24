package com.yhcloud.thankyou.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.yhcloud.thankyou.logic.HomeLogic;
import com.yhcloud.thankyou.mInterface.MyCallListener;

public class LogicService extends Service {

    private MyBinder mBinder = new MyBinder();

    public LogicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        public LogicService getService() {
            return LogicService.this;
        }
    }

    public void getImageUrls(String updateTime, MyCallListener myCallListener) {
        HomeLogic homeLogic = new HomeLogic();
        homeLogic.getImageUrls(updateTime, myCallListener);
    }
}
