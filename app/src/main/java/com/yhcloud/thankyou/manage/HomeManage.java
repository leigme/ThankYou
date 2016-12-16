package com.yhcloud.thankyou.manage;

import android.support.v4.app.Fragment;

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
    private Fragment mFragment;
    private LogicService mService;

    public HomeManage(IHomeView homeView, LogicService service) {
        this.mIHomeView = homeView;
        mFragment = (Fragment) mIHomeView;
        this.mService = service;
        showBanner("-1");
    }

    public void showBanner(String updateTime) {
        mService.getImageUrls(updateTime, new CallListener());
    }

    public class CallListener implements ICallListener<ArrayList<SpreadBean>> {

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
    }
}
