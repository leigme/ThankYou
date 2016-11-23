package com.yhcloud.thankyou.manage;

import com.yhcloud.thankyou.bean.ClassInfo;
import com.yhcloud.thankyou.mInterface.MyCallListener;
import com.yhcloud.thankyou.logic.IMainLogic;
import com.yhcloud.thankyou.view.IMainView;

import java.util.ArrayList;

/**
 * Created by leig on 2016/11/19.
 */

public class MainManage {
    private String TAG = getClass().getSimpleName();
    private IMainLogic mIMainLogic;
    private IMainView mIMainView;

    public MainManage(IMainView mainView) {
        this.mIMainView = mainView;
    }

    public void getClassInfo(String userId) {
        mIMainLogic.getClassInfoList("3237", new CallListener());
    }

    public class CallListener implements MyCallListener<ArrayList<ClassInfo>> {

        @Override
        public void callSuccess(ArrayList<ClassInfo> infos) {
            mIMainView.showDrawer(infos);
        }

        @Override
        public void callFailed() {

        }
    }
}
