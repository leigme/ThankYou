package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.mInterface.ICallListener;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.IHomeView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/20.
 */

public class HomeManage {

    private String TAG = getClass().getSimpleName();
    private IHomeView mIHomeView;
    private Activity mActivity;
    private Fragment mFragment;
    private LogicService mService;

    public HomeManage(IHomeView homeView, LogicService service) {
        this.mIHomeView = homeView;
        this.mFragment = (Fragment) mIHomeView;
        this.mActivity = mFragment.getActivity();
        this.mService = service;
        mIHomeView.initView();
        showBanner("-1");
    }

    public void showBanner(String updateTime) {
        mService.getImageUrls(updateTime, new ICallListener<ArrayList<SpreadBean>>() {
            @Override
            public void callSuccess(ArrayList<SpreadBean> been) {
                ArrayList<String> arrayList = new ArrayList<>();
                for (SpreadBean sb: been) {
                    arrayList.add("http://www.k12chn.com" + sb.getSummaryPicLink());
                }
                mIHomeView.showBanner(arrayList);
            }

            @Override
            public void callFailed() {

            }
        });
    }
}
