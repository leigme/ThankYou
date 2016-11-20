package com.yhcloud.thankyou.manage;

import com.yhcloud.thankyou.bean.SpreadBean;
import com.yhcloud.thankyou.logic.HomeLogic;
import com.yhcloud.thankyou.logic.ICallListener;
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

    public class CallListener implements ICallListener<ArrayList<SpreadBean>> {

        @Override
        public void callSuccess(ArrayList<SpreadBean> been) {
            ArrayList<String> arrayList = new ArrayList<>();
            for (SpreadBean sb: been) {
                arrayList.add(sb.getSummaryPicUrl());
            }
            mIHomeView.showBanner(arrayList);
        }

        @Override
        public void callFailed() {

        }
    }
}
