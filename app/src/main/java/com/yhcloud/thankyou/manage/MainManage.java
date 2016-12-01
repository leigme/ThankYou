package com.yhcloud.thankyou.manage;

import com.yhcloud.thankyou.bean.ClassInfoBean;
import com.yhcloud.thankyou.mInterface.ICallListener;
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

    public class CallListener implements ICallListener<ArrayList<ClassInfoBean>> {

        @Override
        public void callSuccess(ArrayList<ClassInfoBean> infos) {
            mIMainView.showDrawer(infos);
        }

        @Override
        public void callFailed() {

        }
    }
}
