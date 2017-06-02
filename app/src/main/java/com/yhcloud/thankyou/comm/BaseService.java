package com.yhcloud.thankyou.comm;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by leig on 2017/3/17.
 */

public abstract class BaseService extends Service {

    public BaseBinder mBinder = new BaseBinder();

    public class BaseBinder extends Binder {
        public BaseService getService() {
            return BaseService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
