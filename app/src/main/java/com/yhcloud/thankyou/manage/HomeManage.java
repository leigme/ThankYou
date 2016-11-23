package com.yhcloud.thankyou.manage;

import android.util.Log;

import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.logic.HomeLogic;
import com.yhcloud.thankyou.mInterface.MyCallListener;
import com.yhcloud.thankyou.logic.IHomeLogic;
import com.yhcloud.thankyou.view.IHomeView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/20.
 */

public class HomeManage {
    private String TAG = getClass().getSimpleName();
    private IHomeLogic mIHomeLogic;
    private IHomeView mIHomeView;

    public HomeManage(IHomeView homeView) {
        this.mIHomeView = homeView;
        this.mIHomeLogic = new HomeLogic();
    }

    public void showBanner(String updateTime) {
        mIHomeLogic.getImageUrls(updateTime, new CallListener());
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
