package com.yhcloud.thankyou.manage;

import android.util.Log;

import com.yhcloud.thankyou.bean.UserInfo;
import com.yhcloud.thankyou.logic.ICallListener;
import com.yhcloud.thankyou.logic.ILoginLogic;
import com.yhcloud.thankyou.logic.LoginLogic;
import com.yhcloud.thankyou.view.ILoginView;

/**
 * Created by Administrator on 2016/11/14.
 */

public class LoginManage {

    private String TAG = getClass().getSimpleName();
    private ILoginLogic mILoginLogic;
    private ILoginView mILoginView;

    public LoginManage(ILoginView loginView) {
        this.mILoginView = loginView;
        this.mILoginLogic = new LoginLogic();
    }

    public void login() {
        mILoginView.showDialog();
        mILoginLogic.login(mILoginView.getUserName(), mILoginView.getPassWord(), new CallListener());
    }

    public class CallListener implements ICallListener<UserInfo> {

        @Override
        public void callSuccess(UserInfo userInfo) {
            //实现登录成功的回调,如:传递对象到下一个activity或者存储对象到数据库
            Log.e(TAG, "登录成功...");
            mILoginView.pushMainActivity(userInfo.getClassInfos());
            mILoginView.hideDialog();
            mILoginView.closeActivity();
        }

        @Override
        public void callFailed() {
            Log.e(TAG, "登录失败...");
            mILoginView.hideDialog();
        }
    }
}
