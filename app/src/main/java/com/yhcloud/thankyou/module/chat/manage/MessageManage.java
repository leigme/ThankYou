package com.yhcloud.thankyou.module.chat.manage;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.yhcloud.thankyou.module.chat.view.EaseChatActivity;
import com.yhcloud.thankyou.module.chat.view.IMessageView;
import com.yhcloud.thankyou.service.LogicService;

/**
 * Created by Administrator on 2016/12/20.
 */

public class MessageManage {

    private IMessageView mIMessageView;
    private Activity mActivity;
    private LogicService mService;

    public MessageManage(IMessageView iMessageView) {
        this.mIMessageView = iMessageView;
        this.mActivity = (Activity) mIMessageView;
        Intent intent = new Intent(mActivity, LogicService.class);
        mActivity.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = ((LogicService.MyBinder)service).getService();
                mIMessageView.initMessageFragment();
                mIMessageView.initView();
                mIMessageView.initEvent();
                mIMessageView.setTitle("消息列表");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void goChatActivity(EMConversation conversation) {
        Intent intent = new Intent(mActivity, EaseChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.getUserName());
        mActivity.startActivity(intent);
    }
}
