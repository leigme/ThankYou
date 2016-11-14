package com.yhcloud.thankyou.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.yhcloud.thankyou.AIDLService;

/**
 * Created by Administrator on 2016/11/10.
 */

public class MyService extends Service {
    private String TAG = getClass().getSimpleName();
    //AIDL实例化对象
    private AIDLServiceImpl mAIDLServiceImpl;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mAIDLServiceImpl = new AIDLServiceImpl();
        return mAIDLServiceImpl;
    }

    public class AIDLServiceImpl extends AIDLService.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void success() throws RemoteException {
            Log.e(TAG, "连接服务AIDL成功...");
        }
    }

}
