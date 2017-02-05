package com.yhcloud.thankyou.module.chat.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.hyphenate.easeui.EaseConstant;
import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.module.chat.view.IEaseChatView;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.utils.Tools;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/20.
 */

public class EaseChatManage {

    private String TAG = getClass().getSimpleName();

    private IEaseChatView mIEaseChatView;
    private Activity mActivity;
    private LogicService mService;
    private ArrayList<UserInfoBean> mBeen;

    public EaseChatManage(IEaseChatView iEaseChatView) {
        this.mIEaseChatView = iEaseChatView;
        this.mActivity = (Activity) mIEaseChatView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIEaseChatView.initView();
                mIEaseChatView.initEvent();
                mBeen = mService.getUserInfoBeen();
                if (null != mBeen && 0 != mBeen.size()) {

                } else {
                    Tools.print(TAG, "请求服务器获取用户集合");
                }
//                mIEaseChatView.initEaseChatUserInfo(mBeen);
                goEaseChat();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void goEaseChat() {
        Intent intent = mActivity.getIntent();
        if (null != intent) {
            mIEaseChatView.showEaseChat(intent.getStringExtra(EaseConstant.EXTRA_USER_ID));
        }
    }

    public void goDetailInfo() {
        Tools.print(TAG, "点击了用户头像...");
    }
}
