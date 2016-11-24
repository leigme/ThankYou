package com.yhcloud.thankyou.manage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.mInterface.MyCallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.IHomeView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/20.
 */

public class HomeManage {

    private String TAG = getClass().getSimpleName();
    private IHomeView mIHomeView;
    private LogicService mService;

    public HomeManage(IHomeView homeView, final Handler handler) {
        this.mIHomeView = homeView;
        Fragment fragment = (Fragment) homeView;
        Intent intent = new Intent(fragment.getActivity(), LogicService.class);
        fragment.getActivity().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.e(TAG, "服务绑定成功...");
                mService = ((LogicService.MyBinder)binder).getService();
                handler.obtainMessage(1).sendToTarget();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                handler.obtainMessage(0).sendToTarget();
            }
        }, Context.BIND_AUTO_CREATE);
    }

    public void showBanner(String updateTime) {
        mService.getImageUrls(updateTime, new CallListener());
    }

    public class CallListener implements MyCallListener<ArrayList<SpreadBean>> {

        @Override
        public void callSuccess(ArrayList<SpreadBean> been) {
            ArrayList<String> arrayList = new ArrayList<>();
            for (SpreadBean sb: been) {
                arrayList.add("http://www.k12chn.com" + sb.getSummaryPicLink());
                Log.e(TAG, "http://www.k12chn.com" + sb.getSummaryPicLink());
            }
            mIHomeView.showBanner(arrayList);
        }

        @Override
        public void callFailed() {

        }
    }
}
