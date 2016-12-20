package com.yhcloud.thankyou.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.yhcloud.thankyou.bean.UserInfoBean;
import com.yhcloud.thankyou.service.LogicService;
import com.yhcloud.thankyou.view.IEaseChatView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/20.
 */

public class EaseChatManage {

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
                mIEaseChatView.initEaseChatUserInfo(mBeen);
                goEaseChat();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void goEaseChat() {
        Intent intent = mActivity.getIntent();
        String chatType = intent.getStringExtra("chatType");
        String uId = intent.getStringExtra("uId");
        switch (chatType) {
            case "single":
                mIEaseChatView.showEaseChat(ChatType.SINGLE, uId);
                break;
            case "group":
                mIEaseChatView.showEaseChat(ChatType.GROUP, uId);
                break;
        }
    }

    public enum ChatType {
        SINGLE, GROUP;
    }
}
